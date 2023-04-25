package br.com.certacon.certabotnfefiles.services;

import br.com.certacon.certabotnfefiles.configurations.RemoteWebDriverConfig;
import br.com.certacon.certabotnfefiles.helpers.SeleniumHelperComponent;
import br.com.certacon.certabotnfefiles.utils.NfeStatus;
import br.com.certacon.certabotnfefiles.vos.NfeFileForLoginVO;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

@Service
public class LoginTabService {
    private final SeleniumHelperComponent helper;

    public LoginTabService(SeleniumHelperComponent helper) {
        this.helper = helper;
    }

    public NfeStatus loginInput(NfeFileForLoginVO nfeFileForLoginVO) throws InterruptedException, MalformedURLException {
        RemoteWebDriverConfig config = new RemoteWebDriverConfig();
        String ip = "http://192.168.0.123";
        NfeStatus status = null;
        try {
            RemoteWebDriver remoteWebDriver = new RemoteWebDriver(new URL(ip + ":4444/wd/hub"), config.chromeOptions());

            Boolean goToUrl = helper.navigateToUrl(remoteWebDriver, nfeFileForLoginVO.getRemoteDriverUpload());
            if(goToUrl.equals(Boolean.TRUE)) {
                Optional<WebElement> usernameElement = helper.findElementByIdWithSendKeys(1000L, 30L, "geo-login-name", nfeFileForLoginVO.getUsername(), remoteWebDriver);
                if(usernameElement.isPresent()) {
                    Optional<WebElement> passwordElement = helper.findElementByXpathAndSendKeysString(1000L, 30L, "/html/body/div[3]/div[2]/div/div[2]/div[2]/div/input", nfeFileForLoginVO.getPassword(), remoteWebDriver);
                    if (passwordElement.isPresent()){
                        Optional<WebElement> loginElement = helper.findElementByXpathAndSendKeys(1000L, 30L, "/html/body/div[3]/div[2]/div/div[2]/div[3]/a", Keys.ENTER, remoteWebDriver);
                        if(loginElement.isPresent()) status = NfeStatus.LOGGED;
                    }
                }
            }else{
                status = NfeStatus.ERROR;
            }
        }catch (RuntimeException e){
            status = NfeStatus.ERROR;
            throw new RuntimeException("Houve Falha no Login!");
        }finally {
            return status;
        }
    }
}
