package nizamutdinov..kafka;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import nizamutdinov..*;
import nizamutdinov..service.OwnerService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OwnerConsumer {

    private final OwnerService service;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = "owner-requests", groupId = "owner-service")
    public void handleOwnerRequest(OwnerDTO request) {
        OwnerDTO response = service.createOwner(request);
        kafkaTemplate.send("owner-responses", response);
    }

    @KafkaListener(
            topics = "owner-get-requests",
            groupId = "owner-service-get",
            containerFactory = "ownerGetRequestKafkaListenerContainerFactory"
    )
    public void handleGet(OwnerGetRequest req) {
        OwnerDTO dto = service.getOwnerById(req.getId());
        kafkaTemplate.send("owner-get-responses", dto);
    }

    @KafkaListener(
            topics = "owner-delete-requests",
            groupId = "owner-service-delete",
            containerFactory = "ownerDeleteRequestKafkaListenerContainerFactory"
    )
    public void handleDelete(OwnerDeleteRequest req) {
        boolean deleted;
        try {
            service.deleteOwnerById(req.getId());
            deleted = true;
        } catch (EntityNotFoundException ex) {
            deleted = false;
        }
        kafkaTemplate.send("owner-delete-responses",
                new OwnerDeleteResponse(req.getId(), deleted));
    }

    @KafkaListener(
            topics = "owner-update-requests",
            groupId = "owner-service-update",
            containerFactory = "ownerUpdateRequestKafkaListenerContainerFactory"
    )
    public void handleUpdate(OwnerUpdateRequest req) {
        OwnerDTO dto = service.updateOwner(req);
        kafkaTemplate.send("owner-update-responses", dto);
    }
}
