package br.com.certacon.certabotnfefiles.pages;

import br.com.certacon.certabotnfefiles.helpers.SeleniumHelperComponent;
import br.com.certacon.certabotnfefiles.models.NfeFileModel;
import br.com.certacon.certabotnfefiles.repositories.NfeFileRepository;
import br.com.certacon.certabotnfefiles.utils.NfeStatusForSeleniumSchedule;
import br.com.certacon.certabotnfefiles.vos.NfeFileForLoginVO;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LoginCertaconWebPage {

    private final SeleniumHelperComponent helper;

    private final NfeFileRepository fileRepository;

    public LoginCertaconWebPage(SeleniumHelperComponent helper, NfeFileRepository fileRepository) {
        this.fileRepository = fileRepository;
        this.helper = helper;
    }

    public NfeFileModel loginInput(NfeFileForLoginVO nfeFileForLoginVO, RemoteWebDriver remoteWebDriver) {
        Optional<NfeFileModel> fileModel = fileRepository.findById(nfeFileForLoginVO.toModel().getId());
        NfeStatusForSeleniumSchedule loginStatus = null;
        try {

            Boolean goToUrl = helper.navigateToUrl(remoteWebDriver, nfeFileForLoginVO.getRemoteDriverUpload());
            if (goToUrl.equals(Boolean.TRUE)) {
                Optional<WebElement> usernameElement = helper.findElementByIdWithSendKeys(1000L, 30L, "geo-login-name", nfeFileForLoginVO.getUsername(), remoteWebDriver);
                if (usernameElement.isPresent()) {
                    Optional<WebElement> passwordElement = helper.findElementByXpathAndSendKeysString(1000L, 30L, "/html/body/div[3]/div[2]/div/div[2]/div[2]/div/input", nfeFileForLoginVO.getPassword(), remoteWebDriver);
                    if (passwordElement.isPresent()) {
                        Optional<WebElement> loginElement = helper.findElementByXpathAndSendKeys(1000L, 30L, "/html/body/div[3]/div[2]/div/div[2]/div[3]/a", Keys.ENTER, remoteWebDriver);
                        if (loginElement.isPresent()) loginStatus = NfeStatusForSeleniumSchedule.LOGGED;
                    }
                }
            } else {
                loginStatus = NfeStatusForSeleniumSchedule.ERROR;
            }
        } catch (RuntimeException e) {
            loginStatus = NfeStatusForSeleniumSchedule.ERROR;
            throw new RuntimeException("Houve Falha no Login!");
        } finally {
            fileModel.get().setStatus(loginStatus);
            return fileModel.get();
        }
    }
}
