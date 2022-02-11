package fr.uca.springbootstrap.repository;

import java.util.List;
import java.util.Optional;

import fr.uca.springbootstrap.models.Role;
import fr.uca.springbootstrap.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);
  @Transactional
  List<User> findAll();

}
