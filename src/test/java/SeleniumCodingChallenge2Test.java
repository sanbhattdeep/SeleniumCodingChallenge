import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SeleniumCodingChallenge2Test {

    private WebDriver driver;
    protected static final Logger LOGGER = LoggerFactory.getLogger(SeleniumCodingChallenge2Test.class);


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
    public void testSortCarouselItems() {
        driver.get("https://noon.com/");
        driver.manage().window().maximize();
        //keep scrolling down page to make all carousels visible
        ((JavascriptExecutor) driver).executeScript("scroll(0,1000)");
        showCarouselItems("Recommended for you");
        ((JavascriptExecutor) driver).executeScript("scroll(0,2000)");
        showCarouselItems("Selling fast");
        ((JavascriptExecutor) driver).executeScript("scroll(0,3000)");
        showCarouselItems("Clearance deals");
        ((JavascriptExecutor) driver).executeScript("scroll(0,4000)");
        showCarouselItems("Healthcare essentials");
    }

    private void showCarouselItems(String carouselName) {
        //wait for carousel element
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format("//div//h3[contains(text()," +
                "'%s')]", carouselName))));
        //Get the Carousel Items
        List<WebElement> carouselWebElements = driver.findElements(By.xpath(String.format("//div//h3[contains(text()," +
                "'%s')" +
                "]//parent::div//following-sibling::div//div[@title]", carouselName)));
        List<String> carouselItemDescription = new ArrayList<>();
        //Store Item Description in List and sort
        carouselWebElements.forEach(item -> {
            carouselItemDescription.add(item.getAttribute("title"));
        });
        carouselItemDescription.sort(Comparator.naturalOrder());
        //Print Carousel Item names
        LOGGER.info(String.format("Printing Carousel Items in Sorted Order For Carousel: %s", carouselName));
        carouselItemDescription.forEach(System.out::println);
    }
}