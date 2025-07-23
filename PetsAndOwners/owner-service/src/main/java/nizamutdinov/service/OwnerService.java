package nizamutdinov..service;


import nizamutdinov..OwnerDTO;
import nizamutdinov..OwnerUpdateRequest;

import java.util.List;

public interface OwnerService {
    OwnerDTO createOwner(OwnerDTO dto);
    OwnerDTO getOwnerById(Long id);
    List<OwnerDTO> listOwners();
    void deleteOwnerById(Long id);
    OwnerDTO updateOwner(OwnerUpdateRequest req);
}