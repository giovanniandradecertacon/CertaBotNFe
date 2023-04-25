package br.com.certacon.certabotnfefiles.schedules;

import br.com.certacon.certabotnfefiles.configurations.RemoteWebDriverConfig;
import br.com.certacon.certabotnfefiles.helpers.SeleniumHelperComponent;
import br.com.certacon.certabotnfefiles.models.NfeFileModel;
import br.com.certacon.certabotnfefiles.pages.CertaconHomePage;
import br.com.certacon.certabotnfefiles.pages.LoginCertaconWebPage;
import br.com.certacon.certabotnfefiles.repositories.NfeFileRepository;
import br.com.certacon.certabotnfefiles.utils.NfeStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(classes = {LoginCertaconWebPage.class, CertaconHomePage.class, RemoteWebDriverConfig.class, SeleniumHelperComponent.class})
class SeleniumServicesScheduleTest {
    @Autowired
    private SeleniumHelperComponent seleniumHelperComponent;

    @MockBean
    NfeFileRepository nfeFileRepository;

    @SpyBean
    private SeleniumPagesSchedule pagesSchedule;

    private NfeFileModel nfeFileModel;

    @BeforeEach
    void setUp() {
        nfeFileModel = NfeFileModel.builder()
                .id(UUID.randomUUID())
                .remoteDriverUpload("http://192.168.0.62/tributario")
                .username("giovanni.andrade@certacon.com.br")
                .password("1")
                .status(NfeStatus.READY)
                .build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("chamar o metodo PagesSchedule Quando Retornar com Sucesso")
    void shouldCallSeleniumPagesScheduleWhenPagesScheduleReturnWithSuccess() throws MalformedURLException {
        //Given
        List<NfeFileModel> modelList = new ArrayList<>();
        modelList.add(nfeFileModel);
        BDDMockito.given(nfeFileRepository.findAll()).willReturn(modelList);
        //When
        BDDMockito.when(nfeFileRepository.findById(any(UUID.class))).thenReturn(Optional.of(nfeFileModel));
        Boolean actual = pagesSchedule.pagesSchedule();
        //Then
        assertTrue(actual);
    }
}