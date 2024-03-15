package android;

import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.DeviceRotation;
import org.testng.annotations.Test;

import static io.appium.java_client.AppiumBy.*;
import static org.assertj.core.api.Assertions.assertThat;

public class WiFiTest extends BaseAndroidTest {

    @Test
    public void wifiTest() {
        driver.findElement(accessibilityId("Preference")).click();
        driver.findElement(accessibilityId("3. Preference dependencies")).click();
        driver.findElement(id("android:id/checkbox")).click();

        driver.rotate(new DeviceRotation(0, 0, 90));

        driver.findElement(xpath("//*[@text='WiFi settings']")).click();

        var title = driver.findElement(id("android:id/alertTitle")).getText();
        assertThat(title).as("Wifi settings pop up title is as expected").isEqualTo("WiFi settings");

        driver.setClipboardText("Dmytro wifi");
        driver.findElement(id("android:id/edit")).sendKeys(driver.getClipboardText());
        driver.findElement(id("android:id/button1")).click();
        driver.pressKey(new KeyEvent(AndroidKey.HOME));
    }

    @Test
    public void wifiTestWithActivity() {
        startActivity("io.appium.android.apis/io.appium.android.apis.preference.PreferenceDependencies");

        driver.findElement(id("android:id/checkbox")).click();
        driver.rotate(new DeviceRotation(0, 0, 90));
        driver.findElement(xpath("//*[@text='WiFi settings']")).click();

        var title = driver.findElement(id("android:id/alertTitle")).getText();
        assertThat(title).as("Wifi settings pop up title is as expected").isEqualTo("WiFi settings");

        driver.setClipboardText("Dmytro wifi");
        driver.findElement(id("android:id/edit")).sendKeys(driver.getClipboardText());
        driver.findElement(id("android:id/button1")).click();
        driver.pressKey(new KeyEvent(AndroidKey.HOME));
    }
}
