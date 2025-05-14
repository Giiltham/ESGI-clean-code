package fr.esgi.fx.avis.infrastructure.repository;

import fr.esgi.fx.avis.infrastructure.entity.ClassificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ClassificationJpaRepository extends JpaRepository<ClassificationEntity, Long> {
}
