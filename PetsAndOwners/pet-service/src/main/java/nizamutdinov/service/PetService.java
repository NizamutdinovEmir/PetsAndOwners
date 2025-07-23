package nizamutdinov..service;

import nizamutdinov..PetDTO;
import nizamutdinov..PetUpdateRequest;

import java.util.List;

public interface PetService {
    PetDTO createPet(PetDTO dto);
    PetDTO getPetById(Long id);
    boolean deletePetById(Long id);
    PetDTO updatePet(PetUpdateRequest req);
}