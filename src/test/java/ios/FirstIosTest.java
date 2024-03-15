package ios;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import static io.appium.java_client.AppiumBy.*;
import static org.assertj.core.api.Assertions.assertThat;

public class FirstIosTest extends BaseIosTest {

    @Test
    public void test() {
        driver.findElement(accessibilityId("Alert Views")).click();
        driver.findElement(iOSClassChain("**/XCUIElementTypeStaticText[`name == 'Text Entry'`]")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(iOSClassChain("**/XCUIElementTypeAlert[`name == 'A Short Title Is Best'`]")));
        driver.findElement(iOSClassChain("**/XCUIElementTypeTextField")).sendKeys("New title");
        driver.findElement(accessibilityId("OK")).click();

        driver.findElement(iOSNsPredicateString("type = 'XCUIElementTypeStaticText' AND value = 'Confirm / Cancel'")).click();
        var message = driver.findElement(iOSNsPredicateString("value BEGINSWITH[c] 'A message'")).getText();

        assertThat(message).as("Message").isEqualTo("A message should be a short, complete sentence.");

        driver.findElement(accessibilityId("Confirm")).click();
    }
}
