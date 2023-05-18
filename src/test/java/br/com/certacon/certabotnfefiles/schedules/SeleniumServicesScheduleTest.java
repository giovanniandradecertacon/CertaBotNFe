package br.com.certacon.certabotnfefiles.schedules;

import br.com.certacon.certabotnfefiles.configurations.RemoteWebDriverConfig;
import br.com.certacon.certabotnfefiles.helpers.SeleniumHelperComponent;
import br.com.certacon.certabotnfefiles.models.ProcessFileModel;
import br.com.certacon.certabotnfefiles.pages.CertaconHomePage;
import br.com.certacon.certabotnfefiles.pages.LoginCertaconWebPage;
import br.com.certacon.certabotnfefiles.pages.UploadFilesPage;
import br.com.certacon.certabotnfefiles.repositories.ProcessFileRepository;
import br.com.certacon.certabotnfefiles.utils.ProcessStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(classes = {LoginCertaconWebPage.class, CertaconHomePage.class, UploadFilesPage.class, RemoteWebDriverConfig.class, SeleniumHelperComponent.class})
class SeleniumServicesScheduleTest {

    @MockBean
    ProcessFileRepository processFileRepository;

    @SpyBean
    private SeleniumPagesSchedule pagesSchedule;

    private ProcessFileModel processFileModel;

    @BeforeEach
    void setUp() {
        processFileModel = ProcessFileModel.builder()
                .id(UUID.randomUUID())
                .remoteDriverUpload("http://192.168.0.62/tributario")
                .username("giovanni.andrade@certacon.com.br")
                .password("1")
                .cnpj("00.030.181/0001-07")
                .nomeEmpresa("DROGARIA PRO VITA LTDA")
                .status(ProcessStatus.READY)
                .build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("chamar o metodo PagesSchedule Quando Retornar com Sucesso")
    void shouldCallSeleniumPagesScheduleWhenPagesScheduleReturnWithSuccess() throws MalformedURLException {
        //Given
        List<ProcessFileModel> modelList = new ArrayList<>();
        modelList.add(processFileModel);
        BDDMockito.given(processFileRepository.findAll()).willReturn(modelList);
        //When
        BDDMockito.when(processFileRepository.findById(any(UUID.class))).thenReturn(Optional.of(processFileModel));
        Boolean actual = pagesSchedule.pagesSchedule();
        //Then
        assertTrue(actual);
    }
}