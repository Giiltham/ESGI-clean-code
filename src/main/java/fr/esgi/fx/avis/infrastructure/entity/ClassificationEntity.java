package fr.esgi.fx.avis.infrastructure.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Data
public class ClassificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String nom;

    @NonNull
    private String couleurRGB;

    @OneToMany(mappedBy = "classification")
    @JsonIgnore
    private List<JeuEntity> jeux;

}
