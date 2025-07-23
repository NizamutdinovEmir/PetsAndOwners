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
@RequestMapping("/api/pets")
@RequiredArgsConstructor
public class PetController {

    private final KafkaRequestProducer producer;
    private final ResponseStore store;

    @PostMapping
    public PetDTO createPet(@RequestBody PetDTO dto) throws InterruptedException {
        producer.send("pet-requests", dto);
        return store.getPetResponse();
    }


    // GET /api/pets/{id}
    @GetMapping("/{id}")
    public PetDTO get(@PathVariable Long id) throws InterruptedException {
        producer.send("pet-get-requests", new PetGetRequest(id));
        PetDTO dto = store.getPetGetResponse();
        if (dto == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet not found: " + id);
        return dto;
    }

    // DELETE /api/pets/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws InterruptedException {
        producer.send("pet-delete-requests", new PetDeleteRequest(id));
        PetDeleteResponse resp = store.getPetDeleteResponse();
        if (resp == null) throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No delete response");
        if (!resp.isDeleted()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet not found: " + id);
        return ResponseEntity.noContent().build();
    }

    // PUT /api/pets/{id}
    @PutMapping("/{id}")
    public PetDTO update(@PathVariable Long id, @RequestBody PetUpdateRequest req) throws InterruptedException {
        req.setId(id);
        producer.send("pet-update-requests", req);
        PetDTO updated = store.getPetUpdateResponse();
        if (updated == null) throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No update response");
        return updated;
    }
}
