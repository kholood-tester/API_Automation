package utils;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import io.restassured.response.Response;
import org.testng.*;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ExtentTestNGListener implements ITestListener, ISuiteListener {

    private static final ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();
    private ExtentReports extent;
    private File reportFile;

    // ----- Public helpers for tests -----
    public static void log(Status status, String message) {
        ExtentTest t = testThread.get();
        if (t != null) t.log(status, message);
    }

    /**
     * Assert and report the API status code & response time.
     * Adds a "warning" tag when time >= warnThresholdMs.
     */
    public static void assertAndReport(String apiName, Response res, int expectedStatus, long warnThresholdMs) {
        ExtentTest t = testThread.get();
        if (t == null) return;

        long timeMs = res.timeIn(TimeUnit.MILLISECONDS);
        int status = res.statusCode();

        // 1) Display status code in the report
        t.info("API: " + apiName);
        t.info("Status Code: <b>" + status + "</b>");
        t.info("Response Time: <b>" + timeMs + " ms</b>");

        // 2) Time-based warning logic
        if (timeMs >= warnThresholdMs) {
            t.warning(MarkupHelper.createLabel(
                    "WARNING: Slow API (" + timeMs + " ms ≥ " + warnThresholdMs + " ms)", ExtentColor.ORANGE));
            t.assignCategory("warning");  // "warning tag"
        } else {
            t.pass("Response time OK (" + timeMs + " ms < " + warnThresholdMs + " ms)");
        }

        // 3) Hard assertions (fail test if wrong)
        Assert.assertEquals(status, expectedStatus, apiName + " unexpected status code.");
        // Optional: a very loose upper bound to catch stuck calls (tweak as you like)
        Assert.assertTrue(timeMs < 60000, apiName + " took > 60s (sanity cap).");
    }

    // ----- Suite wiring -----
    @Override
    public void onStart(ISuite suite) {
        String downloads = System.getProperty("user.home") + File.separator + "Downloads";
        reportFile = new File(downloads, "Unified test result.html");
        extent = ExtentManager.getReporter(reportFile.getAbsolutePath());
    }

    @Override
    public void onFinish(ISuite suite) {
        if (extent != null) extent.flush();

        try {
            String os = System.getProperty("os.name").toLowerCase();
            String chromeCmd = os.contains("win") ? "cmd /c start chrome"
                    : os.contains("mac") ? "open -a \"Google Chrome\""
                    : "google-chrome";
            Runtime.getRuntime().exec(chromeCmd + " \"" + reportFile.getAbsolutePath() + "\"");
        } catch (IOException e) {
            System.err.println("Could not open Chrome automatically. Report at: " + reportFile.getAbsolutePath());
        }
    }

    @Override public void onTestStart(ITestResult result) {
        String name = result.getMethod().getMethodName();
        String className = result.getTestClass().getName();
        ExtentTest test = extent.createTest(name);
        testThread.set(test);
    }
    @Override public void onTestSuccess(ITestResult r){ testThread.get().pass("Test passed"); testThread.remove(); }
    @Override public void onTestFailure(ITestResult r){ testThread.get().fail(r.getThrowable()); testThread.remove(); }
    @Override public void onTestSkipped(ITestResult r){ testThread.get().skip("Test skipped"); testThread.remove(); }
}
