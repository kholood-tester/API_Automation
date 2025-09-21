package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
    private static ExtentReports extent;

    public static ExtentReports getReporter(String filePath) {
        if (extent == null) {
            ExtentSparkReporter spark = new ExtentSparkReporter(filePath);
            spark.config().setReportName("Unified TestResult");
            spark.config().setDocumentTitle("Unified TestReport");

            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("Tester", "Kholood");
            extent.setSystemInfo("Environment", "ReqRes Free");
        }
        return extent;
    }
}
