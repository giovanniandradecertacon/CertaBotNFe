package br.com.certacon.certabotnfefiles.vos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class DownloadFileVO {
    private String remoteDriverDownload;
    private String fileName;
}
