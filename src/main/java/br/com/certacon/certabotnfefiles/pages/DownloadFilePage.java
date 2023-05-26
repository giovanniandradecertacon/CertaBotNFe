package br.com.certacon.certabotnfefiles.pages;

import br.com.certacon.certabotnfefiles.utils.ProcessStatus;
import br.com.certacon.certabotnfefiles.vos.DownloadFileVO;
import org.openqa.selenium.By;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class DownloadFilePage {
    private final String ip;

    public DownloadFilePage(@Value("${config.ip}") String ip) {
        this.ip = ip;
    }

    public ProcessStatus downloadFiles(DownloadFileVO fileVO, RemoteWebDriver remoteWebDriver) throws InterruptedException {
        remoteWebDriver.navigate().to(fileVO.getRemoteDriverDownload());
        Thread.sleep(1000);
        remoteWebDriver.switchTo().newWindow(WindowType.TAB);
        Thread.sleep(1000);
        boolean exists = Boolean.FALSE;
        do {
            SessionId sessionId = remoteWebDriver.getSessionId();
            String resourceUrl = ip + ":4444/download/" + sessionId.toString();
            remoteWebDriver.navigate().to(resourceUrl);
            Thread.sleep(1000);
            remoteWebDriver.navigate().refresh();
            Thread.sleep(1000);
            String filePresence = new WebDriverWait(remoteWebDriver, Duration.ofSeconds(100))
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/pre"))).getText();
            remoteWebDriver.navigate().to("about:blank");
            Thread.sleep(1000);
            if (filePresence.equals(fileVO.getFileName())) exists = Boolean.TRUE;
            else Thread.sleep(1000);
        } while (exists != Boolean.TRUE);
        return ProcessStatus.DOWNLOADED;
    }
}
