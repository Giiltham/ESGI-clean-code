package fr.esgi.fx.avis.infrastructure.repository;

import fr.esgi.fx.avis.infrastructure.entity.EditeurEntity;
import fr.esgi.fx.avis.infrastructure.entity.JeuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EditeurJpaRepository extends JpaRepository<EditeurEntity, Long> {
  @Query("""
          FROM EditeurEntity e
          WHERE size(e.jeux)=0
          """)
  List<JeuEntity> findEditorsWithoutGames();

  List<EditeurEntity> findByNomContainingIgnoreCase(String nom);

  Optional<EditeurEntity> findByNom(String nom);

  Optional<EditeurEntity> findByNomIgnoreCase(String nom);

}