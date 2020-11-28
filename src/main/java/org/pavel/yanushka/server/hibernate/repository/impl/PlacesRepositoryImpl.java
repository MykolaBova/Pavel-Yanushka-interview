package org.pavel.yanushka.server.hibernate.repository.impl;

import org.pavel.yanushka.server.hibernate.entities.PlacesEntity;
import org.pavel.yanushka.server.hibernate.repository.PlacesRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class PlacesRepositoryImpl implements PlacesRepository {
    @PersistenceContext
    private final EntityManager em;

    public PlacesRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PlacesEntity> getAllPlaces() {
        return em.createQuery("from " + PlacesEntity.class.getName()).getResultList();
    }

    @Override
    public PlacesEntity getPlaceByName(String name) {
        return em.createQuery(
                "SELECT u from PlacesEntity u WHERE u.name = :name", PlacesEntity.class).
                setParameter("name", name).getSingleResult();
    }
}
