package com.lambdatest.ltOptions_w3c;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class androidWeb_ltOptions_w3c {

    String username = System.getenv("LT_USERNAME") == null ? "LT_USERNAME" //Enter the Username here
            : System.getenv("LT_USERNAME");
    String accessKey = System.getenv("LT_ACCESS_KEY") == null ? "LT_ACCESS_KEY"  //Enter the Access key here
            : System.getenv("LT_ACCESS_KEY");
    public static RemoteWebDriver driver = null;
    public String gridURL = "@mobile-hub.lambdatest.com/wd/hub";
    public String status = "passed";
    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        HashMap<String, Object> ltOptions = new HashMap<String, Object>();
        ltOptions.put("w3c", true);
        ltOptions.put("console", true);
        ltOptions.put("network", true);
        ltOptions.put("visual", true);
        ltOptions.put("enableCustomTranslation", true);
        ltOptions.put("platformName", "android");
        ltOptions.put("deviceName", ".*");
        //        ltOptions.put("platformVersion", "10");
        ltOptions.put("deviceOrientation", "PORTRAIT");
        ltOptions.put("build", "JUNIT_lt:options_w3c");
        ltOptions.put("name", "android_lt:options_w3c");
        ltOptions.put("isRealMobile", true);
        capabilities.setCapability("lt:options", ltOptions);
        try
        {
            driver = new RemoteWebDriver(new URL("https://" + username + ":" + accessKey + gridURL), capabilities);
        }
        catch (MalformedURLException e)
        {
            System.out.println("Invalid grid URL");
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testSimple() throws Exception
    {
        try
        {
            driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
            driver.get("https://mfml.in/api/getInfo");
            driver.getWindowHandles().forEach(handle -> System.out.println(handle));
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.elementToBeClickable(By.id("resolution"))).click();

            wait.until(ExpectedConditions.elementToBeClickable(By.id("location"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.id("details"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.id("timezone"))).click();

            status="passed";
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            status="failed";
        }
    }
    @After
    public void tearDown() throws Exception
    {
        if (driver != null)
        {
            driver.executeScript("lambda-status=" + status);
            driver.quit();
        }
    }

}