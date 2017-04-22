package com.tinkoff;

import lib.Lib;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.By;

/**
 * Created by iboidachenko on 21.04.17.
 */
public class OneTest {
    //Путь к файлу с настройками
    private static String pathToSettings = "src\\main\\resources\\Settings.properties";
    //Настройки
    private static String pathToDriver;
    private static String url;

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
    }
//______________________________________________________________________________________

    @Test
    public void открытиеТестовойСреды() throws Exception {
        System.out.println("Открытие стартовой страницы");
        Lib.openLoginPage(pathToDriver, url);
        Lib.waitElement(By.xpath("//span[@class='ui-link__text' and contains (text(), 'Платежи')]"));
		}
//_____________________________________________________________________________________
    @Test
    public void переходНаПлатежи() throws Exception {
        System.out.println("Нажатие");
        Lib.driver.findElement(By.xpath("//span[@class='ui-link__text' and contains (text(), 'Платежи')]")).click();
        Lib.waitElement(By.xpath("//div[@class='payment-page__header']/h1[contains (text(), 'Платежи')]"));
        }
    //_____________________________________________________________________________________
    @AfterClass
        public static void finishTest() throws Exception {
        Lib.logout();
        }
}
