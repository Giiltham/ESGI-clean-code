package fr.esgi.fx.avis.adapter.mapper;

import fr.esgi.fx.avis.domain.model.Plateforme;
import fr.esgi.fx.avis.adapter.dto.PlateformeDto;
import fr.esgi.fx.avis.infrastructure.entity.PlateformeEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PlateformeMapper {
    Plateforme toDomain(PlateformeDto plateformeDto);

    @Mapping(target = "jeux", ignore = true)
    Plateforme toDomain(PlateformeEntity plateformeEntity);

    PlateformeDto toDto(Plateforme plateforme);

    PlateformeEntity toEntity(Plateforme plateforme);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Plateforme partialUpdate(PlateformeDto plateformeDto, @MappingTarget Plateforme plateforme);
}