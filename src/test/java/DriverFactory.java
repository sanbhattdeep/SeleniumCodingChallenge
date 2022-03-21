import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverFactory {

    public static WebDriver Build(String type, String browser) {

        if (type.equals("local")) {
            return buildChromeDriver();

        } else if (type.equals("remote")) {
            try {
                return buildRemoteDriver(browser);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else {
            throw new InvalidArgumentException("Invalid type, type should be local or remote");
        }
        return null;
    }

    private static RemoteWebDriver buildRemoteDriver(String browser) throws MalformedURLException {
        var DOCKER_GRID_HUB_URI = new URL("http://localhost:4444/wd/hub");

        RemoteWebDriver driver;

        switch (browser) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setCapability("browserVersion", "");
                chromeOptions.setCapability("platformName", "LINUX");
                chromeOptions.addArguments("--start-maximized");

                driver = new RemoteWebDriver(DOCKER_GRID_HUB_URI, chromeOptions);
                break;

            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setCapability("browserVersion", "");
                firefoxOptions.setCapability("platformName", "LINUX");

                driver = new RemoteWebDriver(DOCKER_GRID_HUB_URI, firefoxOptions);
                break;

            default:
                throw new InvalidArgumentException(String.format("browser %s is not supported remotely", browser));
        }

        return driver;
    }

    private static ChromeDriver buildChromeDriver() {
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver();
    }
}

