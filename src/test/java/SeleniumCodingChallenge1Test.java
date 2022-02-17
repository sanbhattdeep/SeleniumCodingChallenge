import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class SeleniumCodingChallenge1Test {
    private WebDriver driver;
    protected static final Logger LOGGER = LoggerFactory.getLogger(SeleniumCodingChallenge1Test.class);


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
    public void testWorldPopulationInfo() {
        driver.get("https://www.worldometers.info/world-population/");
        driver.manage().window().maximize();
        LocalDateTime then = LocalDateTime.now();
        while (true) {

            //Get and Print Current World Population
            LOGGER.info("Current World Population: " + driver.findElement(By.cssSelector("#maincounter-wrap > div")).getText());
            //Get and Print TODAY Births,Deaths,Population Growth today
            LOGGER.info("Births today: " + driver.findElement(By.cssSelector("div:nth-child(1) > div > div:nth-child" +
                    "(2) > div.sec-counter")).getText());
            LOGGER.info("Deaths today: " + driver.findElement(By.cssSelector("div:nth-child(1) > div > div:nth-child" +
                    "(3)" +
                    " > div.sec-counter")).getText());
            LOGGER.info("Population Growth today: " + driver.findElement(By.cssSelector("div:nth-child(1) > div > " +
                    "div:nth-child(4)" +
                    " > div.sec-counter")).getText());
            //Get and Print THIS YEAR Births,Deaths,Population Growth today
            LOGGER.info("Births this year: " + driver.findElement(By.cssSelector("div:nth-child(2) > div > " +
                    "div:nth-child(2) > div.sec-counter")).getText());
            LOGGER.info("Deaths this year: " + driver.findElement(By.cssSelector("div:nth-child(2) > div > " +
                    "div:nth-child(3)" +
                    " > div.sec-counter")).getText());
            LOGGER.info("Population Growth this year: " + driver.findElement(By.cssSelector("div:nth-child(2) > div >" +
                    " " +
                    "div:nth-child(4)" +
                    " > div.sec-counter")).getText());
            //break loop after 20 secs
            if (ChronoUnit.SECONDS.between(then, LocalDateTime.now()) >= 20) {
                break;
            }
        }
    }
}
