package ios.helpers;

import com.google.common.collect.ImmutableMap;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

public class IosGestureHelper {

    private final WebDriver driver;

    public IosGestureHelper(WebDriver driver) {
        this.driver = driver;
    }

    public void  longPress(WebElement element, int duration) {
        ((JavascriptExecutor) driver).executeScript("mobile: touchAndHold", ImmutableMap.of(
                "elementId", ((RemoteWebElement) element).getId(),
                "duration", duration
        ));
    }
}
