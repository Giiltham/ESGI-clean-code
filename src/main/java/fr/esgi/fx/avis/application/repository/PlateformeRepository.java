package fr.esgi.fx.avis.application.repository;

import fr.esgi.fx.avis.domain.model.Plateforme;

import java.util.List;

public interface PlateformeRepository {
    List<Plateforme> findAll();
}
