package nizamutdinov..service;

import jakarta.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;
import nizamutdinov..OwnerDTO;
import nizamutdinov..OwnerUpdateRequest;
import nizamutdinov..model.Owner;
import nizamutdinov..repository.OwnerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {
    private final OwnerRepository repository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public OwnerDTO createOwner(OwnerDTO dto) {
        // 1) Собираем entity из DTO
        Owner owner = new Owner();
        owner.setName(dto.getName());
        owner.setBirthDate(dto.getBirthDate());
        owner.setUsername(dto.getUsername());
        // хэшируем пароль перед сохранением
        owner.setPassword(passwordEncoder.encode(dto.getPassword()));
        // по умолчанию пользователь получает роль USER
        owner.setRoles(Set.of("ROLE_USER"));

        // 2) Сохраняем
        Owner saved = repository.save(owner);

        // 3) Конвертируем обратно в DTO (без пароля)
        return toDTO(saved);
    }

    @Override
    public OwnerDTO getOwnerById(Long id) {
        Owner owner = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Owner not found: " + id));
        return toDTO(owner);
    }

    @Override
    public List<OwnerDTO> listOwners() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Внутренний метод-конвертер Entity → DTO
    private OwnerDTO toDTO(Owner owner) {
        OwnerDTO dto = new OwnerDTO();
        dto.setId(owner.getId());
        dto.setName(owner.getName());
        dto.setBirthDate(owner.getBirthDate());
        dto.setUsername(owner.getUsername());
        return dto;
    }

    @Override
    public void deleteOwnerById(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Owner not found: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public OwnerDTO updateOwner(OwnerUpdateRequest req) {
        Owner owner = repository.findById(req.getId())
                .orElseThrow(() -> new EntityNotFoundException("Owner not found: " + req.getId()));
        owner.setName(req.getName());
        owner.setBirthDate(req.getBirthDate());
        owner.setUsername(req.getUsername());
        owner.setPassword(passwordEncoder.encode(req.getPassword()));
        Owner saved = repository.save(owner);
        return toDTO(saved);
    }
}