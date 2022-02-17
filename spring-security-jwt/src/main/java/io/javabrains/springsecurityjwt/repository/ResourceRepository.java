package io.javabrains.springsecurityjwt.repository;

import io.javabrains.springsecurityjwt.models.Resource.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {
    Optional<Resource> findByName(String name);

    Optional<Resource> findById(Long aLong);
}
