package com.tinkoff;

import lib.Lib;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

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
        System.out.println("Претензии_Претензии в cервисном запросе_63602");

        System.out.println("Открытие страницы авторизации пользователя");
        Lib.openLoginPage(pathToDriver, url);
		}
//_____________________________________________________________________________________
    @AfterClass
        public static void finishTest() throws Exception {
        Lib.logout();
}
}
