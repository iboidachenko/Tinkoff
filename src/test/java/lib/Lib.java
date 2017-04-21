package lib;

import static org.junit.Assert.fail;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by iboidachenko on 21.04.17.
 */
public class Lib {

    public static WebDriver driver;
    private static WebDriverWait wait;

    //Путь к файлу с настройками
    private static String pathToSettings = "Settings.properties";
    //Настройки
    private static String pathToScreenshotsWithErrors;
//______________________________________________________________________________________
    /**
     * Функция открытия страницы авторизации пользователя
     *
     * @String pathToDriver - путь к драйверу
     * @String url - ссылка на тестовую среду
     */
    public static void openLoginPage (String pathToDriver, String url) throws Exception {
        //Открытие браузера
        System.setProperty(
                "webdriver.chrome.driver",
                pathToDriver
        );
        driver = new ChromeDriver();
        //Открытие тестовой среды
        driver.get(url);
        //Окно браузера во весь экран
        driver.manage().window().maximize();
    }
//______________________________________________________________________________________
    /**
     * Функция выхода из системы
     */
    public static void logout() throws Exception {
        StringBuffer verificationErrors = new StringBuffer();
        driver.quit();
        //Если ошибка при выходе
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }
//______________________________________________________________________________________

}
