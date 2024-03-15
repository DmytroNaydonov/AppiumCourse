package ios;

import com.google.common.collect.ImmutableMap;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.annotations.Test;

import static io.appium.java_client.AppiumBy.accessibilityId;
import static io.appium.java_client.AppiumBy.iOSClassChain;
import static ios.enums.ScrollDirection.DOWN;
import static org.assertj.core.api.Assertions.assertThat;

public class iosGestureTest extends BaseIosTest {

    @Test
    public void iosLongPressTest() {
        driver.findElement(accessibilityId("Steppers")).click();
        var incrementButton = driver.findElement(iOSClassChain("**/XCUIElementTypeButton[`name == 'Increment'`][3]"));
        gestureHelper.longPress(incrementButton, 4);

        var counter = driver.findElement(iOSClassChain("**/XCUIElementTypeStaticText[4]")).getAttribute("value");

        assertThat(counter).as("").isEqualTo("10");
    }

    @Test
    public void pickerViewTest() {
       driver.findElement(accessibilityId("Picker View")).click();
       driver.findElement(accessibilityId("Red color component value")).sendKeys("80");
       driver.findElement(accessibilityId("Green color component value")).sendKeys("220");
       driver.findElement(accessibilityId("Blue color component value")).sendKeys("105");
    }
}
