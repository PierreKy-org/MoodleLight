package io.javabrains.springsecurityjwt.repository;

import io.javabrains.springsecurityjwt.models.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {
  Optional<Module> findByName(String name);
  Optional<Module> findById(Long name);
}
