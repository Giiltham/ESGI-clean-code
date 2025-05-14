package fr.esgi.fx.avis.infrastructure.repository;

import fr.esgi.fx.avis.infrastructure.entity.AvatarEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvatarJpaRepository extends JpaRepository<AvatarEntity, Long> {
}