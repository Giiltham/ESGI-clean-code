package fr.esgi.fx.avis.adapter.dto;

import fr.esgi.fx.avis.domain.model.Plateforme;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link Plateforme}
 */
@Value
public class PlateformeDto implements Serializable {
    String nom;
    String dateDeSortie;
    List<JeuDto> jeux;
}