package br.com.certacon.certabotnfefiles.services;

import br.com.certacon.certabotnfefiles.models.NfeFileModel;
import br.com.certacon.certabotnfefiles.repositories.NfeFileRepository;
import br.com.certacon.certabotnfefiles.utils.NfeStatus;
import org.openqa.selenium.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class NfeFileService {
    private final NfeFileRepository fileRepository;

    public NfeFileService(NfeFileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }


    public NfeFileModel saveOrUpdate(NfeFileModel nfeFileModel) {
        validateNfeFile(nfeFileModel);
        Optional<NfeFileModel> existingFile = fileRepository.findByFileName(nfeFileModel.getFileName());
        if (existingFile.isPresent()) {
            NfeFileModel updatedFile = updateFile(existingFile.get(), nfeFileModel);
            return fileRepository.save(updatedFile);
        } else {
            nfeFileModel.setCreatedAt(new Date());
            nfeFileModel.setStatus(NfeStatus.CREATED);
            return fileRepository.save(nfeFileModel);
        }
    }

    private NfeFileModel updateFile(NfeFileModel existingFile, NfeFileModel updatedFile) {
        existingFile.setFileName(updatedFile.getFileName());
        existingFile.setStatus(NfeStatus.UPDATED);
        existingFile.setCreatedAt(updatedFile.getCreatedAt());
        existingFile.setUpdatedAt(new Date());
        return existingFile;
    }

    private void validateNfeFile(NfeFileModel fileModel) {
        if (fileModel.getFileName() == null || fileModel.getFileName().isEmpty()) {
            throw new IllegalArgumentException("File name cannot be null or empty");
        }
    }

    public Boolean deleteFile(UUID id) {
        Optional<NfeFileModel> modelOptional = fileRepository.findById(id);
        if (!modelOptional.isPresent()) {
            throw new NotFoundException("Arquivo não encontrado");
        } else {
            fileRepository.delete(modelOptional.get());
            return Boolean.TRUE;
        }
    }
}

