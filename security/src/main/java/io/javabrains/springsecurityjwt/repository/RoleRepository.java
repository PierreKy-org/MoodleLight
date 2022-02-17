package io.javabrains.springsecurityjwt.repository;

import io.javabrains.springsecurityjwt.models.ERole;
import io.javabrains.springsecurityjwt.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}
