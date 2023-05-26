package br.com.certacon.certabotnfefiles.pages;

import br.com.certacon.certabotnfefiles.helpers.SeleniumHelperComponent;
import br.com.certacon.certabotnfefiles.models.ProcessFileModel;
import br.com.certacon.certabotnfefiles.repositories.ProcessFileRepository;
import br.com.certacon.certabotnfefiles.utils.ProcessStatus;
import br.com.certacon.certabotnfefiles.vos.ProcessFileForLoginVO;
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
public class LoginCertaconWebPage {

    private final SeleniumHelperComponent helper;

    private final ProcessFileRepository processRepository;

    public LoginCertaconWebPage(SeleniumHelperComponent helper, ProcessFileRepository processRepository) {
        this.processRepository = processRepository;
        this.helper = helper;
    }

    public ProcessFileModel loginInput(ProcessFileForLoginVO processFileVO, RemoteWebDriver remoteWebDriver) {
        Optional<ProcessFileModel> fileModel = processRepository.findById(processFileVO.toModel().getId());
        ProcessStatus loginStatus = null;
        try {

            Boolean goToUrl = helper.navigateToUrl(remoteWebDriver, processFileVO.getRemoteDriverUpload());
            if (goToUrl.equals(Boolean.TRUE)) {
                Optional<WebElement> usernameElement = helper.findElementByIdWithSendKeys(1000L, 30L, "geo-login-name", processFileVO.getUsername(), remoteWebDriver);
                if (usernameElement.isPresent()) {
                    Optional<WebElement> passwordElement = helper.findElementByXpathAndSendKeysString(1000L, 30L, "/html/body/div[3]/div[2]/div/div[2]/div[2]/div/input", processFileVO.getPassword(), remoteWebDriver);
                    if (passwordElement.isPresent()) {
                        Optional<WebElement> loginElement = helper.findElementByXpathAndSendKeys(1000L, 30L, "/html/body/div[3]/div[2]/div/div[2]/div[3]/a", Keys.ENTER, remoteWebDriver);

                        Optional<WebElement> errorMessage = helper.findByXpathAndSendKeysForError(10L, remoteWebDriver, "class^=[modal fade show]");
                        if (errorMessage.isPresent()) {
                            WebElement errorMessageButton = new WebDriverWait(remoteWebDriver, Duration.ofSeconds(10))
                                    .until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div/div/button")));
                            errorMessageButton.click();
                            helper.findElementByIdWithSendKeys(1000L, 30L, "geo-login-name", processFileVO.getUsername(), remoteWebDriver);
                            helper.findElementByXpathAndSendKeysString(1000L, 30L, "/html/body/div[3]/div[2]/div/div[2]/div[2]/div/input", processFileVO.getPassword(), remoteWebDriver);
                            helper.findElementByXpathAndSendKeys(1000L, 30L, "/html/body/div[3]/div[2]/div/div[2]/div[3]/a", Keys.ENTER, remoteWebDriver);
                        }

                        if (loginElement.isPresent()) loginStatus = ProcessStatus.LOGGED;
                    }
                }
            } else {
                loginStatus = ProcessStatus.ERROR;
            }
        } catch (RuntimeException e) {
            loginStatus = ProcessStatus.ERROR;
            throw new RuntimeException("Houve Falha no Login!");
        } finally {
            fileModel.get().setStatus(loginStatus);
            return fileModel.get();
        }
    }
}
