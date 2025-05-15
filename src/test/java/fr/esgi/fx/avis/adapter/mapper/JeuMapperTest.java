package fr.esgi.fx.avis.adapter.mapper;

import fr.esgi.fx.avis.adapter.dto.*;
import fr.esgi.fx.avis.domain.model.*;
import fr.esgi.fx.avis.infrastructure.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class JeuMapperTest {

    private JeuMapper jeuMapper;

    @BeforeEach
    void setUp() {
        jeuMapper = Mappers.getMapper(JeuMapper.class);
    }

    @Test
    void toDomain_fromDto_mapsAllFields() {
        EditeurDto editeurDto = new EditeurDto(1L, "Ubisoft");
        GenreDto genreDto = new GenreDto(2L, "Action");
        ClassificationDto classificationDto = new ClassificationDto(3L, "PEGI 18", "#FFFFFF");
        PlateformeDto plateformeDto = new PlateformeDto("PC", "2020-01-01", List.of());

        JeuDto dto = new JeuDto(
                10L,
                "Assassin's Creed",
                editeurDto,
                genreDto,
                classificationDto,
                "Un jeu d'action",
                LocalDate.of(2020, 10, 10),
                List.of(plateformeDto)
        );

        Jeu domain = jeuMapper.toDomain(dto);

        assertThat(domain).isNotNull();
        assertThat(domain.getId()).isEqualTo(10L);
        assertThat(domain.getNom()).isEqualTo("Assassin's Creed");
        assertThat(domain.getEditeur()).isNotNull();
        assertThat(domain.getEditeur().getId()).isEqualTo(1L);
        assertThat(domain.getEditeur().getNom()).isEqualTo("Ubisoft");
        assertThat(domain.getGenre()).isNotNull();
        assertThat(domain.getGenre().getId()).isEqualTo(2L);
        assertThat(domain.getGenre().getNom()).isEqualTo("Action");
        assertThat(domain.getClassification()).isNotNull();
        assertThat(domain.getClassification().getId()).isEqualTo(3L);
        assertThat(domain.getClassification().getNom()).isEqualTo("PEGI 18");
        assertThat(domain.getClassification().getCouleurRGB()).isEqualTo("#FFFFFF");
        assertThat(domain.getDescription()).isEqualTo("Un jeu d'action");
        assertThat(domain.getDateDeSortie()).isEqualTo(LocalDate.of(2020, 10, 10));
        assertThat(domain.getPlateformes()).hasSize(1);
        assertThat(domain.getPlateformes().get(0).getNom()).isEqualTo("PC");
    }
}