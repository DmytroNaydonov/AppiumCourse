package android;

import com.google.common.collect.ImmutableMap;
import helpers.GestureHelper;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import lombok.SneakyThrows;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.net.URI;
import java.nio.file.Paths;
import java.time.Duration;

import static org.openqa.selenium.By.id;
import static org.openqa.selenium.By.xpath;

public class BaseTest {

    private static final String DEVICE_NAME = "DmytroPhone";
    private final static String URL = "127.0.0.1";
    private final static int PORT = 4723;
    private AppiumDriverLocalService service;
    protected final AndroidDriver driver;
    protected final WebDriverWait wait;
    protected GestureHelper gestureHelper;

    @SneakyThrows
    public BaseTest() {
        var options = new UiAutomator2Options();
        options.setDeviceName(DEVICE_NAME);
        var appPath = Paths.get("C:", "Users", "dmytro.naydonov", "IdeaProjects", "AppiumCourse", "src", "test", "resources", "General-Store.apk").toString();
        options.setApp(appPath);
        //options.setCapability("browserName", "Chrome"); //FOR BROWSER TESTING
        driver = new AndroidDriver(new URI("http://%s:%s".formatted(URL, PORT)).toURL(), options);
        gestureHelper = new GestureHelper(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @BeforeSuite
    public void setUp() {
        var mainJsFile = Paths.get("C:", "Users", "dmytro.naydonov", "AppData", "Roaming", "npm", "node_modules", "appium", "build", "lib", "main.js").toFile();

        service = new AppiumServiceBuilder()
                .withAppiumJS(mainJsFile)
                .withIPAddress(URL)
                .usingPort(PORT)
                .build();

        service.start();
    }

    @AfterSuite
    public void tearDown() {
        driver.quit();
        service.stop();
    }

    public void startActivity(String activity) {
        ((JavascriptExecutor) driver).executeScript("mobile: startActivity", ImmutableMap.of(
                "intent", activity
        ));
    }

    protected void addToCart(String productToSelect) {
        gestureHelper.scrollToElementWithText(productToSelect);

        var products = driver.findElements(xpath("//*[@resource-id='com.androidsample.generalstore:id/productImage']/parent::android.widget.LinearLayout"));

        for (WebElement product : products) {
            var productName = product.findElement(xpath("//*[@resource-id='com.androidsample.generalstore:id/productName']")).getText();
            if (productName.equals(productToSelect)) {
                product.findElement(xpath(".//*[@resource-id='com.androidsample.generalstore:id/productAddCart']")).click();
                break;
            }
        }
    }

    protected void login() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(id("com.androidsample.generalstore:id/splashscreen")));
        driver.findElement(id("com.androidsample.generalstore:id/nameField")).sendKeys("Dmytro");
        driver.findElement(id("com.androidsample.generalstore:id/btnLetsShop")).click();
    }
}
