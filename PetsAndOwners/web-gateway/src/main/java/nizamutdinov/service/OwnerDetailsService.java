package nizamutdinov..service;

import lombok.RequiredArgsConstructor;
import nizamutdinov..repository.OwnerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OwnerDetailsService implements UserDetailsService {
    private final OwnerRepository ownerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return ownerRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Owner not found: " + username));
    }
}