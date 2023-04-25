package br.com.certacon.certabotnfefiles.services;

import br.com.certacon.certabotnfefiles.configurations.RemoteWebDriverConfig;
import br.com.certacon.certabotnfefiles.helpers.SeleniumHelperComponent;
import br.com.certacon.certabotnfefiles.utils.NfeStatus;
import br.com.certacon.certabotnfefiles.vos.NfeFileForLoginVO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.MalformedURLException;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {LoginTabService.class, RemoteWebDriverConfig.class, SeleniumHelperComponent.class})
class LoginTabServiceTest {

    @Autowired
    private SeleniumHelperComponent helper;

    @Autowired
    private LoginTabService loginTabService;

    private NfeFileForLoginVO loginVO;

    @BeforeEach
    void setUp() {
        loginTabService = new LoginTabService(helper);
        loginVO = NfeFileForLoginVO.builder()
                .remoteDriverUpload("http://192.168.0.62/tributario")
                .username("giovanni.andrade@certacon.com.br")
                .password("1")
                .build();
    }

    @AfterEach
    void tearDown() {
        loginTabService = null;
    }

    @Test
    @DisplayName("chamar o metodo LoginInput quando Retornar com Sucesso")
    void shouldCallLoginTabServiceWhenLoginInputReturnWithSuccess() throws MalformedURLException, InterruptedException {
        //Given
        NfeStatus expected = NfeStatus.LOGGED;
        //When
        NfeStatus actual = loginTabService.loginInput(loginVO);
        //Then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("chamar o metodo LoginInput quando Retornar com Erro")
    void shouldCallLoginTabServiceWhenLoginInputReturnWithError() throws MalformedURLException, InterruptedException {
        //Given
        NfeStatus expected = NfeStatus.ERROR;
        //When
        NfeStatus actual = loginTabService.loginInput(null);
        //Then
        assertEquals(expected, actual);
    }
    @Test
    @DisplayName("chamar o metodo LoginInput quando Retornar com Erro na Url passada")
    void shouldCallLoginTabServiceWhenLoginInputReturnWithErrorForUrl() throws MalformedURLException, InterruptedException {
        //Given
        loginVO.setRemoteDriverUpload("Wrong-Url");
        NfeStatus expected = NfeStatus.ERROR;
        //When
        NfeStatus actual = loginTabService.loginInput(loginVO);
        //Then
        assertEquals(expected, actual);
    }


  /*  @Test
    @DisplayName("chamar o metodo closeAndSearchForNFe quando Retornar com Sucesso")
    void shouldCallLoginTabServiceWhenCloseAndSearchForNFeReturnWithSuccess(){
        //Given
        NfeStatus expected = ;
    }*/
}