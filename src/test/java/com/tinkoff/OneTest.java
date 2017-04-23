package com.tinkoff;

import lib.Lib;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import java.io.FileInputStream;
import java.util.Properties;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by iboidachenko on 21.04.17.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OneTest {
    //Путь к файлу с настройками
    private static String pathToSettings = "src\\main\\resources\\Settings.properties";
    //Настройки
    private static String pathToDriver;
    private static String url;

    //Путь к файлу с параметрами
    private static String pathToProperties = "src\\main\\resources\\Properties.properties";
    //Параметры
    private static String регионМосква;
    private static String регионПитер;
    private static String искомыйПровайдер;
    private static String urlШаг5;


   @BeforeClass
    public static void initialize() throws Exception {
        //Инициализация специального объекта Properties
        //типа Hashtable для удобной работы с данными
        Properties scriptSettings = new Properties();

        //Обращение к файлу и получение данных
        scriptSettings.load(new FileInputStream(pathToSettings));
        pathToDriver = scriptSettings.getProperty("pathToDriver");
        url = scriptSettings.getProperty("url");
        //Печать полученных данных в консоль
        System.out.println("Cсылка на тестовую среду - "+url);

       //Инициализация специального объекта Properties
       //типа Hashtable для удобной работы с данными
       Properties scriptProperties = new Properties();
       //Обращение к файлу и получение данных
       scriptProperties.load(new FileInputStream(pathToProperties));
       регионМосква = scriptProperties.getProperty("regionM");
       регионПитер = scriptProperties.getProperty("regionP");
    }
//______________________________________________________________________________________
    @Test

    public void aоткрытиеТестовойСреды() throws Exception {
        System.out.println("Открытие стартовой страницы");
        Lib.openLoginPage(pathToDriver, url);
        Lib.waitElement(By.xpath("//span[@class='ui-link__text' and contains (text(), 'Платежи')]"));
    }
//_____________________________________________________________________________________
    @Test
    public void bпереходНаПлатежи() throws Exception {
        System.out.println("Нажатие на \"Платежи\"");
        Lib.driver.findElement(By.xpath("//span[@class='ui-link__text' and contains (text(), 'Платежи')]")).click();
        Lib.waitElement(By.xpath("//div[@class='payment-page__header']/h1[contains (text(), 'Платежи')]"));
    }
//_____________________________________________________________________________________
    @Test
    public void cпереходНаВыборПровайдера() throws Exception {
        System.out.println("Нажатие на \"Коммунальные платежи\"");
        Lib.waitElement(By.xpath("//span[@class='ui-link__text' and contains (text(), 'Коммунальные платежи')]"));
        Lib.driver.findElement(By.xpath("//span[@class='ui-link__text' and contains (text(), 'Коммунальные платежи')]")).click();
        Lib.waitElement(By.xpath("//div[@class='payment-page__header']/h1[contains (text(), 'Коммунальные платежи')]"));
    }
//_____________________________________________________________________________________
    @Test
    public void dпроверитьРегион() throws Exception {
        System.out.println("Проверить, что регион \"Москва\"");
        Lib.waitElement(By.xpath("//span[@class='ui-link payment-page__title_inner']"));
        if(Lib.waitElementTF(By.xpath("//span[@class='ui-link payment-page__title_inner' and contains (., 'Москве')]")) == true){
            System.out.println("Регион \"Москва\"");
        } else {
            System.out.println("Выбрать регион \"Москва\"");
            Lib.driver.findElement(By.xpath("//span[@class='ui-link payment-page__title_inner']")).click();
            Lib.waitElement(By.xpath("//div[@class='ui-input__column']/input[@class='ui-input__field']"));
            Lib.driver.findElement(By.xpath("//div[@class='ui-input__column']/input[@class='ui-input__field']")).click();
            System.out.println("Заполнить поле \"Поиск\" значением \""+регионМосква+"\"");
            Lib.driver.findElement(By.xpath("//div[@class='ui-input__column']/input[@class='ui-input__field']")).clear();
            Lib.driver.findElement(By.xpath("//div[@class='ui-input__column']/input[@class='ui-input__field']")).sendKeys(регионМосква);
            System.out.println("Выбрать найденное значение");
            Lib.waitElement(By.xpath(String.format("//div[@class='ui-regions__item']/span[@class='ui-link ui-link_active' and contains (text(), '%s')]",регионМосква)));
            Lib.driver.findElement(By.xpath(String.format("//div[@class='ui-regions__item']/span[@class='ui-link ui-link_active' and contains (text(), '%s')]",регионМосква))).click();
            Lib.waitElement(By.xpath("//ul[@class='ui-menu ui-menu_icons']"));
        }
    }
//_____________________________________________________________________________________
    @Test
    public void eвыборПровайдера() throws Exception {
        System.out.println("Выбрать провайдера \"ЖКУ-Москва\"");
        Lib.waitElement(By.xpath("//span[@class='ui-link__text' and contains (text(), 'ЖКУ-Москва')]"));
        искомыйПровайдер = Lib.driver.findElement(By.xpath("//span[@class='ui-link__text' and contains (text(), 'ЖКУ-Москва')]")).getText();
        Lib.driver.findElement(By.xpath("//span[@class='ui-link__text' and contains (text(), 'ЖКУ-Москва')]")).click();
        Lib.waitElement(By.xpath("//span[@class='ui-menu-second__title' and contains (text(), 'Оплатить ЖКУ в Москве')]"));
        urlШаг5 = Lib.driver.getCurrentUrl();
    }
//_____________________________________________________________________________________
    @Test
    public void fпереходНаВкладкуПлатеж() throws Exception {
        System.out.println("Перейти на вкладку \"Платеж\"");
        Lib.driver.findElement(By.xpath("//span[@class='ui-menu-second__title' and contains (text(), 'Оплатить ЖКУ в Москве')]")).click();
        Lib.waitElement(By.xpath("//a[contains(@class, 'ui-menu-second__link_active')]"));
    }
//_____________________________________________________________________________________
    @Test
    public void gпроверкаПолей() throws Exception {
        System.out.println("Отрицательные проверки полей на НЕвалидные значения");
        System.out.println("Проверка поля \"Код плательщика\"");
        Lib.waitElement(By.xpath("//div[@class='ui-input__column']/input[@name='provider-payerCode']"));
        Lib.driver.findElement(By.xpath("//div[@class='ui-input__column']/input[@name='provider-payerCode']")).click();
        Lib.driver.findElement(By.xpath("//div[@class='ui-input__column']/input[@name='provider-payerCode']")).clear();
        Lib.driver.findElement(By.xpath("//div[@class='ui-input__column']/input[@name='provider-payerCode']")).sendKeys("test");
        Lib.pressEnter();
        assertEquals("Поле неправильно заполнено", Lib.driver.findElement(By.xpath("//div[contains(@class, 'ui-form__row_text')]/descendant::div[contains(@class, 'ui-form-field-error-message_ui-form')]")).getText());

        System.out.println("Проверка поля \"Период\"");
        Lib.waitElement(By.xpath("//div[@class='ui-input__column']/input[@name='provider-period']"));
        Lib.driver.findElement(By.xpath("//div[@class='ui-input__column']/input[@name='provider-period']")).click();
        Lib.driver.findElement(By.xpath("//div[@class='ui-input__column']/input[@name='provider-period']")).clear();
        Lib.driver.findElement(By.xpath("//div[@class='ui-input__column']/input[@name='provider-period']")).sendKeys("99.9999");
        Lib.pressEnter();
        assertEquals("Поле заполнено некорректно", Lib.driver.findElement(By.xpath("//div[contains(@class, 'ui-form__row_date')]/descendant::div[contains(@class, 'ui-form-field-error-message_ui-form')]")).getText());

        System.out.println("Проверка поля \"Сумма\"");
        assertEquals("Поле обязательное", Lib.driver.findElement(By.xpath("//div[@class='ui-form__row ui-form__row_amount']/descendant::div[contains(@class, 'ui-form-field-error-message_ui-form')]")).getText());
        Lib.waitElement(By.xpath("//div[@class='ui-form__row ui-form__row_amount ui-form__row_default-error-view-visible']/descendant::input[@class='ui-input__field']"));
        Lib.driver.findElement(By.xpath("//div[@class='ui-form__row ui-form__row_amount ui-form__row_default-error-view-visible']/descendant::input[@class='ui-input__field']")).click();
        Lib.driver.findElement(By.xpath("//div[@class='ui-form__row ui-form__row_amount ui-form__row_default-error-view-visible']/descendant::input[@class='ui-input__field']")).clear();
        Lib.driver.findElement(By.xpath("//div[@class='ui-form__row ui-form__row_amount ui-form__row_default-error-view-visible']/descendant::input[@class='ui-input__field']")).sendKeys("999 999 999 999");
        Lib.pressEnter();
        assertEquals("Максимальная сумма перевода - 15 000 \u20BD", Lib.driver.findElement(By.xpath("//div[@class='ui-form__row ui-form__row_amount ui-form__row_default-error-view-visible']/descendant::div[contains(@class, 'ui-form-field-error-message_ui-form')]")).getText());

        System.out.println("Положительные проверки полей на валидные значения");
        System.out.println("Проверка поля \"Код плательщика\"");
        Lib.waitElement(By.xpath("//div[@class='ui-input__column']/input[@name='provider-payerCode']"));
        Lib.driver.findElement(By.xpath("//div[@class='ui-input__column']/input[@name='provider-payerCode']")).click();
        Lib.driver.findElement(By.xpath("//div[@class='ui-input__column']/input[@name='provider-payerCode']")).clear();
        Lib.driver.findElement(By.xpath("//div[@class='ui-input__column']/input[@name='provider-payerCode']")).sendKeys("1234567890");
        Lib.pressEnter();
        Lib.checkObjectIsEnabled(By.xpath("//div[contains(@class, 'ui-form__row_text')]/descendant::div[contains(@class, 'ui-form-field-error-message_ui-form')]"));

        System.out.println("Проверка поля \"Период\"");
        Lib.waitElement(By.xpath("//div[@class='ui-input__column']/input[@name='provider-period']"));
        Lib.driver.findElement(By.xpath("//div[@class='ui-input__column']/input[@name='provider-period']")).click();
        Lib.driver.findElement(By.xpath("//div[@class='ui-input__column']/input[@name='provider-period']")).clear();
        Lib.driver.findElement(By.xpath("//div[@class='ui-input__column']/input[@name='provider-period']")).sendKeys("01.2017");
        Lib.pressEnter();
        Lib.checkObjectIsEnabled(By.xpath("//div[contains(@class, 'ui-form__row_date')]/descendant::div[contains(@class, 'ui-form-field-error-message_ui-form')]"));

        System.out.println("Проверка поля \"Сумма страхования\"");
        Lib.waitElement(By.xpath("//div[@class='ui-form__row ui-form__row_amount']/descendant::input[contains(@class, 'ui-input__field')]"));
        Lib.driver.findElement(By.xpath("//div[@class='ui-form__row ui-form__row_amount']/descendant::input[contains(@class, 'ui-input__field')]")).click();
        Lib.driver.findElement(By.xpath("//div[@class='ui-form__row ui-form__row_amount']/descendant::input[contains(@class, 'ui-input__field')]")).clear();
        Lib.driver.findElement(By.xpath("//div[@class='ui-form__row ui-form__row_amount']/descendant::input[contains(@class, 'ui-input__field')]")).sendKeys("150");
        Lib.pressEnter();

        System.out.println("Проверка поля \"Сумма\"");
        Lib.waitElement(By.xpath("//div[@class='ui-form__row ui-form__row_amount ui-form__row_default-error-view-visible']/descendant::input[contains(@class, 'ui-input__field')]"));
        Lib.driver.findElement(By.xpath("//div[@class='ui-form__row ui-form__row_amount ui-form__row_default-error-view-visible']/descendant::input[contains(@class, 'ui-input__field')]")).click();
        Lib.driver.findElement(By.xpath("//div[@class='ui-form__row ui-form__row_amount ui-form__row_default-error-view-visible']/descendant::input[contains(@class, 'ui-input__field')]")).clear();
        Lib.driver.findElement(By.xpath("//div[@class='ui-form__row ui-form__row_amount ui-form__row_default-error-view-visible']/descendant::input[contains(@class, 'ui-input__field')]")).sendKeys("11000");
        Lib.pressEnter();
        Lib.checkObjectIsEnabled(By.xpath("//div[@class='ui-form__row ui-form__row_amount ui-form__row_default-error-view-visible']/descendant::div[contains(@class, 'ui-form-field-error-message_ui-form')]"));
    }
//_____________________________________________________________________________________
    @Test
    public void hпереходНаПлатежи() throws Exception {
        System.out.println("Перейти на вкладку \"Платеж\"");
        Lib.waitElement(By.xpath("//div[@class='ui-menu-primary__item']/a/span[contains(text(), 'Платежи')]"));
        Lib.driver.findElement(By.xpath("//div[@class='ui-menu-primary__item']/a/span[contains(text(), 'Платежи')]")).click();
        Lib.waitElement(By.xpath("//div[@class='ui-search-input__input-wrapper']/input"));
    }
//_____________________________________________________________________________________
    @Test
    public void iпоискПровайдера() throws Exception {
        System.out.println("Поиск провайдера");
        Lib.driver.findElement(By.xpath("//div[@class='ui-search-input__input-wrapper']/input")).clear();
        Lib.driver.findElement(By.xpath("//div[@class='ui-search-input__input-wrapper']/input")).sendKeys(искомыйПровайдер);
        Lib.waitElement(By.xpath("//div[@class='ui-search-flat__box']"));
   }
//_____________________________________________________________________________________
    @Test
    public void jпроверкаПровайдераВсписке() throws Exception {
        System.out.println("Проверка, что икомый провайдер первый в списке");
        assertEquals(искомыйПровайдер, Lib.driver.findElement(By.xpath("//div[@class='ui-search-flat__title-box']")).getText());
    }
//_____________________________________________________________________________________
    @Test
    public void kпереходПоСсылкеПровайдера() throws Exception {
        System.out.println("Выбор искомого провайдера");
        Lib.driver.findElement(By.xpath("//div[@class='ui-search-flat__title-box']")).click();
        Lib.waitElement(By.xpath("//span[@class='ui-link__text' and contains (text(), 'Платежи')]"));
        System.out.println("Проверка открытия страницы, что и на шаге 5");
        assertEquals(urlШаг5, Lib.driver.getCurrentUrl());
    }
//_____________________________________________________________________________________
    @Test
    public void lпереходПлатежиКоммунальныеПлатежи() throws Exception {
        System.out.println("Перейти на вкладку \"Платеж\"");
        Lib.waitElement(By.xpath("//div[@class='ui-menu-primary__item']/a/span[contains(text(), 'Платежи')]"));
        Lib.driver.findElement(By.xpath("//div[@class='ui-menu-primary__item']/a/span[contains(text(), 'Платежи')]")).click();
        Lib.waitElement(By.xpath("//div[@class='ui-search-input__input-wrapper']/input"));

        System.out.println("Нажатие на \"Коммунальные платежи\"");
        Lib.waitElement(By.xpath("//span[@class='ui-link__text' and contains (text(), 'Коммунальные платежи')]"));
        Lib.driver.findElement(By.xpath("//span[@class='ui-link__text' and contains (text(), 'Коммунальные платежи')]")).click();
        Lib.waitElement(By.xpath("//div[@class='payment-page__header']/h1[contains (text(), 'Коммунальные платежи')]"));
}
//_____________________________________________________________________________________
    @Test
    public void mвыбратьРегион() throws Exception {
        System.out.println("Выбрать регион \"г. Санкт-Петербург\"");
        Lib.waitElement(By.xpath("//span[@class='ui-link payment-page__title_inner']"));
        Lib.driver.findElement(By.xpath("//span[@class='ui-link payment-page__title_inner']")).click();
        Lib.waitElement(By.xpath("//div[@class='ui-input__column']/input[@class='ui-input__field']"));
        Lib.driver.findElement(By.xpath("//div[@class='ui-input__column']/input[@class='ui-input__field']")).click();
        System.out.println("Заполнить поле \"Поиск\" значением \""+регионПитер+"\"");
        Lib.driver.findElement(By.xpath("//div[@class='ui-input__column']/input[@class='ui-input__field']")).clear();
        Lib.driver.findElement(By.xpath("//div[@class='ui-input__column']/input[@class='ui-input__field']")).sendKeys(регионПитер);
        System.out.println("Выбрать найденное значение");
        Lib.waitElement(By.xpath(String.format("//div[@class='ui-regions__item']/span[@class='ui-link' and contains (text(), '%s')]",регионПитер)));
        Lib.driver.findElement(By.xpath(String.format("//div[@class='ui-regions__item']/span[@class='ui-link' and contains (text(), '%s')]",регионПитер))).click();
        Lib.waitElement(By.xpath("//ul[@class='ui-menu ui-menu_icons']"));
}
//_____________________________________________________________________________________
    @Test
    public void nпроверкаСпискаПровайдеров() throws Exception {
        System.out.println("Проверить, что в списке провайдеров не отображается искомый провайдер");
        assertFalse(Lib.driver.findElement(By.xpath("//ul[@class='ui-menu ui-menu_icons']")).getText().contains(искомыйПровайдер));
}
//_____________________________________________________________________________________
    @AfterClass
        public static void finishTest() throws Exception {
        Lib.logout();
        }
}
