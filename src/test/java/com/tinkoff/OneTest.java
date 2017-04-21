package com.tinkoff;

import lib.Lib;
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
    private static String pathToSettings = "Settings/Settings.properties";
    //Настройки
    private static String url;

   @BeforeClass
    public static void initialize() throws Exception {
        //Инициализация специального объекта Properties
        //типа Hashtable для удобной работы с данными
        Properties scriptSettings = new Properties();

        //Обращение к файлу и получение данных
        scriptSettings.load(new FileInputStream(pathToSettings));
        url = scriptSettings.getProperty("url");
        //Печать полученных данных в консоль
        System.out.println("Cсылка на тестовую среду - "+url);
    }

    //______________________________________________________________________________________

		@Test
		public void открытиеТстовойСреды() throws Exception {

			System.out.println("Претензии_Претензии в cервисном запросе_63602");

			System.out.println("Открытие страницы авторизации пользователя");
		    Lib.openLoginPage(url);




		}
//_____________________________________________________________________________________
}
