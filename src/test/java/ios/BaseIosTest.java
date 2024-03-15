package ios;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import ios.helpers.IosGestureHelper;
import lombok.SneakyThrows;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

public abstract class BaseIosTest {
    private static final String DEVICE_NAME = "iPhone 14 Pro";
    private final static String URL = "127.0.0.1";
    private final static int PORT = 4723;
    private AppiumDriverLocalService service;
    protected final IOSDriver driver;
    protected final WebDriverWait wait;
    protected final IosGestureHelper gestureHelper;

    @SneakyThrows
    public BaseIosTest() {
        var options = new XCUITestOptions();
        options.setDeviceName(DEVICE_NAME);
        options.setApp("/" + Paths.get("Users", "dimonaz", "IdeaProjects", "AppiumCourse", "src", "test", "resources", "UIKitCatalog.app"));
        options.setPlatformVersion("16.4");
        options.setWdaLaunchTimeout(Duration.ofSeconds(20));
        //options.setCapability("browserName", "Chrome"); //FOR BROWSER TESTING
        driver = new IOSDriver(new URI("http://%s:%s".formatted(URL, PORT)).toURL(), options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.gestureHelper = new IosGestureHelper(driver);
    }

    @BeforeSuite
    public void setUp() {
        var indexJsFile = Path.of("/" + Paths.get("usr", "local", "lib", "node_modules", "appium", "index.js")).toFile();

        service = new AppiumServiceBuilder()
                .withAppiumJS(indexJsFile)
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
}
