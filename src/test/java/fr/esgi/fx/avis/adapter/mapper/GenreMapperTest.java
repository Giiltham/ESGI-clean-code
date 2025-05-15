package fr.esgi.fx.avis.adapter.mapper;

import fr.esgi.fx.avis.adapter.dto.GenreDto;
import fr.esgi.fx.avis.domain.model.Genre;
import fr.esgi.fx.avis.infrastructure.entity.GenreEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class GenreMapperTest {

    private GenreMapper genreMapper;

    @BeforeEach
    void setUp() {
        genreMapper = Mappers.getMapper(GenreMapper.class);
    }

    @Test
    void testToDomainFromDto() {
        GenreDto dto = new GenreDto(1L, "Action");
        Genre genre = genreMapper.toDomain(dto);

        assertThat(genre).isNotNull();
        assertThat(genre.getId()).isEqualTo(1L);
        assertThat(genre.getNom()).isEqualTo("Action");
        assertThat(genre.getJeux()).isNull();  // Non mappé, reste null
    }

    @Test
    void testToDto() {
        Genre genre = new Genre();
        genre.setId(2L);
        genre.setNom("RPG");

        GenreDto dto = genreMapper.toDto(genre);

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(2L);
        assertThat(dto.getNom()).isEqualTo("RPG");
    }

    @Test
    void testToDomainFromEntity() {
        GenreEntity entity = new GenreEntity();
        entity.setId(3L);
        entity.setNom("Aventure");

        Genre genre = genreMapper.toDomain(entity);

        assertThat(genre).isNotNull();
        assertThat(genre.getId()).isEqualTo(3L);
        assertThat(genre.getNom()).isEqualTo("Aventure");
    }

    @Test
    void testToEntity() {
        Genre genre = new Genre();
        genre.setId(4L);
        genre.setNom("Simulation");

        GenreEntity entity = genreMapper.toEntity(genre);

        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(4L);
        assertThat(entity.getNom()).isEqualTo("Simulation");
    }

    @Test
    void testPartialUpdate() {
        Genre genre = new Genre();
        genre.setId(5L);
        genre.setNom("Initial");

        GenreDto dto = new GenreDto(null, "Updated");

        genreMapper.partialUpdate(dto, genre);

        assertThat(genre.getId()).isEqualTo(5L); // Non modifié car dto.id est null
        assertThat(genre.getNom()).isEqualTo("Updated"); // Modifié
    }
}
