package io.javabrains.springsecurityjwt.repository;

import io.javabrains.springsecurityjwt.models.Role;
import io.javabrains.springsecurityjwt.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);

  Optional<User> findById(Long id);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);

  @Transactional
  List<User> findAll();

  @Transactional
  List<User> findAllByRoles(Role role);

}
