package br.com.certacon.certabotnfefiles.services;

import br.com.certacon.certabotnfefiles.components.CreateNfeFileComponent;
import br.com.certacon.certabotnfefiles.models.NfeFileModel;
import br.com.certacon.certabotnfefiles.repositories.NfeFileRepository;
import br.com.certacon.certabotnfefiles.vos.NfeFileForCreateVO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;


@SpringBootTest(classes = {NfeFileService.class, CreateNfeFileComponent.class})
class NfeFileServiceTest {
    @MockBean
    NfeFileRepository fileRepository;
    @Autowired
    private NfeFileService fileService;

    @Autowired
    private CreateNfeFileComponent createComponent;

    private NfeFileModel fileModel;

    @BeforeEach
    void setUp() {
        fileService = new NfeFileService(fileRepository, createComponent);

        fileModel = NfeFileModel.builder()
                .id(UUID.randomUUID())
                .nomeEmpresa("Certacon")
                .cnpj("00.181.904/0001-97")
                .username("giovanni.andrade@certacon.com.br")
                .pathToFile("D:\\loadFileData\\192168062\\06333120000197\\2023\\NFe")
                .fileName("NFeCertaconTeste.zip")
                .password("1")
                .remoteDriverUpload("http://192.168.0.62/tributario")
                .build();
    }

    @AfterEach
    void tearDown() {
        fileService = null;
    }

    @Test
    @DisplayName("chamar o metodo create quando Retornar com Sucesso")
    void shouldCallNfeFileServiceWhenCreateReturnWithSuccess() {
        //Given
        NfeFileForCreateVO fileForCreateVO = NfeFileForCreateVO.builder()
                .fileName(fileModel.getFileName())
                .remoteDriverUpload(fileModel.getRemoteDriverUpload())
                .pathToFile(fileModel.getPathToFile())
                .cnpj(fileModel.getCnpj())
                .nomeEmpresa(fileModel.getNomeEmpresa())
                .username(fileModel.getUsername())
                .password(fileModel.getPassword())
                .build();

        NfeFileModel expected = NfeFileModel.builder()
                .cnpj("Certacon")
                .build();
        //When
        BDDMockito.when(fileRepository.save(any(NfeFileModel.class))).thenReturn(expected);
        NfeFileModel actual = fileService.create(fileForCreateVO);
        //Then
        assertEquals(expected.getCnpj(), actual.getId());
    }
}