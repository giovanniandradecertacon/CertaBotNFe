package br.com.certacon.certabotnfefiles.services;


import br.com.certacon.certabotnfefiles.dto.ProcessFileDto;
import br.com.certacon.certabotnfefiles.exception.BadRequestException;
import br.com.certacon.certabotnfefiles.models.NfeFileModel;
import br.com.certacon.certabotnfefiles.models.ProcessFileModel;
import br.com.certacon.certabotnfefiles.repositories.NfeFileRepository;
import br.com.certacon.certabotnfefiles.repositories.ProcessFileRepository;
import br.com.certacon.certabotnfefiles.utils.ProcessStatus;
import org.openqa.selenium.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProcessNfeFileService {
    private final ProcessFileRepository processFileRepository;
    private final NfeFileRepository nfeFileRepository;

    public ProcessNfeFileService(ProcessFileRepository processFileRepository, NfeFileRepository nfeFileRepository) {
        this.processFileRepository = processFileRepository;
        this.nfeFileRepository = nfeFileRepository;
    }

    public Optional<ProcessFileModel> searchById(UUID id) throws NotFoundException, BadRequestException {
        Optional<ProcessFileModel> processFileModel = processFileRepository.findById(id);
        try {
            if (!processFileModel.isPresent()) {
                throw new NotFoundException();
            }
        } catch (BadRequestException e) {
            throw new BadRequestException();
        } finally {
            return processFileModel;
        }
    }

    public ProcessFileModel createFile(ProcessFileDto processFileDto) throws NotFoundException, BadRequestException {
        ProcessFileModel model = null;
        try {
            if (processFileDto != null) {
                model = processFileDto.toModel();
                model.setCreatedAt(new Date());
                model.setStatus(ProcessStatus.CREATED);
                Optional<NfeFileModel> arquivoModel = nfeFileRepository.findById(processFileDto.getArquivoId());
                if (!arquivoModel.isEmpty()) {
                    if (processFileDto.toModel().getFileName().endsWith("/") || processFileDto.toModel().getDownloadPath().endsWith("/") || processFileDto.toModel().getFileName().startsWith("/")) {
                        String filename = processFileDto.getFileName().replaceAll("/", "");
                        model.setFileName(filename);
                        String downloadPath = processFileDto.getDownloadPath();
                        downloadPath = downloadPath.substring(0, downloadPath.length() - 1);
                        model.setDownloadPath(downloadPath);
                    }
                    List<NfeFileModel> modelList = new ArrayList<>();
                    modelList.add(arquivoModel.get());
                    model.setArquivosNFeModel(modelList);
                    model = processFileRepository.save(model);
                    return model;
                } else {
                    throw new NotFoundException();
                }
            }
        } catch (BadRequestException e) {
            throw new BadRequestException();
        }
        return model;
    }

    public List<ProcessFileModel> findAll() {
        List<ProcessFileModel> processFileModelList = processFileRepository.findAll();
        return processFileModelList;
    }

    public ProcessFileModel updateFile(ProcessFileDto processFileDto) throws RuntimeException {
        ProcessFileModel model = null;
        try {
            Optional<ProcessFileModel> processFileModel = processFileRepository.findById(processFileDto.toModel().getId());

            if (processFileModel.isPresent()) {
                model = processFileModel.get();
                model.setId(model.getId());
                model.setCreatedAt(model.getCreatedAt());
                model.setUsername(processFileDto.getUsername() == null ? model.getUsername() : processFileDto.getUsername());
                model.setPassword(processFileDto.getPassword() == null ? model.getPassword() : processFileDto.getPassword());
                model.setRemoteDriverDownload(processFileDto.getRemoteDriverDownload() == null ? model.getRemoteDriverDownload() : processFileDto.getRemoteDriverDownload());
                model.setRemoteDriverUpload(processFileDto.getRemoteDriverUpload() == null ? model.getRemoteDriverUpload() : processFileDto.getRemoteDriverUpload());
                model.setDownloadPath(processFileDto.getDownloadPath() == null ? model.getDownloadPath() : processFileDto.getDownloadPath());
                model.setUpdatedAt(new Date());
                model.setStatus(ProcessStatus.UPDATED);
                model.setFileName(processFileDto.getFileName() == null ? model.getFileName() : processFileDto.getFileName());

                processFileRepository.save(model);
            } else {
                throw new RuntimeException();
            }
        } catch (BadRequestException e) {
            throw new BadRequestException();
        } finally {
            return model;
        }
    }

    public boolean deleteFile(UUID id) {
        boolean isDeleted = false;
        try {
            Optional<ProcessFileModel> modelOptional = processFileRepository.findById(id);

            if (!modelOptional.isPresent()) {
                throw new NotFoundException();
            } else {
                isDeleted = Boolean.TRUE;
                processFileRepository.delete(modelOptional.get());
            }
        } catch (BadRequestException e) {
            throw new BadRequestException();
        } finally {
            return isDeleted;
        }

    }
}
