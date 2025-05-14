package fr.esgi.fx.avis.adapters.dto;

import fr.esgi.fx.avis.domain.model.Classification;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link Classification}
 */
@Value
public class ClassificationDto implements Serializable {
    Long id;
    String nom;
    String couleurRGB;
}