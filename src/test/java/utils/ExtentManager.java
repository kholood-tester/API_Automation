package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.ViewName;

public class ExtentManager {
    private static ExtentReports extent;

    public static ExtentReports getReporter(String filePath) {
        if (extent == null) {
            ExtentSparkReporter spark = new ExtentSparkReporter(filePath);
            spark.config().setReportName("Unified Test Result");
            spark.config().setDocumentTitle("Unified Test Report");
            spark.config().setTimelineEnabled(true);

            // 👇 Configure Dashboard/Charts as the first (default) view
            spark.viewConfigurer()
                    .viewOrder()
                    .as(new ViewName[]{
                            ViewName.DASHBOARD,
                            ViewName.TEST,
                            ViewName.EXCEPTION
                    }).apply();

            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("Tester", "Kholood");
            extent.setSystemInfo("Environment", "ReqRes Free");
        }
        return extent;
    }
}
