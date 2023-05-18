package br.com.certacon.certabotnfefiles.repositories;

import br.com.certacon.certabotnfefiles.models.ProcessFileModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProcessFileRepository extends JpaRepository<ProcessFileModel, UUID> {
    Optional<ProcessFileModel> findByFileName(String fileName);
}
