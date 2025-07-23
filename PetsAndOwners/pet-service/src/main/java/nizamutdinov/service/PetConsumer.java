package nizamutdinov..service;

import lombok.RequiredArgsConstructor;
import nizamutdinov..*;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PetConsumer {

    private final PetService petService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = "pet-requests", groupId = "pet-service")
    public void consumeCreatePet(PetDTO dto) {
        PetDTO response = petService.createPet(dto);
        kafkaTemplate.send("pet-responses", response);
    }

    @KafkaListener(
            topics = "pet-get-requests",
            groupId = "pet-service-get",
            containerFactory = "petGetRequestKafkaListenerContainerFactory"
    )
    public void handleGet(PetGetRequest req) {
        PetDTO dto = petService.getPetById(req.getId());
        kafkaTemplate.send("pet-get-responses", dto);
    }

    @KafkaListener(
            topics = "pet-delete-requests",
            groupId = "pet-service-delete",
            containerFactory = "petDeleteRequestKafkaListenerContainerFactory"
    )
    public void handleDelete(PetDeleteRequest req) {
        boolean deleted = petService.deletePetById(req.getId());
        kafkaTemplate.send("pet-delete-responses", new PetDeleteResponse(req.getId(), deleted));
    }

    @KafkaListener(
            topics = "pet-update-requests",
            groupId = "pet-service-update",
            containerFactory = "petUpdateRequestKafkaListenerContainerFactory"
    )
    public void handleUpdate(PetUpdateRequest req) {
        PetDTO dto = petService.updatePet(req);
        kafkaTemplate.send("pet-update-responses", dto);
    }
}

