package br.com.certacon.certabotnfefiles.services;

import br.com.certacon.certabotnfefiles.models.NfeFileModel;
import br.com.certacon.certabotnfefiles.repositories.NfeFileRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.UUID;

@SpringBootTest(classes = {NfeFileService.class})
class NfeFileServiceTest {
    @MockBean
    NfeFileRepository fileRepository;
    @Autowired
    private NfeFileService fileService;

    private NfeFileModel fileModel;

    @BeforeEach
    void setUp() {
        fileService = new NfeFileService(fileRepository);

        fileModel = NfeFileModel.builder()
                .id(UUID.randomUUID())
                .nomeEmpresa("Certacon")
                .cnpj("00.181.904/0001-97")
                .status()
                .build();
    }

    @AfterEach
    void tearDown() {
        fileService = null;
    }

    @Test
    @DisplayName("chamar o metodo create quando Retornar com Sucesso")
    void shouldCallNfeFileServiceWhenCreateReturnWithSuccess() {

    }
}