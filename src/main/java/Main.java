

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;


import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;



public class Main {
    public static WebDriver driver;
    public static WebDriverWait myWaitVar;

    public static void main(String[] args) throws InterruptedException, IOException {
        System.out.println(System.getenv("PATH"));
        System.out.println(System.getenv("HOME"));
        System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver");
        FirefoxOptions options = new FirefoxOptions();
        driver = new FirefoxDriver(options);
        myWaitVar = new WebDriverWait(driver, 10);
        driver.get("https://www.basketball-reference.com/");
        BasketballScrapper basketballScrapper = new BasketballScrapper(driver, myWaitVar);
        basketballScrapper.start();
        System.out.println(" -- BYE --");
        driver.quit();

    }
}