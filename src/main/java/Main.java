

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;


import org.openqa.selenium.support.ui.WebDriverWait;



/**
 * Clase Main para ejecutar nuestro programa.
 */
public class Main {
    /**
     * Driver Inicial
     */
    public static WebDriver driver;
    /**
     * Condiciones para Driver Inicial
     */
    public static WebDriverWait myWaitVar;

    /**
     * Clase Principal del Programa. Llamamos a la clase BasketballScrapper.
     * @param args args
     */
    public static void main(String[] args) {
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