package br.com.certacon.certabotnfefiles.vos;

import br.com.certacon.certabotnfefiles.models.ProcessFileModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
@Builder
public class ProcessFileForSearchVO {
    private UUID idForSearch;

    private String nomeEmpresa;

    private String cnpj;

    private String pathToFile;

    private String nfeFileName;

    private String percentage;

    private String quantity;

    public ProcessFileModel toModel() {
        return ProcessFileModel.builder()
                .id(idForSearch)
                .nomeEmpresa(nomeEmpresa)
                .downloadPath(pathToFile)
                .cnpj(cnpj)
                .build();
    }
}
