package br.com.certacon.certabotnfefiles.dto;

import br.com.certacon.certabotnfefiles.models.NfeFileModel;
import br.com.certacon.certabotnfefiles.models.ProcessFileModel;
import br.com.certacon.certabotnfefiles.utils.ProcessStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ProcessFileDto {

    @JsonProperty(value = "processo_id")
    private UUID id;
    @JsonProperty(value = "caminho_de_destino_download", required = true)
    private String downloadPath;
    @JsonProperty(value = "url_de_download", required = true)
    private String remoteDriverDownload;
    @JsonProperty(value = "url_de_upload", required = true)
    private String remoteDriverUpload;
    @JsonProperty(value = "senha", required = true)
    private String password;
    @JsonProperty(value = "usuario", required = true)
    private String username;
    @JsonProperty(value = "nome_arquivo", required = true)
    private String fileName;

    @JsonProperty(value = "caminho_de_arquivo", required = true)
    private String filePath;
    @JsonProperty(value = "status")
    private ProcessStatus status;
    @JsonProperty(value = "criado_em", required = true)
    private Date createdAt;
    @JsonProperty(value = "atualizado_em")
    private Date updatedAt;
    @JsonProperty(value = "id_arquivo", required = true)
    private UUID arquivoId;

    @JsonProperty(value = "cnpj", required = true)
    private String cnpj;

    @JsonProperty(value = "nome_empresa", required = true)
    private String nomeEmpresa;


    public ProcessFileModel toModel() {
        List<NfeFileModel> listaDeArquivos = new ArrayList<>();
        listaDeArquivos.add(NfeFileModel.builder()
                .id(arquivoId)
                .build());
        return ProcessFileModel.builder()
                .id(id)
                .fileName(fileName)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .status(status)
                .downloadPath(downloadPath)
                .password(password)
                .cnpj(cnpj)
                .nomeEmpresa(nomeEmpresa)
                .remoteDriverDownload(remoteDriverDownload)
                .remoteDriverUpload(remoteDriverUpload)
                .username(username)
                .filePath(filePath)
                .arquivosNFeModel(listaDeArquivos)
                .build();
    }
}
