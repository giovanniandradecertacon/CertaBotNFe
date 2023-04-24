package br.com.certacon.certabotnfefiles.components;

import br.com.certacon.certabotnfefiles.configurations.RemoteWebDriverConfig;
import br.com.certacon.certabotnfefiles.models.ArquivoNfeModel;
import br.com.certacon.certabotnfefiles.services.LoginTabService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {LoginTabService.class, RemoteWebDriverConfig.class})
class LoginAreaComponentTest {
    @Autowired
    private LoginTabService loginTabService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("chamar o metodo login quando Retornar com Sucesso")
    void shouldCallLoginTabServiceWhenLoginReturnWithSuccess(){
        //Given
        ArquivoNfeModel arquivoNfeModel =
    }
}