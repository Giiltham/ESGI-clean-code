package fr.esgi.fx.avis.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Avis {

    private Long id;

    private String description;

    private Jeu jeu;

    private Joueur joueur;

    private Float note;

    private LocalDateTime dateDEnvoi;

    private Moderateur moderateur;
}
