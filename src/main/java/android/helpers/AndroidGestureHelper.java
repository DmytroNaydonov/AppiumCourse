package android.helpers;

import com.google.common.collect.ImmutableMap;
import android.enums.SwipeDirection;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import static io.appium.java_client.AppiumBy.androidUIAutomator;

public class AndroidGestureHelper {

    private final WebDriver driver;

    public AndroidGestureHelper(WebDriver driver) {
        this.driver = driver;
    }

    public void  longPress(WebElement element, int duration) {
        ((JavascriptExecutor) driver).executeScript("mobile: longClickGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) element).getId(),
                "duration", duration
        ));
    }

    public WebElement scrollToElementWithText(String text) {
        return driver.findElement(androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"%s\"));".formatted(text)));
    }

    public void swipeElement(WebElement element, SwipeDirection direction, double percent) {
        ((JavascriptExecutor) driver).executeScript("mobile: swipeGesture", ImmutableMap.of(
                "element", ((RemoteWebElement) element).getId(),
                "direction", direction.toString(),
                "percent", percent
        ));
    }

    public void dragAndDrop(WebElement element, int endX, int endY) {
        ((JavascriptExecutor) driver).executeScript("mobile: dragGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) element).getId(),
                "endX", endX,
                "endY", endY
        ));
    }
}
