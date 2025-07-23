package nizamutdinov..service;

import lombok.RequiredArgsConstructor;
import nizamutdinov..PetDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PetProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendPetCreated(PetDTO dto) {
        kafkaTemplate.send("pet-responses", dto);
    }
}
