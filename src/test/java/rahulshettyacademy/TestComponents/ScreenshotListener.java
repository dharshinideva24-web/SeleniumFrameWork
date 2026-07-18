package rahulshettyacademy.TestComponents;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;

import rahulshettyacademy.resources.EvidenceManager;

/**
 * Automatic, hands-free screenshots.
 *
 * Selenium's EventFiringDecorator calls these hooks whenever the driver
 * navigates or an element is clicked / typed into. We grab a screenshot
 * at each of those points and hand it to EvidenceManager, which later
 * builds the per-test PDF.
 *
 * Result: your TestNG tests get "screenshot at every page" WITHOUT any
 * manual captureStep(...) calls in the test code.
 *
 * The BUSY guard prevents a screenshot call from recursively triggering
 * more screenshots.
 */
public class ScreenshotListener implements WebDriverListener {

    private static final ThreadLocal<Boolean> BUSY = ThreadLocal.withInitial(() -> false);

    private void snap(String label) {
        if (Boolean.TRUE.equals(BUSY.get())) return;   // re-entrancy guard
        WebDriver driver = DriverManager.get();
        if (driver == null) return;
        try {
            BUSY.set(true);
            byte[] png = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            EvidenceManager.addShot(png, label);
        } catch (Exception e) {
            // Never let a screenshot failure break a test.
        } finally {
            BUSY.set(false);
        }
    }

    @Override
    public void afterGet(WebDriver driver, String url) {
        snap("Navigated to: " + url);
    }

    @Override
    public void afterClick(WebElement element) {
        snap("After click");
    }

    // Optional: also capture after typing into a field.
    @Override
    public void afterSendKeys(WebElement element, CharSequence... keysToSend) {
        snap("After input");
    }
}
