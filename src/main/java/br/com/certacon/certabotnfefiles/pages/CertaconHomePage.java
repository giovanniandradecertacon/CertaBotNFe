package br.com.certacon.certabotnfefiles.pages;

import br.com.certacon.certabotnfefiles.helpers.SeleniumHelperComponent;
import br.com.certacon.certabotnfefiles.repositories.NfeFileRepository;
import br.com.certacon.certabotnfefiles.utils.NfeStatus;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.util.Optional;

@Service
public class CertaconHomePage {
    private final SeleniumHelperComponent helper;
    public CertaconHomePage(SeleniumHelperComponent helper) {
        this.helper = helper;
    }

    public NfeStatus closeAndNavigate(RemoteWebDriver remoteWebDriver) throws MalformedURLException {

        NfeStatus homePageStatus = null;

        try {
            Optional<WebElement> tutorialElement = helper.findElementByXpathAndSendKeys(1000L, 30L, "/html/body/div[3]/div/div/button", Keys.ESCAPE, remoteWebDriver);
            if(tutorialElement.isPresent()){
                Optional<WebElement> filterFrameElement = helper.switchToFrameByClassName(1000L, 30L, "geo-smart-iframe", remoteWebDriver);
                if(filterFrameElement.isPresent()){
                    Optional<WebElement> filterElement = helper.findElementNotButtonByXpathAndClick(1000L, 30L, "/html/body/div[17]/div[1]/div/div/div/div/div[2]/div[4]/div/div", remoteWebDriver);
                    if(filterElement.isPresent()){
                        remoteWebDriver.switchTo().parentFrame();
                        Optional<WebElement> sideMenuElement = helper.findElementByXpathAndClick(1000L, 30L, "/html/body/div[4]/div[1]/div/div/button", remoteWebDriver);
                        if(sideMenuElement.isPresent()){
                            Optional<WebElement> uploadFilesElement = helper.findElementNotButtonByXpathAndClick(1000L, 30L,"/html/body/div[4]/div[2]/div/div[1]/div/ul/li[4]/div/ul/li[1]/div/ul/li", remoteWebDriver);
                            if(uploadFilesElement.isPresent()){
                                homePageStatus = NfeStatus.CHANGED;
                            }
                        }

                    }
                }

            }
        }catch (InterruptedException e){
            homePageStatus = NfeStatus.ERROR;
            throw new RuntimeException(e.getMessage(), e.getCause());
        }finally {
            return homePageStatus;
        }
    }
}
