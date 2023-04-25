package br.com.certacon.certabotnfefiles.vos;

import br.com.certacon.certabotnfefiles.models.NfeFileModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
@Builder
public class NfeFileForSearchCnpjVO {
    private UUID idForSearch;

    private String nomeEmpresa;

    private String cnpj;

    public NfeFileModel toModel() {
        return NfeFileModel.builder()
                .id(idForSearch)
                .nomeEmpresa(nomeEmpresa)
                .cnpj(cnpj)
                .build();
    }
}
