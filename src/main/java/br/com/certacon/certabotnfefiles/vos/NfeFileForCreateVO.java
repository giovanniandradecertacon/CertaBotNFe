package br.com.certacon.certabotnfefiles.vos;

import br.com.certacon.certabotnfefiles.models.NfeFileModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class NfeFileForCreateVO {
    private String username;
    private String password;
    private String remoteDriverUpload;
    private String nomeEmpresa;
    private String cnpj;
    private String fileName;
    private String pathToFile;

    public NfeFileModel toModel() {
        return NfeFileModel.builder()
                .username(username)
                .password(password)
                .remoteDriverUpload(remoteDriverUpload)
                .nomeEmpresa(nomeEmpresa)
                .cnpj(cnpj)
                .fileName(fileName)
                .pathToFile(pathToFile)
                .build();
    }
}
