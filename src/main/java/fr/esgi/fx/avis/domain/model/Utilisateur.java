package fr.esgi.fx.avis.domain.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public abstract class Utilisateur {

    protected Long id;

    protected String pseudo;

    protected String motDePasse;

    protected String email;

    public Utilisateur(String pseudo, String motDePasse, String email){
        this.pseudo = pseudo;
        this.motDePasse = motDePasse;
        this.email = email;
    }
}
