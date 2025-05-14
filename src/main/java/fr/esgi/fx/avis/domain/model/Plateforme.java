package fr.esgi.fx.avis.domain.model;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Data
public class Plateforme {

    private Long id;

    private String nom;

    @ToString.Exclude
    private List<Jeu> jeux;

    private LocalDate dateDeSortie;
}
