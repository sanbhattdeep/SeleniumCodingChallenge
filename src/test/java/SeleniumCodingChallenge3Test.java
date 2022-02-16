import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class SeleniumCodingChallenge3Test {

    private WebDriver driver;
    protected static final Logger LOGGER = LoggerFactory.getLogger(SeleniumCodingChallenge3Test.class);


    @org.testng.annotations.BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @org.testng.annotations.AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testAddMaxPriceProductToCart() {
        driver.get("https://saucedemo.com/");

        driver.findElement(By.cssSelector("#user-name")).sendKeys("standard_user");
        driver.findElement(By.cssSelector("#password")).sendKeys("secret_sauce");
        driver.findElement(By.cssSelector("#login-button")).click();
        //Get list of WebElements containing Prices and store as BigDecimal List
        List<BigDecimal> itemPriceList = new ArrayList<>();
        List<WebElement> itemPriceElementsList = driver.findElements(By.cssSelector("div.inventory_item_description >" +
                " div" +
                ".pricebar > " +
                "div"));
        itemPriceElementsList.forEach(item -> {
            LOGGER.info(String.format("Item Price: %s", item.getText()));
            itemPriceList.add(new BigDecimal(item.getText().replace("$", StringUtils.EMPTY)));
        });
        //Sort the list so that max price is stored at end of list
        itemPriceList.sort(Comparator.naturalOrder());
        //Get max price item and click on Add to Cart which has the max price
        String maxPrice = itemPriceList.get(itemPriceList.size() - 1).toString();
        LOGGER.info(String.format("Max Price is:%s", maxPrice));
        driver.findElement(By.xpath(String.format("//div[text()='%s']//following-sibling::button[contains(@id," +
                "'add-to-cart')]", maxPrice))).click();
        //Assert that Item is added to cart successfully by checking that Button text has changed to Remove
        assertTrue(driver.findElement(By.xpath(String.format("//div[text()='%s']//following-sibling::button" +
                "[contains(@id,'remove')]", maxPrice))).isDisplayed());
    }
}