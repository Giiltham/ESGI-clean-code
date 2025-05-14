package fr.esgi.fx.avis.application.repository;

import fr.esgi.fx.avis.domain.model.Jeu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface JeuRepository {
    Jeu save(Jeu jeu);

    Page<Jeu> findAll(Pageable pageable);

    Optional<Jeu> findById(Long idJeu);
}
