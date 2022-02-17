package fr.uca.springbootstrap.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import fr.uca.springbootstrap.models.Role;
import fr.uca.springbootstrap.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);

  Optional<User> findById(Long id);

  Optional<User> findByEmail(String email);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);

  @Transactional
  List<User> findAll();

  @Transactional
  List<User> findAllByRoles(Role role);

}
