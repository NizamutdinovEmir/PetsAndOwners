package nizamutdinov..kafka;

import lombok.RequiredArgsConstructor;
import nizamutdinov..OwnerDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OwnerProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendOwnerCreated(OwnerDTO ownerDTO) {
        kafkaTemplate.send("owner-responses", ownerDTO);
    }
}
