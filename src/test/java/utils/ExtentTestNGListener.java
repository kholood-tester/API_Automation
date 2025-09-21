package utils;

import com.aventstack.extentreports.*;
import org.testng.*;

import java.io.File;
import java.io.IOException;

public class ExtentTestNGListener implements ITestListener, ISuiteListener {

    private static final ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();
    private ExtentReports extent;
    private File reportFile;

    @Override
    public void onStart(ISuite suite) {
        // Always create in Downloads with the same name
        String downloads = System.getProperty("user.home") + File.separator + "Downloads";
        reportFile = new File(downloads, "Unified TestResult.html");

        extent = ExtentManager.getReporter(reportFile.getAbsolutePath());
    }

    @Override
    public void onFinish(ISuite suite) {
        if (extent != null) {
            extent.flush();
        }

        // Automatically open in Chrome after test run
        try {
            String chromeCmd = "google-chrome"; // Linux
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                chromeCmd = "cmd /c start chrome";
            } else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                chromeCmd = "open -a \"Google Chrome\"";
            }

            Runtime.getRuntime().exec(chromeCmd + " \"" + reportFile.getAbsolutePath() + "\"");
        } catch (IOException e) {
            System.err.println("Could not open Chrome automatically. Report at: " + reportFile.getAbsolutePath());
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extent.createTest(result.getMethod().getMethodName());
        testThread.set(test);
    }

    @Override public void onTestSuccess(ITestResult result) { testThread.get().pass("Test passed"); testThread.remove(); }
    @Override public void onTestFailure(ITestResult result) { testThread.get().fail(result.getThrowable()); testThread.remove(); }
    @Override public void onTestSkipped(ITestResult result) { testThread.get().skip("Test skipped"); testThread.remove(); }
}
