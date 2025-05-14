package fr.esgi.fx.avis.infrastructure.repository;

import fr.esgi.fx.avis.infrastructure.entity.AvisEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvisJpaRepository extends JpaRepository<AvisEntity, Long> {
}