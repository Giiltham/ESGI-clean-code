package fr.esgi.fx.avis.adapters.mapper;

import fr.esgi.fx.avis.domain.model.Genre;
import fr.esgi.fx.avis.adapters.dto.GenreDto;
import fr.esgi.fx.avis.infrastructure.entity.GenreEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface GenreMapper {
    Genre toDomain(GenreDto genreDto);

    Genre toDomain(GenreEntity genreEntity);

    GenreDto toDto(Genre genre);

    GenreEntity toEntity(Genre genre);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Genre partialUpdate(GenreDto genreDto, @MappingTarget Genre genre);
}