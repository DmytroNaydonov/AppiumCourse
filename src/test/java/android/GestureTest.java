package android;

import org.testng.annotations.Test;

import static enums.SwipeDirection.*;
import static io.appium.java_client.AppiumBy.accessibilityId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.openqa.selenium.By.id;
import static org.openqa.selenium.By.xpath;

public class GestureTest extends BaseTest {

    @Test
    public void longPressTest() {
        driver.findElement(accessibilityId("Views")).click();
        driver.findElement(accessibilityId("Expandable Lists")).click();
        driver.findElement(accessibilityId("1. Custom Adapter")).click();

        var peopleNames = driver.findElement(xpath("//*[@text='People Names']"));
        gestureHelper.longPress(peopleNames, 2000);
        var popup = driver.findElement(xpath("//*[@text='Sample menu']"));

        assertThat(popup.isDisplayed()).as("Popup is visible").isTrue();

        assertThat(popup.getText())
                .as("People Names pop up title is as expected")
                .isEqualTo("Sample menu");
    }

    @Test
    public void scrollTest() {
        driver.findElement(accessibilityId("Views")).click();
        gestureHelper.scrollToElementWithText("WebView");
    }

    @Test
    public void swipeTest() {
        driver.findElement(accessibilityId("Views")).click();
        driver.findElement(accessibilityId("Gallery")).click();
        driver.findElement(accessibilityId("1. Photos")).click();
        var firstImage = driver.findElement(xpath("(//android.widget.ImageView)[1]"));

        assertThat(firstImage.getAttribute("focusable"))
                .as("First image is focusable")
                .isEqualTo("true");

        gestureHelper.swipeElement(firstImage, LEFT, 0.75);
        firstImage = driver.findElement(xpath("(//android.widget.ImageView)[1]"));

        assertThat(firstImage.getAttribute("focusable"))
                .as("First image is not focusable")
                .isEqualTo("false");
    }

    @Test
    public void dragAndDropTest() {
        driver.findElement(accessibilityId("Views")).click();
        driver.findElement(accessibilityId("Drag and Drop")).click();

        var source = driver.findElement(id("io.appium.android.apis:id/drag_dot_1"));
        gestureHelper.dragAndDrop(source, 837, 743);

        var resultText = driver.findElement(id("io.appium.android.apis:id/drag_result_text")).getText();

        assertThat(resultText).as("Result text is as expected").isEqualTo("Dropped!");
    }
}
