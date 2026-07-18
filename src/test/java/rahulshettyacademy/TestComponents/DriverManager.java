package rahulshettyacademy.TestComponents;

import org.openqa.selenium.WebDriver;

/**
 * Holds the active WebDriver in a ThreadLocal so that Cucumber hooks
 * (Hooks.java) can reach the SAME driver instance that your step
 * definitions created in the "Given I landed on Ecommerce Page" step.
 *
 * ThreadLocal also keeps each parallel scenario isolated - important
 * when you run scenarios in parallel later.
 */
public class DriverManager {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    public static void set(WebDriver driver) {
        DRIVER.set(driver);
    }

    public static WebDriver get() {
        return DRIVER.get();
    }

    public static void remove() {
        DRIVER.remove();
    }
}
