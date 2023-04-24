package br.com.certacon.certabotnfefiles.helpers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;

@Component
public class SeleniumHelperComponent {
    public Optional<WebElement> findElementByXpathWithSendKeys(Long sleepTime, Long wait, String xpath, String keys, RemoteWebDriver remoteWebDriver) throws InterruptedException {
        Thread.sleep(sleepTime);
        WebElement box = new WebDriverWait(remoteWebDriver, Duration.ofSeconds(wait))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
        box.sendKeys(keys);

        return Optional.ofNullable(box);
    }

    public Optional<WebElement> findElementByIdWithSendKeys(Long sleepTime, Long wait, String id, String keys, RemoteWebDriver remoteWebDriver) throws InterruptedException {
        Thread.sleep(sleepTime);
        WebElement box = new WebDriverWait(remoteWebDriver, Duration.ofSeconds(wait))
                .until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
        box.sendKeys(keys);

        return Optional.ofNullable(box);
    }

    public Optional<WebElement> findElementByClassNameWithSendKeys(Long sleepTime, Long wait, String className, String keys, RemoteWebDriver remoteWebDriver) throws InterruptedException {
        Thread.sleep(sleepTime);
        WebElement box = new WebDriverWait(remoteWebDriver, Duration.ofSeconds(wait))
                .until(ExpectedConditions.presenceOfElementLocated(By.className(className)));
        box.sendKeys(keys);

        return Optional.ofNullable(box);
    }

    public Optional<WebElement> findElementByCssSelectorWithSendKeys(Long sleepTime, Long wait, String cssSelector, String keys, RemoteWebDriver remoteWebDriver) throws InterruptedException {
        Thread.sleep(sleepTime);
        WebElement box = new WebDriverWait(remoteWebDriver, Duration.ofSeconds(wait))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(cssSelector)));
        box.sendKeys(keys);

        return Optional.ofNullable(box);
    }


}
