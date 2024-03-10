package android;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import static java.lang.Double.parseDouble;
import static org.assertj.core.api.Assertions.assertThat;
import static org.openqa.selenium.By.id;
import static org.openqa.selenium.By.xpath;

public class GeneralStoreTest extends BaseTest {

    @Test
    public void fillForm() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(id("com.androidsample.generalstore:id/splashscreen")));
        driver.findElement(id("com.androidsample.generalstore:id/spinnerCountry")).click();
        var argentinaCountryOption = gestureHelper.scrollToElementWithText("Argentina");
        argentinaCountryOption.click();
        driver.findElement(id("com.androidsample.generalstore:id/btnLetsShop")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(xpath("//android.widget.Toast[@text='Please enter your name']")));
        var errorMessage = driver.findElement(xpath("//android.widget.Toast[@text='Please enter your name']")).getText();

        assertThat(errorMessage).as("Error message is as displayed").isEqualTo("Please enter your name");

        driver.findElement(id("com.androidsample.generalstore:id/nameField")).sendKeys("Dmytro");
        driver.findElement(id("com.androidsample.generalstore:id/btnLetsShop")).click();
    }

    @Test
    public void addToCart() {
        var productToSelect = "Jordan 6 Rings";
        login();
        addToCart(productToSelect);

        driver.findElement(id("com.androidsample.generalstore:id/appbar_btn_cart")).click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(id("com.androidsample.generalstore:id/toolbar_title"), "Cart"));
        var addedProductName = driver.findElement(xpath("//*[@resource-id='com.androidsample.generalstore:id/productName']")).getText();

        assertThat(addedProductName).as("Name of added product is correct").isEqualTo(productToSelect);
    }

    @Test
    public void cartSum() {
        login();
        addToCart("Converse All Star");
        addToCart("Jordan 6 Rings");

        driver.findElement(id("com.androidsample.generalstore:id/appbar_btn_cart")).click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(id("com.androidsample.generalstore:id/toolbar_title"), "Cart"));

        var expectedSum = 0.0;
        var prices = driver.findElements(xpath("//*[@resource-id='com.androidsample.generalstore:id/productPrice']"));

        for (WebElement price : prices) {
            expectedSum += parseDouble(price.getText().split("\\$")[1]);
        }

        var sum = parseDouble(
                driver.findElement(id("com.androidsample.generalstore:id/totalAmountLbl"))
                .getText()
                .split("\\$ ")[1]
        );

        assertThat(expectedSum).as("Sums match").isEqualTo(sum);

        driver.findElement(xpath("//android.widget.CheckBox")).click();
        driver.findElement(id("com.androidsample.generalstore:id/btnProceed")).click();
    }

    @Test
    public void termsTest() {
        login();
        addToCart("Converse All Star");
        driver.findElement(id("com.androidsample.generalstore:id/appbar_btn_cart")).click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(id("com.androidsample.generalstore:id/toolbar_title"), "Cart"));
        var termsButton = driver.findElement(id("com.androidsample.generalstore:id/termsButton"));
        gestureHelper.longPress(termsButton, 2000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(xpath("//android.widget.TextView[@text = 'Terms Of Conditions']")));

        var message = driver.findElement(id("android:id/message")).getText();

        assertThat(message).as("Message is correct").contains("Lorem Ipsum");
    }
}
