package nizamutdinov.;

import nizamutdinov..model.Owner;
import nizamutdinov..repository.OwnerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {
    private final OwnerRepository repo;
    private final PasswordEncoder encoder;
    public DataLoader(OwnerRepository repo, PasswordEncoder encoder) {
        this.repo = repo; this.encoder = encoder;
    }
    @Override
    public void run(String... args) {
        if (repo.findByUsername("admin").isEmpty()) {
            Owner admin = new Owner();
            admin.setUsername("admin");
            admin.setPassword(encoder.encode("admin"));
            admin.setRoles(Set.of("ROLE_ADMIN"));
            repo.save(admin);
        }
    }
}
