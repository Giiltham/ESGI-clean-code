package fr.esgi.fx.avis.adapters.mapper;

import fr.esgi.fx.avis.domain.model.Editeur;
import fr.esgi.fx.avis.domain.model.Genre;
import fr.esgi.fx.avis.domain.model.Jeu;
import fr.esgi.fx.avis.adapters.dto.JeuDto;
import fr.esgi.fx.avis.domain.model.Plateforme;
import fr.esgi.fx.avis.infrastructure.entity.EditeurEntity;
import fr.esgi.fx.avis.infrastructure.entity.GenreEntity;
import fr.esgi.fx.avis.infrastructure.entity.JeuEntity;
import fr.esgi.fx.avis.infrastructure.entity.PlateformeEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface JeuMapper {
    Jeu toDomain(JeuDto jeuDto);

    Jeu toDomain(JeuEntity jeuEntity);

    @Mapping(target = "jeux", ignore = true)
    Plateforme plateformeEntityToPlateforme(PlateformeEntity entity);

    @Mapping(target = "jeux", ignore = true)
    Genre genreEntityToGenre(GenreEntity genreEntity);

    @Mapping(target= "jeux", ignore = true)
    Editeur editeurEntityToEditeur(EditeurEntity editeurEntity);

    JeuDto toDto(Jeu jeu);

    JeuEntity toEntity(Jeu jeu);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Jeu partialUpdate(JeuDto jeuDto, @MappingTarget Jeu jeu);
}