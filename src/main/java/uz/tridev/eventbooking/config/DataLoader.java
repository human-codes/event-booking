package uz.tridev.eventbooking.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.tridev.eventbooking.entity.Role;
import uz.tridev.eventbooking.entity.enums.RoleName;
import uz.tridev.eventbooking.repository.RoleRepository;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataLoader {

    private final RoleRepository roleRepository;

    @PostConstruct
    public void init() {
        if (roleRepository.count() == 0) {
            Arrays.stream(RoleName.values()).forEach(roleName -> {
                if (!roleRepository.existsByRoleName(roleName)) {
                    Role role = new Role();
                    role.setRoleName(roleName);
                    roleRepository.save(role);
                }
            });
        }
    }
}
