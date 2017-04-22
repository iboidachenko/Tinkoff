package com.tinkoff;

import lib.Lib;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;

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
    private static String регион;


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
       регион = scriptProperties.getProperty("region");
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
        if(Lib.isElementPresent(By.xpath("//span[@class='ui-link payment-page__title_inner' and contains (text(), 'Москве')]")) == true){
            System.out.println("Регион \"Москва\"");
        } else {
            System.out.println("Выбрать регион \"Москва\"");
            Lib.driver.findElement(By.xpath("//span[@class='ui-link payment-page__title_inner']")).click();
            Lib.waitElement(By.xpath("//div[@class='ui-input__column']/input[@class='ui-input__field']"));
            Lib.driver.findElement(By.xpath("//div[@class='ui-input__column']/input[@class='ui-input__field']")).click();
            System.out.println("Заполнить поле \"Поиск\" значением \""+регион+"\"");
            Lib.driver.findElement(By.xpath("//div[@class='ui-input__column']/input[@class='ui-input__field']")).clear();
            Lib.driver.findElement(By.xpath("//div[@class='ui-input__column']/input[@class='ui-input__field']")).sendKeys(регион);
            System.out.println("Выбрать найденное значение");
            Lib.waitElement(By.xpath("//div[@class='ui-regions__item']/span[@class='ui-link ui-link_active' and contains (text(), ${регион})]"));
            Lib.driver.findElement(By.xpath("//div[@class='ui-regions__item']/span[@class='ui-link ui-link_active' and contains (text(), ${регион})]")).click();

            Lib.waitElement(By.xpath("//span[@class='ui-link__text' and contains (text(), 'ЖКУ-Москва')]"));
        }
    }
//_____________________________________________________________________________________
    @Test
    public void eвыборПровайдера() throws Exception {

    }
//_____________________________________________________________________________________
    @AfterClass
        public static void finishTest() throws Exception {
        Lib.logout();
        }
}
