package fr.esgi.fx.avis.adapter.mapper;

import fr.esgi.fx.avis.domain.model.Editeur;
import fr.esgi.fx.avis.adapter.dto.EditeurDto;
import fr.esgi.fx.avis.domain.model.Genre;
import fr.esgi.fx.avis.domain.model.Jeu;
import fr.esgi.fx.avis.domain.model.Plateforme;
import fr.esgi.fx.avis.infrastructure.entity.EditeurEntity;
import fr.esgi.fx.avis.infrastructure.entity.GenreEntity;
import fr.esgi.fx.avis.infrastructure.entity.JeuEntity;
import fr.esgi.fx.avis.infrastructure.entity.PlateformeEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface EditeurMapper {
    Editeur toDomain(EditeurDto editeurDto);

    Editeur toDomain(EditeurEntity editeurEntity);

    EditeurDto toDto(Editeur editeur);

    EditeurEntity toEntity(Editeur editeur);

    @Mapping(target = "editeur", ignore = true)
    Jeu jeuEntityToJeu(JeuEntity jeuEntity);

    @Mapping(target = "jeux", ignore = true)
    Genre genreEntityToGenre(GenreEntity genreEntity);

    @Mapping(target = "jeux", ignore = true)
    Plateforme plateformeEntityToPlateforme(PlateformeEntity plateformeEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Editeur partialUpdate(EditeurDto editeurDto, @MappingTarget Editeur editeur);
}