package fr.esgi.fx.avis.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class Jeu {

    private Long id;

    private String nom;

    private Editeur editeur;

    private Genre genre;

    private Classification classification;

    private String description;

    private LocalDate dateDeSortie;

    private List<Plateforme> plateformes;

    private String image;

    private float prix;


    public Jeu(String nom) {
        super();
        this.nom = nom;
    }

    public Jeu(String nom, Editeur editeur){
        this(nom);
        this.editeur = editeur;
    }

    public Jeu(String nom, LocalDate dateDeSortie, Editeur editeur) {
        this(nom, editeur);
        this.dateDeSortie = dateDeSortie;
    }

    public Jeu(String nom, String description, LocalDate dateSortie, Editeur editeur) {
        this(nom, dateSortie, editeur);
        this.description = description;
    }

    public Jeu(String nom, LocalDate dateSortie, Editeur editeur, Genre genre) {
        this(nom, null, dateSortie, editeur);
        this.genre = genre;
    }

}
