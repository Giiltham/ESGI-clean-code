package fr.esgi.fx.avis.application.repository;

import fr.esgi.fx.avis.domain.model.Editeur;

import java.util.List;
import java.util.Optional;

public interface EditeurRepository {
    Optional<Editeur> findById(Long id);

    void deleteById(Long id);

    List<Editeur> findAll();

    List<Editeur> findByNomContainingIgnoreCase(String nom);

    Optional<Editeur> findByNomIgnoreCase(String nom);

    Editeur save(Editeur editeur);
}
