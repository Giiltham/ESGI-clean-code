package fr.esgi.fx.avis.adapter.dto;

import fr.esgi.fx.avis.domain.model.Genre;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link Genre}
 */
@Value
public class GenreDto implements Serializable {
    Long id;
    String nom;
}