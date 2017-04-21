package lib;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.BeforeClass;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by iboidachenko on 21.04.17.
 */
public class Lib {

    public static WebDriver driver;
    private static WebDriverWait wait;

    //______________________________________________________________________________________
    /**
     * Функция открытия страницы авторизации пользователя
     *
     * @String pathToDriver - путь к драйверу
     * @String url - ссылка на тестовую среду
     */
    public static void openLoginPage (String url) throws Exception {
        //Открытие браузера
        driver = new ChromeDriver();
        //Открытие тестовой среды
        driver.get(url);
        //Окно браузера во весь экран
        driver.manage().window().maximize();
    }
//______________________________________________________________________________________

}
