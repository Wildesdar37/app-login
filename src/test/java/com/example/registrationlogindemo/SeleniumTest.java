package com.example.registrationlogindemo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SeleniumTest {

    private WebDriver webDriver;

    @Before
    public void setup() {
        WebDriverManager.firefoxdriver().setup();
        webDriver =  new FirefoxDriver();
    }

    @Test
    public void shouldBeOpenNavigator() {
        webDriver.get("https://google.com");
        webDriver.quit();
    }


    @After
    public void tearDown(){
        webDriver.quit();
    }

}
