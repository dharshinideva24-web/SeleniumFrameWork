package rahulshettyacademy.TestComponents;

import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import rahulshettyacademy.resources.EvidenceManager;
import rahulshettyacademy.resources.ExtentReportNG;

/**
 * TestNG listener for your NON-Cucumber tests (ErrorValidationsTest,
 * FinalStandaloneTest, ...).
 *
 * What changed from your original:
 *  - screenshots are captured automatically by ScreenshotListener
 *  - onTestSuccess AND onTestFailure -> both build a PDF, so you get a
 *    PDF whether the test PASSES or FAILS
 *  - the per-page screenshots come from BaseTest.captureStep(...) calls
 *    you place inside the tests (see the setup guide)
 */
public class Listeners extends BaseTest implements ITestListener, ISuiteListener {

    private static ExtentReports extent = ExtentReportNG.getReportObjects();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().log(Status.PASS, "Test Passed");
        WebDriver driver = resolveDriver(result);
        captureFinal(driver, "Final state - PASS");
        buildPdf(result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.get().fail(result.getThrowable());
        WebDriver driver = resolveDriver(result);
        captureFinal(driver, "Final state - FAIL");

        // keep your original behaviour: embed the failure shot in Extent too
        if (driver != null) {
            try {
                String path = getScreenshot(result.getMethod().getMethodName(), driver);
                test.get().addScreenCaptureFromPath(path, result.getMethod().getMethodName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        buildPdf(result.getMethod().getMethodName());
    }

    @Override
    public void onFinish(ISuite suite) {
        extent.flush();
    }

    // ---------- helpers ----------

    /** Finds the 'driver' field on the running test instance (walks up to BaseTest). */
    private WebDriver resolveDriver(ITestResult result) {
        try {
            Class<?> clazz = result.getTestClass().getRealClass();
            java.lang.reflect.Field field = null;
            while (clazz != null) {
                try {
                    field = clazz.getDeclaredField("driver");
                    break;
                } catch (NoSuchFieldException e) {
                    clazz = clazz.getSuperclass();
                }
            }
            if (field != null) {
                field.setAccessible(true);
                return (WebDriver) field.get(result.getInstance());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void captureFinal(WebDriver driver, String label) {
        if (driver == null) return;
        try {
            byte[] png = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            EvidenceManager.addShot(png, label);
        } catch (Exception e) {
            System.out.println("Final screenshot skipped: " + e.getMessage());
        }
    }

    private void buildPdf(String testName) {
        try {
            String pdf = EvidenceManager.buildPdf(testName);
            if (pdf != null) {
                System.out.println("Evidence PDF created: " + pdf);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            EvidenceManager.reset();
        }
    }
}
