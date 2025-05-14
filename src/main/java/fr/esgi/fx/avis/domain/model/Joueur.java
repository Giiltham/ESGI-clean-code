package fr.esgi.fx.avis.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@ToString(callSuper = true)
@Data
public class Joueur extends Utilisateur {

    private LocalDate dateDeNaissance;

    // Hypothèse de Nicolas : il ne sait pas comment initialiser la liste d'avis
    private List<Avis> avis;

    private Avatar avatar;

}
