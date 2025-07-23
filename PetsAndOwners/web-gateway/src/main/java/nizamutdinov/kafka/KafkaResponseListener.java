package nizamutdinov..kafka;

import lombok.RequiredArgsConstructor;
import nizamutdinov..OwnerDTO;
import nizamutdinov..OwnerDeleteResponse;
import nizamutdinov..PetDTO;
import nizamutdinov..PetDeleteResponse;
import nizamutdinov..service.ResponseStore;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaResponseListener {

    private final ResponseStore store;

    @KafkaListener(
            topics = "owner-responses",
            groupId = "web-gateway-owner",
            containerFactory = "ownerKafkaListenerContainerFactory"
    )
    public void listenOwnerResponse(OwnerDTO dto) {
        store.addOwnerResponse(dto);
    }

    @KafkaListener(
            topics = "owner-get-responses",
            groupId = "web-gateway-owner-get",
            containerFactory = "ownerKafkaListenerContainerFactory"
    )
    public void listenOwnerGet(OwnerDTO dto) {
        store.addOwnerGetResponse(dto);
    }

    @KafkaListener(
            topics = "pet-responses",
            groupId = "web-gateway-pet",
            containerFactory = "petKafkaListenerContainerFactory"
    )
    public void listenPetResponse(PetDTO dto) {
        store.addPetResponse(dto);
    }

    @KafkaListener(
            topics = "owner-delete-responses",
            groupId = "web-gateway-owner-delete",
            containerFactory = "ownerKafkaListenerContainerFactory"
    )
    public void listenOwnerDelete(OwnerDeleteResponse dto) {
        store.addOwnerDeleteResponse(dto);
    }

    @KafkaListener(
            topics = "owner-update-responses",
            groupId = "web-gateway-owner-update",
            containerFactory = "ownerKafkaListenerContainerFactory"
    )
    public void listenOwnerUpdate(OwnerDTO dto) {
        store.addOwnerUpdateResponse(dto);
    }

    @KafkaListener(
            topics = "pet-get-responses",
            groupId = "web-gateway-pet-get",
            containerFactory = "petKafkaListenerContainerFactory"
    )
    public void listenPetGet(PetDTO dto) {
        store.addPetGetResponse(dto);
    }

    @KafkaListener(
            topics = "pet-delete-responses",
            groupId = "web-gateway-pet-delete",
            containerFactory = "petKafkaListenerContainerFactory"
    )
    public void listenPetDelete(PetDeleteResponse dto) {
        store.addPetDeleteResponse(dto);
    }

    @KafkaListener(
            topics = "pet-update-responses",
            groupId = "web-gateway-pet-update",
            containerFactory = "petKafkaListenerContainerFactory"
    )
    public void listenPetUpdate(PetDTO dto) {
        store.addPetUpdateResponse(dto);
    }
}