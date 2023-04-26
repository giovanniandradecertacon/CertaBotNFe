package br.com.certacon.certabotnfefiles.schedules;

import br.com.certacon.certabotnfefiles.configurations.RemoteWebDriverConfig;
import br.com.certacon.certabotnfefiles.models.NfeFileModel;
import br.com.certacon.certabotnfefiles.pages.CertaconHomePage;
import br.com.certacon.certabotnfefiles.pages.LoginCertaconWebPage;
import br.com.certacon.certabotnfefiles.pages.UploadFilesPage;
import br.com.certacon.certabotnfefiles.repositories.NfeFileRepository;
import br.com.certacon.certabotnfefiles.utils.NfeStatus;
import br.com.certacon.certabotnfefiles.vos.NfeFileForLoginVO;
import br.com.certacon.certabotnfefiles.vos.NfeFileForSearchCnpjVO;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Service
public class SeleniumPagesSchedule {
    private final LoginCertaconWebPage loginPage;
    private final CertaconHomePage homePage;
    private final UploadFilesPage uploadPage;
    private final NfeFileRepository nfeFileRepository;
    @Value("${config.ip}")
    private String ip;

    public SeleniumPagesSchedule(LoginCertaconWebPage loginPage, CertaconHomePage homePage, UploadFilesPage filesPage, NfeFileRepository nfeFileRepository) {
        this.loginPage = loginPage;
        this.homePage = homePage;
        this.uploadPage = filesPage;
        this.nfeFileRepository = nfeFileRepository;
    }

    @Scheduled(fixedRate = 30000)
    public boolean pagesSchedule() throws MalformedURLException {
        List<NfeFileModel> nfeFilesList = nfeFileRepository.findAll();

        if (!nfeFilesList.isEmpty()) {
            RemoteWebDriverConfig config = new RemoteWebDriverConfig();
            RemoteWebDriver remoteWebDriver = new RemoteWebDriver(new URL(ip + ":4444/wd/hub"), config.chromeOptions());

            nfeFilesList.forEach(nfeFile -> {
                if (nfeFile.getStatus() == NfeStatus.READY) {
                    try {
                        NfeFileForLoginVO nfeLoginVO = NfeFileForLoginVO.builder()
                                .id(nfeFile.getId())
                                .remoteDriverUpload(nfeFile.getRemoteDriverUpload())
                                .username(nfeFile.getUsername())
                                .password(nfeFile.getPassword())
                                .build();
                        nfeFile = loginPage.loginInput(nfeLoginVO, remoteWebDriver);
                        if (nfeFile.getStatus() == NfeStatus.LOGGED) {
                            NfeStatus homePageStatus = homePage.closeAndNavigate(remoteWebDriver);
                            nfeFile.setStatus(homePageStatus);
                            if (nfeFile.getStatus() == NfeStatus.CHANGED) {
                                NfeFileForSearchCnpjVO nfeSearchVO = NfeFileForSearchCnpjVO.builder()
                                        .cnpj(nfeFile.getCnpj())
                                        .nomeEmpresa(nfeFile.getNomeEmpresa())
                                        .idForSearch(nfeFile.getId())
                                        .build();
                                nfeFile = uploadPage.navigateOnUploadPage(nfeSearchVO, remoteWebDriver);
                                nfeFile.getStatus();
                            }
                        }
                    } catch (RuntimeException e) {
                        throw new RuntimeException();
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } finally {
                        remoteWebDriver.quit();
                    }
                }
            });
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
