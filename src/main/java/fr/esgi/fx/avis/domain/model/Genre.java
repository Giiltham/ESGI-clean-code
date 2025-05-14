package fr.esgi.fx.avis.domain.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@Data
public class Genre {

    private Long id;

    private String nom;

    private List<Jeu> jeux;

    @Override
    public String toString() {
        return nom;
    }
}
