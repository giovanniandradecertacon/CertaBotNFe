package br.com.certacon.certabotnfefiles.services;

import br.com.certacon.certabotnfefiles.models.NfeFileModel;
import br.com.certacon.certabotnfefiles.repositories.NfeFileRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.openqa.selenium.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;


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
                .fileName("NFeCertaconTeste.zip")
                .build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("chamar o metodo saveOrUpdate quando Retornar com Sucesso")
    void shouldCallNfeFileServiceWhenCreateReturnWithSuccess() {
        //When
        BDDMockito.when(fileRepository.save(any(NfeFileModel.class))).thenReturn(fileModel);
        NfeFileModel actual = fileService.saveOrUpdate(fileModel);
        //Then
        assertEquals(fileModel.getId(), actual.getId());
    }

    @Test
    @DisplayName("chamar o metodo saveOrUpdate quando Retornar com Erro")
    void shouldCallNfeFileServiceWhenCreateReturnWithError() {
        //Given
        NfeFileModel nfeFileModel = NfeFileModel.builder()
                .id(UUID.randomUUID())
                .fileName(null)
                .build();
        IllegalArgumentException expected = new IllegalArgumentException("File name cannot be null or empty");
        //When
        BDDMockito.when(fileRepository.save(any(NfeFileModel.class))).thenReturn(nfeFileModel);
        IllegalArgumentException actual = assertThrows(IllegalArgumentException.class, () -> fileService.saveOrUpdate(nfeFileModel));
        //Then
        assertEquals(expected.getMessage(), actual.getMessage());
    }

    @Test
    @DisplayName("chamar o metodo DeleteFile quando retornar com Sucesso")
    void shouldCallNfeFileServiceWhenDeleteFileReturnWithSuccess() {
        //When
        BDDMockito.when(fileRepository.findById(any(UUID.class))).thenReturn(Optional.of(fileModel));
        Boolean actual = fileService.deleteFile(fileModel.getId());
        //Then
        assertTrue(actual);
    }

    @Test
    @DisplayName("chamar o metodo DeleteFile quando retornar com Erro")
    void shouldCallNfeFileServiceWhenDeleteFileReturnWithNotFoundError() {
        //Given
        NotFoundException expected = new NotFoundException("Arquivo não encontrado");
        //When
        BDDMockito.when(fileRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        NotFoundException actual = assertThrows(NotFoundException.class, () -> fileService.deleteFile(fileModel.getId()));
        //Then
        assertEquals(expected.getMessage(), actual.getMessage());
    }
}