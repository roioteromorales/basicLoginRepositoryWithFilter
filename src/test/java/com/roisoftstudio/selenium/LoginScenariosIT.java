package com.roisoftstudio.selenium;

import com.roisoftstudio.selenium.helpers.SeleniumHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static com.roisoftstudio.Constants.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class LoginScenariosIT {

    public static final String HTTP_LOCALHOST_9090 = "http://localhost:9090/";
    private WebDriver driver;
    private SeleniumHelper seleniumHelper;

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        seleniumHelper = new SeleniumHelper(driver);
    }

    @After
    public void tearDown() throws Exception {
        driver.close();
    }

    @Test
    public void userCannotLogin_withWrongCredentials() throws Exception {
        seleniumHelper.login("fakeUsername", "fakePassword");

        assertThat(driver.getCurrentUrl().contains("Login"), is(true));
        assertThat(driver.findElement(By.id("errorBox")).getText(), is(INVALID_CREDENTIALS_ERROR_MESSAGE));
    }

    @Test
    public void userCanLogin_withValidCredentials() throws Exception {
        seleniumHelper.login("admin", "password");

        assertThat(driver.getCurrentUrl().contains(MAIN_PAGE), is(true));
        assertThat(driver.findElement(By.id("successBox")).getText().contains("Hola"), is(true));
    }

    @Test
    public void userCanLogout_withValidCredentials() throws Exception {
        seleniumHelper.login("admin", "password");
        driver.findElement(By.id("logout")).click();

        assertThat(driver.getCurrentUrl().contains(LOGIN_PAGE), is(true));
    }

    @Test
    public void userCannotAccessLoginProtectedPath_withoutLoginIn() throws Exception {
        checkProtectedPage(HTTP_LOCALHOST_9090 + PROTECTED_PATH + "MainPage.jsp");
        checkProtectedPage(HTTP_LOCALHOST_9090 + PROTECTED_PATH + "page1.jsp");
        checkProtectedPage(HTTP_LOCALHOST_9090 + PROTECTED_PATH + "page2.jsp");
        checkProtectedPage(HTTP_LOCALHOST_9090 + PROTECTED_PATH + "page3.jsp");
    }

    private void checkProtectedPage(String protectedPage) {
        driver.get(protectedPage);
        assertThat(driver.getCurrentUrl().contains(LOGIN_PAGE), is(true));
    }


}