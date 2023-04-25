package br.com.certacon.certabotnfefiles.repositories;

import br.com.certacon.certabotnfefiles.models.NfeFileModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NfeFileRepository extends JpaRepository<NfeFileModel, UUID> {
}
