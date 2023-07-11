package br.com.certacon.certabotnfefiles.kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, ProducerMesssage> kafkaTemplate;

    @Value("${topic.certabotupload}")
    private String topicTermino;

    public void sendTermino(ProducerMesssage message) {

        this.kafkaTemplate.send(topicTermino, message);
        log.info("Published the message [{}] to the kafka queue: [{}]",
                message.getStatus(),
                message.getNomeOriginal(),
                topicTermino
        );
    }

}