package br.com.certacon.certabotnfefiles.services;

import br.com.certacon.certabotnfefiles.models.ProcessFileModel;
import br.com.certacon.certabotnfefiles.repositories.ProcessFileRepository;
import br.com.certacon.certabotnfefiles.utils.ProcessStatus;
import org.openqa.selenium.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProcessNfeFileService {
    private final ProcessFileRepository processFileRepository;

    public ProcessNfeFileService(ProcessFileRepository processFileRepository) {
        this.processFileRepository = processFileRepository;
    }

    public Optional<ProcessFileModel> findById(UUID id) {
        if (id != null) {
            Optional<ProcessFileModel> model = processFileRepository.findById(id);
            if (model.isEmpty()) {
                throw new NotFoundException("Processo não existe");
            } else {
                return model;
            }
        } else {
            throw new NullPointerException("Id não pode ser nulo");
        }
    }

    public List<ProcessFileModel> findAll() {
        try {
            return processFileRepository.findAll();
        } catch (RuntimeException e) {
            throw new RuntimeException("Algo deu errado");
        }
    }

    public ProcessFileModel saveOrUpdate(ProcessFileModel processFileModel) {
        validateNfeFile(processFileModel);
        Optional<ProcessFileModel> existingFile = processFileRepository.findByFileName(processFileModel.getFileName());
        if (existingFile.isPresent()) {
            ProcessFileModel updatedFile = updateFile(existingFile.get(), processFileModel);
            return processFileRepository.save(updatedFile);
        } else {
            processFileModel.setCreatedAt(new Date());
            processFileModel.setStatus(ProcessStatus.CREATED);
            return processFileRepository.save(processFileModel);
        }
    }

    private ProcessFileModel updateFile(ProcessFileModel existingFile, ProcessFileModel updatedFile) {
        existingFile.setFileName(updatedFile.getFileName());
        existingFile.setStatus(ProcessStatus.UPDATED);
        existingFile.setRemoteDriverUpload(updatedFile.getRemoteDriverUpload());
        existingFile.setCreatedAt(updatedFile.getCreatedAt());
        existingFile.setUpdatedAt(new Date());
        return existingFile;
    }

    private void validateNfeFile(ProcessFileModel fileModel) {
        if (fileModel.getFileName() == null || fileModel.getFileName().isEmpty()) {
            throw new IllegalArgumentException("File name cannot be null or empty");
        }
    }

    public Boolean deleteFile(UUID id) {
        Optional<ProcessFileModel> modelOptional = processFileRepository.findById(id);
        if (!modelOptional.isPresent()) {
            throw new NotFoundException("Arquivo não encontrado");
        } else {
            processFileRepository.delete(modelOptional.get());
            return Boolean.TRUE;
        }
    }
}
