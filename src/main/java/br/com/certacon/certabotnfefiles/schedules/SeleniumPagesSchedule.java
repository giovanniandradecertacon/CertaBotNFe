package br.com.certacon.certabotnfefiles.schedules;

import br.com.certacon.certabotnfefiles.configurations.RemoteWebDriverConfig;
import br.com.certacon.certabotnfefiles.models.ProcessFileModel;
import br.com.certacon.certabotnfefiles.pages.CertaconHomePage;
import br.com.certacon.certabotnfefiles.pages.LoginCertaconWebPage;
import br.com.certacon.certabotnfefiles.pages.UploadFilesPage;
import br.com.certacon.certabotnfefiles.repositories.ProcessFileRepository;
import br.com.certacon.certabotnfefiles.utils.ProcessStatus;
import br.com.certacon.certabotnfefiles.vos.ProcessFileForLoginVO;
import br.com.certacon.certabotnfefiles.vos.ProcessFileForSearchVO;
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
    private final ProcessFileRepository processFileRepository;
    @Value("${config.ip}")
    private String ip;

    public SeleniumPagesSchedule(LoginCertaconWebPage loginPage, CertaconHomePage homePage, UploadFilesPage filesPage, ProcessFileRepository processFileRepository) {
        this.loginPage = loginPage;
        this.homePage = homePage;
        this.uploadPage = filesPage;
        this.processFileRepository = processFileRepository;
    }

    @Scheduled(fixedRate = 30000)
    public boolean pagesSchedule() throws MalformedURLException {
        List<ProcessFileModel> nfeFilesList = processFileRepository.findAll();

        if (!nfeFilesList.isEmpty()) {
            RemoteWebDriverConfig config = new RemoteWebDriverConfig();
            RemoteWebDriver remoteWebDriver = new RemoteWebDriver(new URL(ip + ":4444/wd/hub"), config.chromeOptions());

            nfeFilesList.forEach(processFileModel -> {
                if (processFileModel.getStatus() == ProcessStatus.CREATED) {
                    try {
                        ProcessFileForLoginVO processLoginVO = ProcessFileForLoginVO.builder()
                                .id(processFileModel.getId())
                                .remoteDriverUpload(processFileModel.getRemoteDriverUpload())
                                .username(processFileModel.getUsername())
                                .password(processFileModel.getPassword())
                                .build();
                        processFileModel = loginPage.loginInput(processLoginVO, remoteWebDriver);
                        if (processFileModel.getStatus() == ProcessStatus.LOGGED) {
                            ProcessStatus homePageStatus = homePage.closeAndNavigate(remoteWebDriver);
                            processFileModel.setStatus(homePageStatus);
                            if (processFileModel.getStatus() == ProcessStatus.CHANGED) {
                                ProcessFileForSearchVO processSearchVO = ProcessFileForSearchVO.builder()
                                        .cnpj(processFileModel.getCnpj())
                                        .nomeEmpresa(processFileModel.getNomeEmpresa())
                                        .idForSearch(processFileModel.getId())
                                        .build();
                                processFileModel = uploadPage.navigateOnUploadPage(processSearchVO, remoteWebDriver);
                                processFileModel.getStatus();
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
