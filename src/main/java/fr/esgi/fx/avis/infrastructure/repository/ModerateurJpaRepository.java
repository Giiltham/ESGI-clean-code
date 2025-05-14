package fr.esgi.fx.avis.infrastructure.repository;

import fr.esgi.fx.avis.infrastructure.entity.ModerateurEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModerateurJpaRepository extends JpaRepository<ModerateurEntity, Long> {
}