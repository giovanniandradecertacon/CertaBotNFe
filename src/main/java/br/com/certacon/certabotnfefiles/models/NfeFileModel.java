package br.com.certacon.certabotnfefiles.models;

import br.com.certacon.certabotnfefiles.utils.NfeStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class NfeFileModel {
    @Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "arquivo_nfe_id")
    private UUID id;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private NfeStatus status;

    @Column(name = "file_name", nullable = false)
    private String fileName;


    @ManyToOne
    private ProcessFileModel processFileModel;
}
