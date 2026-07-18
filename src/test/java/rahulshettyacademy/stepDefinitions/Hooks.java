package rahulshettyacademy.stepDefinitions;

import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Scenario;
import rahulshettyacademy.TestComponents.DriverManager;
import rahulshettyacademy.resources.EvidenceManager;

/**
 * Cucumber lifecycle hooks.
 *
 *  @AfterStep  -> runs after EVERY Gherkin step. Takes a screenshot,
 *                 stores it for the scenario's PDF, and also embeds it
 *                 in the Cucumber HTML report.
 *
 *  @After      -> runs once when the scenario finishes. Builds the
 *                 single PDF for that scenario, then quits the driver.
 *
 * Cucumber discovers this class automatically because it lives in the
 * glue package (rahulshettyacademy.stepDefinitions) already configured
 * in your TestNGTestRunner.
 */
public class Hooks {

    @AfterStep
    public void afterEachStep(Scenario scenario) {
        WebDriver driver = DriverManager.get();
        if (driver == null) {
            return; // driver not launched yet (e.g. before the first step) - skip
        }
        try {
            byte[] png = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

            // Embed inside the Cucumber HTML report. The per-scenario PDF is
            // fed automatically by ScreenshotListener, so we do NOT addShot here
            // (that would duplicate pages in the PDF).
            scenario.attach(png, "image/png", scenario.getName());

        } catch (Exception e) {
            // Never let a screenshot failure break the test.
            System.out.println("Screenshot skipped for a step: " + e.getMessage());
        }
    }

    @After
    public void afterScenario(Scenario scenario) {
        try {
            String pdf = EvidenceManager.buildPdf(scenario.getName());
            if (pdf != null) {
                System.out.println("Evidence PDF created: " + pdf);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            EvidenceManager.reset();
            WebDriver driver = DriverManager.get();
            if (driver != null) {
                driver.quit();
                DriverManager.remove();
            }
        }
    }
}
