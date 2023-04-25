package br.com.certacon.certabotnfefiles.vos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class NfeFileForLoginVO {
    private String username;

    private String password;

    private String remoteDriverUpload;
}
