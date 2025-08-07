package uz.tridev.eventbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.tridev.eventbooking.entity.Role;
import uz.tridev.eventbooking.entity.enums.RoleName;

import java.util.List;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
  List<Role> findAllByRoleNameIn(List<RoleName> roleNames);

  List<Role> findByRoleName(RoleName roleName);

  boolean existsByRoleName(RoleName roleName);
}
