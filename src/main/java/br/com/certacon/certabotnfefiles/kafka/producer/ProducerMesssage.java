package br.com.certacon.certabotnfefiles.kafka.producer;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProducerMesssage {

    private String status;
    private String nomeOriginal;

}
