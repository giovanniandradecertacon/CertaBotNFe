package br.com.certacon.certabotnfefiles.helpers;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;

@Component
public class SeleniumHelperComponent {
    public Boolean navigateToUrl(RemoteWebDriver remoteWebDriver, String urlToNavigate) {
        Boolean navigated = null;
        try {
            remoteWebDriver.manage().window().maximize();
            remoteWebDriver.navigate().to(urlToNavigate);
            navigated = Boolean.TRUE;
        } catch (RuntimeException e) {
            navigated = Boolean.FALSE;
            throw new RuntimeException("Falha ao navegar pra URL passada");
        } finally {
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

    public Optional<WebElement> findElementByXpathAndClick(Long sleepTime, Long wait, String xpath, RemoteWebDriver remoteWebDriver) throws InterruptedException {
        Thread.sleep(sleepTime);
        WebElement clickSpace = new WebDriverWait(remoteWebDriver, Duration.ofSeconds(wait))
                .until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        clickSpace.click();
        return Optional.ofNullable(clickSpace);
    }

    public Optional<WebElement> findElementNotButtonByXpathAndClick(Long sleepTime, Long wait, String xpath, RemoteWebDriver remoteWebDriver) throws InterruptedException {
        Thread.sleep(sleepTime);
        WebElement clickSpace = new WebDriverWait(remoteWebDriver, Duration.ofSeconds(wait))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
        clickSpace.click();

        return Optional.ofNullable(clickSpace);
    }

    public Optional<WebElement> findElementByXpathAndDoubleClick(Long sleepTime, Long wait, String xpath, RemoteWebDriver remoteWebDriver) throws InterruptedException {
        Thread.sleep(sleepTime);
        Actions actions = new Actions(remoteWebDriver);
        WebElement clickSpace = new WebDriverWait(remoteWebDriver, Duration.ofSeconds(wait))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
        actions.doubleClick(clickSpace).perform();

        return Optional.ofNullable(clickSpace);

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

    public Optional<WebElement> switchToFrameByClassName(Long sleepTime, Long wait, String className, RemoteWebDriver remoteWebDriver) throws InterruptedException {
        Thread.sleep(sleepTime);
        WebElement frame = new WebDriverWait(remoteWebDriver, Duration.ofSeconds(wait))
                .until(ExpectedConditions.presenceOfElementLocated(By.className(className)));
        remoteWebDriver.switchTo().frame(frame);
        return Optional.of(frame);
    }

    public Optional<WebElement> findElementByCssSelectorWithSendKeys(Long sleepTime, Long wait, String cssSelector, String keys, RemoteWebDriver remoteWebDriver) throws InterruptedException {
        Thread.sleep(sleepTime);
        WebElement sendBox = new WebDriverWait(remoteWebDriver, Duration.ofSeconds(wait))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(cssSelector)));
        sendBox.sendKeys(keys);

        return Optional.ofNullable(sendBox);
    }

    public String findElementByXpathAndGetCssValue(Long sleepTime, Long wait, String xpath, String cssValue, RemoteWebDriver remoteWebDriver) throws InterruptedException {
        Thread.sleep(sleepTime);
        WebElement cssValueFromElement = new WebDriverWait(remoteWebDriver, Duration.ofSeconds(wait))
                .until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));

        return cssValueFromElement.getCssValue(cssValue);
    }

    public Optional<WebElement> findElementByXpathAnGetText(Long sleepTime, Long wait, String xpath, RemoteWebDriver remoteWebDriver) throws InterruptedException {
        WebElement textValueFromElement = null;
        try {
            Thread.sleep(sleepTime);
            textValueFromElement = new WebDriverWait(remoteWebDriver, Duration.ofSeconds(wait))
                    .until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } finally {
            return Optional.ofNullable(textValueFromElement);
        }
    }

    public Optional<WebElement> findElementIsPresent(Long sleepTime, Long wait, String xpath, RemoteWebDriver remoteWebDriver) {
        WebElement element = null;
        try {
            Thread.sleep(sleepTime);
            element = new WebDriverWait(remoteWebDriver, Duration.ofSeconds(wait))
                    .until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            return Optional.ofNullable(element);
        }
    }

    public Optional<WebElement> findByXpathAndSendKeysForError(Long duration, RemoteWebDriver remoteWebDriver, String className) {
        WebElement errorMessage = null;
        try {
            errorMessage = new WebDriverWait(remoteWebDriver, Duration.ofSeconds(duration))
                    .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(className)));
            errorMessage.sendKeys(Keys.ESCAPE);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } finally {
            return Optional.ofNullable(errorMessage);
        }


    }

}
