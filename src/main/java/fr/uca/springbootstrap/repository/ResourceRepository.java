package fr.uca.springbootstrap.repository;

import fr.uca.springbootstrap.models.Resource;

import java.util.Optional;

public interface ResourceRepository {
    Optional<Resource> findByName(String name);

    Optional<Resource> findById(Long name);
}
