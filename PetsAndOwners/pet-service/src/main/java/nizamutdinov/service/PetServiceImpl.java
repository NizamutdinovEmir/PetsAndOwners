package nizamutdinov..service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import nizamutdinov..PetDTO;
import nizamutdinov..PetUpdateRequest;
import nizamutdinov..model.Pet;
import nizamutdinov..repository.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {
    private final PetRepository repository;

    @Override
    public PetDTO createPet(PetDTO dto) {
        Pet pet = new Pet();
        pet.setName(dto.getName());
        pet.setBirthDate(dto.getBirthDate());
        pet.setBreed(dto.getBreed());
        pet.setColor(dto.getColor());
        pet.setOwnerId(dto.getOwnerId());

        Pet saved = repository.save(pet);
        return toDto(saved);
    }


    @Override
    public PetDTO getPetById(Long id) {
        Pet p = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pet not found: "+id));
        return toDto(p);
    }

    @Override
    public boolean deletePetById(Long id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }

    @Override
    public PetDTO updatePet(PetUpdateRequest req) {
        Pet p = repository.findById(req.getId()).orElseThrow(() -> new EntityNotFoundException("Pet not found: "+req.getId()));
        p.setName(req.getName());
        p.setBirthDate(req.getBirthDate());
        return toDto(repository.save(p));
    }


    private PetDTO toDto(Pet pet) {
        PetDTO dto = new PetDTO();
        dto.setId(pet.getId());
        dto.setName(pet.getName());
        dto.setBirthDate(pet.getBirthDate());
        dto.setBreed(pet.getBreed());
        dto.setColor(pet.getColor());
        dto.setOwnerId(pet.getOwnerId());
        return dto;
    }
}