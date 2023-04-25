package br.com.certacon.certabotnfefiles.helpers;

import br.com.certacon.certabotnfefiles.utils.NfeStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;

@Component
public class SeleniumHelperComponent {
    public Boolean navigateToUrl(RemoteWebDriver remoteWebDriver, String urlToNavigate){
        Boolean navigated = null;
        try{
            remoteWebDriver.manage().window().maximize();
            remoteWebDriver.navigate().to(urlToNavigate);
            navigated = Boolean.TRUE;
        }catch (RuntimeException e){
            navigated = Boolean.FALSE;
            throw new RuntimeException("Falha ao navegar pra URL passada");
        }finally {
            return navigated;
        }

    }

    public Optional<WebElement> findElementByXpathAndSendKeysString(Long sleepTime, Long wait, String xpath, String keys, RemoteWebDriver remoteWebDriver) throws InterruptedException {
        Thread.sleep(sleepTime);
        WebElement sendBox = new WebDriverWait(remoteWebDriver, Duration.ofSeconds(wait))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
        sendBox.sendKeys(keys);

        return Optional.ofNullable(sendBox);
    }

    public Optional<WebElement> findElementByXpathAndSendKeys(Long sleepTime, Long wait, String xpath, Keys keys, RemoteWebDriver remoteWebDriver) throws InterruptedException {
        Thread.sleep(sleepTime);
        WebElement sendBox = new WebDriverWait(remoteWebDriver, Duration.ofSeconds(wait))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
        sendBox.sendKeys(keys);

        return Optional.ofNullable(sendBox);
    }

    public Optional<WebElement> findElementByIdWithSendKeys(Long sleepTime, Long wait, String id, String keys, RemoteWebDriver remoteWebDriver) throws InterruptedException {
        Thread.sleep(sleepTime);
        WebElement sendBox = new WebDriverWait(remoteWebDriver, Duration.ofSeconds(wait))
                .until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
        sendBox.sendKeys(keys);

        return Optional.of(sendBox);
    }

    public Optional<WebElement> findElementByClassNameWithSendKeys(Long sleepTime, Long wait, String className, String keys, RemoteWebDriver remoteWebDriver) throws InterruptedException {
        Thread.sleep(sleepTime);
        WebElement sendBox = new WebDriverWait(remoteWebDriver, Duration.ofSeconds(wait))
                .until(ExpectedConditions.presenceOfElementLocated(By.className(className)));
        sendBox.sendKeys(keys);

        return Optional.ofNullable(sendBox);
    }

    public Optional<WebElement> findElementByCssSelectorWithSendKeys(Long sleepTime, Long wait, String cssSelector, String keys, RemoteWebDriver remoteWebDriver) throws InterruptedException {
        Thread.sleep(sleepTime);
        WebElement sendBox = new WebDriverWait(remoteWebDriver, Duration.ofSeconds(wait))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(cssSelector)));
        sendBox.sendKeys(keys);

        return Optional.ofNullable(sendBox);
    }

    public Optional<WebElement> findElementByXpathToBeClickableAndClick(Long sleepTime, Long wait, String xpath, RemoteWebDriver remoteWebDriver) throws InterruptedException {
        Thread.sleep(sleepTime);
        WebElement clickBox = new WebDriverWait(remoteWebDriver, Duration.ofSeconds(wait))
                .until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        clickBox.click();

        return Optional.ofNullable(clickBox);
    }


}
