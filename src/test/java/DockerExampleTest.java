//go to lolesports.com
//click on standings tab
//assert first place team

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DockerExampleTest {


    @Test
    public void testDefaultFilter() {
        WebDriver driver = DriverFactory.Build("remote", "chrome");
        WebDriverWait wait = new WebDriverWait(driver, 15);

        goTo(driver, wait);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".button.stage-option.selected")));
        driver.findElement(By.cssSelector(".button.stage-option.selected")).click();
        Assert.assertEquals(driver.findElement(By.cssSelector("a:nth-child(2) > div.team > div.team-info > div.name")).getText(), "Team Liquid");

        tearDown(driver);
    }

    @Test
    public void testLCSAcademyFilter() {
        WebDriver driver = DriverFactory.Build("remote", "chrome");
        WebDriverWait wait = new WebDriverWait(driver, 15);

        goTo(driver, wait);
        driver.findElement(By.xpath(String.format("//div[contains(text(),'%s')]//parent::div//parent::button", "LCS " +
                "Academy"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".button.stage-option.selected")));
        driver.findElement(By.cssSelector(".button.stage-option.selected")).click();
        driver.findElement(By.cssSelector(".button.stage-option.selected")).click();
        Assert.assertEquals(driver.findElement(By.cssSelector("a:nth-child(2) > div.team > div.team-info > div.name")).getText(), "CLG Academy");

        tearDown(driver);
    }

    private void goTo(WebDriver driver, WebDriverWait wait) {
        driver.manage().window().maximize();
        driver.get("https://lolesports.com");
        driver.navigate().to("https://lolesports.com/standings");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".league.selected")));
    }

    private void tearDown(WebDriver driver) {
        if (driver != null) {
            driver.quit();
        }
    }
}
