package fr.esgi.fx.avis.infrastructure.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@DynamicUpdate
@DynamicInsert
@Table(indexes = @Index(name="Jeu_Nom_Index", columnList = "nom"))
public class JeuEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "jeu_seq")
    @SequenceGenerator(name = "jeu_seq")
    private Long id;

    @Column(nullable = false)
    @NonNull
    private String nom;

    @NonNull
    @ManyToOne // Many Jeu to One Editeur
    @JsonManagedReference
    private EditeurEntity editeur;

    @ManyToOne // Many Jeu to One Genre
    private GenreEntity genre;

    @ManyToOne
    private ClassificationEntity classification;

    @Lob
    private String description;

    private LocalDate dateDeSortie;

    @ManyToMany
    private List<PlateformeEntity> plateformes;

    private String image;

    private float prix;

    //private int version;

    public JeuEntity(String nom) {
        super();
        this.nom = nom;
    }

    public JeuEntity(String nom, LocalDate dateDeSortie, EditeurEntity editeurEntity) {
        this(nom, editeurEntity);
        this.dateDeSortie = dateDeSortie;
    }

    public JeuEntity(String nom, String description, LocalDate dateSortie, EditeurEntity editeurEntity) {
        this(nom, dateSortie, editeurEntity);
        this.description = description;
    }

    public JeuEntity(String nom, LocalDate dateSortie, EditeurEntity editeurEntity, GenreEntity genre) {
        this(nom, null, dateSortie, editeurEntity);
        this.genre = genre;
    }

}
