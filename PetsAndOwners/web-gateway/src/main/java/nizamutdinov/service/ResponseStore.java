package nizamutdinov..service;

import nizamutdinov..OwnerDTO;
import nizamutdinov..OwnerDeleteResponse;
import nizamutdinov..PetDTO;
import nizamutdinov..PetDeleteResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Component
public class ResponseStore {

    private final BlockingQueue<OwnerDTO> ownerQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<List<OwnerDTO>> ownerListQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<OwnerDTO> ownerGetQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<PetDTO> petQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<OwnerDeleteResponse> ownerDeleteQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<OwnerDTO> ownerUpdateQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<PetDTO>         petGetQueue    = new LinkedBlockingQueue<>();
    private final BlockingQueue<PetDeleteResponse> petDeleteQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<PetDTO>         petUpdateQueue = new LinkedBlockingQueue<>();

    public void addOwnerResponse(OwnerDTO dto) {
        ownerQueue.offer(dto);
    }

    public OwnerDTO getOwnerResponse() throws InterruptedException {
        return ownerQueue.poll(2, TimeUnit.SECONDS); // ожидание до 2 секунд
    }

    public void addOwnerGetResponse(OwnerDTO dto) {
        ownerGetQueue.offer(dto);
    }
    public OwnerDTO getOwnerGetResponse() throws InterruptedException {
        return ownerGetQueue.poll(2, TimeUnit.SECONDS);
    }

    public void addOwnerListResponse(List<OwnerDTO> list) {
        ownerListQueue.offer(list);
    }
    public List<OwnerDTO> getOwnerListResponse() throws InterruptedException {
        return ownerListQueue.poll(2, TimeUnit.SECONDS);
    }

    public void addPetResponse(PetDTO dto) {
        petQueue.offer(dto);
    }

    public PetDTO getPetResponse() throws InterruptedException {
        return petQueue.poll(2, TimeUnit.SECONDS); // ожидание до 2 секунд
    }
    public void addOwnerDeleteResponse(OwnerDeleteResponse dto) {
        ownerDeleteQueue.offer(dto);
    }
    public OwnerDeleteResponse getOwnerDeleteResponse() throws InterruptedException {
        return ownerDeleteQueue.poll(2, TimeUnit.SECONDS);
    }

    public void addOwnerUpdateResponse(OwnerDTO dto) {
        ownerUpdateQueue.offer(dto);
    }
    public OwnerDTO getOwnerUpdateResponse() throws InterruptedException {
        return ownerUpdateQueue.poll(2, TimeUnit.SECONDS);
    }

    public void addPetGetResponse(PetDTO dto)           { petGetQueue.offer(dto); }
    public PetDTO getPetGetResponse() throws InterruptedException {
        return petGetQueue.poll(2, TimeUnit.SECONDS);
    }

    public void addPetDeleteResponse(PetDeleteResponse dto) { petDeleteQueue.offer(dto); }
    public PetDeleteResponse getPetDeleteResponse() throws InterruptedException {
        return petDeleteQueue.poll(2, TimeUnit.SECONDS);
    }

    public void addPetUpdateResponse(PetDTO dto)        { petUpdateQueue.offer(dto); }
    public PetDTO getPetUpdateResponse() throws InterruptedException {
        return petUpdateQueue.poll(2, TimeUnit.SECONDS);
    }
}
