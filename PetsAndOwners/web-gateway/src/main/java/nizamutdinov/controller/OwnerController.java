package nizamutdinov..controller;

import lombok.RequiredArgsConstructor;
import nizamutdinov..*;
import nizamutdinov..kafka.KafkaRequestProducer;
import nizamutdinov..service.ResponseStore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/owners")
@RequiredArgsConstructor
public class OwnerController {

    private final KafkaRequestProducer producer;
    private final ResponseStore store;

    @PostMapping
    public OwnerDTO createOwner(@RequestBody OwnerDTO dto) throws InterruptedException {
        producer.send("owner-requests", dto);
        return store.getOwnerResponse();
    }

    // GET по id — через Kafka
    @GetMapping("/{id}")
    public OwnerDTO getOwnerById(@PathVariable Long id) throws InterruptedException {
        producer.send("owner-get-requests", new OwnerGetRequest(id));
        OwnerDTO dto = store.getOwnerGetResponse();
        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Owner not found: " + id);
        }
        return dto;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOwner(@PathVariable Long id) throws InterruptedException {
        producer.send("owner-delete-requests", new OwnerDeleteRequest(id));
        OwnerDeleteResponse resp = store.getOwnerDeleteResponse();
        if (resp == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "No response from delete operation");
        }
        if (!resp.isDeleted()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Owner not found: " + id);
        }
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public OwnerDTO updateOwner(
            @PathVariable Long id,
            @RequestBody OwnerUpdateRequest req
    ) throws InterruptedException {
        req.setId(id);
        producer.send("owner-update-requests", req);

        OwnerDTO updated = store.getOwnerUpdateResponse();
        if (updated == null) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "No response for update"
            );
        }
        return updated;
    }
}