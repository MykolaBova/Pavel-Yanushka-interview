package org.pavel.yanushka.server.persistence.repository;

import org.pavel.yanushka.server.persistence.entities.PlacesEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlacesRepository extends CrudRepository<PlacesEntity, Long> {
    PlacesEntity findByName(final String name);
}
