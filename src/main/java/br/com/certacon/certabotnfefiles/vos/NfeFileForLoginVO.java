package br.com.certacon.certabotnfefiles.vos;

import br.com.certacon.certabotnfefiles.models.NfeFileModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
@Builder
public class NfeFileForLoginVO {
    private UUID id;
    private String username;

    private String password;

    private String remoteDriverUpload;

    public NfeFileModel toModel(){
        return NfeFileModel.builder()
                .id(id)
                .username(username)
                .password(password)
                .remoteDriverUpload(remoteDriverUpload)
                .build();
    }
}
