package fr.esgi.fx.avis.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class Classification {

    private Long id;

    private String nom;

    private String couleurRGB;

    private List<Jeu> jeux;

}
