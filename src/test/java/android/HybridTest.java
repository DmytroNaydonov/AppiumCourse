package android;

import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import static org.openqa.selenium.By.id;
import static org.openqa.selenium.By.xpath;

public class HybridTest extends BaseAndroidTest {

    @Test
    public void browserTest() {
        login();
        addToCart("Air Jordan 4 Retro");
        driver.findElement(id("com.androidsample.generalstore:id/appbar_btn_cart")).click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(id("com.androidsample.generalstore:id/toolbar_title"), "Cart"));
        driver.findElement(id("com.androidsample.generalstore:id/btnProceed")).click();

        driver.getContextHandles();
        driver.context("WEBVIEW_com.androidsample.generalstore");

        var acceptCookiesButton = driver.findElement(xpath("//button[@id='W0wltc']"));

        try {
            acceptCookiesButton.click();
        } catch (ElementClickInterceptedException e) {
            acceptCookiesButton.click();
        }

        driver.findElement(xpath("//textarea[@name = 'q']")).sendKeys("Dimonaz");
        driver.pressKey(new KeyEvent(AndroidKey.ENTER));
    }
}
