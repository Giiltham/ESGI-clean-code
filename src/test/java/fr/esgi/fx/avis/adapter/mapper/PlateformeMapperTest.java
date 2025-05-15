package fr.esgi.fx.avis.adapter.mapper;

import fr.esgi.fx.avis.adapter.dto.PlateformeDto;
import fr.esgi.fx.avis.domain.model.Plateforme;
import fr.esgi.fx.avis.infrastructure.entity.PlateformeEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import java.time.LocalDate;
import static org.assertj.core.api.Assertions.assertThat;

class PlateformeMapperTest {

    private PlateformeMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(PlateformeMapper.class);
    }

    @Test
    void toDomain_fromDto_mapsAllFields() {
        PlateformeDto dto = new PlateformeDto("PC", "2020-10-10", null);

        Plateforme domain = mapper.toDomain(dto);

        assertThat(domain).isNotNull();
        assertThat(domain.getNom()).isEqualTo("PC");
        assertThat(domain.getDateDeSortie()).isEqualTo(LocalDate.parse("2020-10-10"));  // <- parse en LocalDate
    }

    @Test
    void toDomain_fromEntity_mapsAllFieldsExceptJeux() {
        PlateformeEntity entity = new PlateformeEntity();
        entity.setNom("Xbox");
        entity.setDateDeSortie(LocalDate.of(2019, 9, 15));  // <- ici LocalDate

        Plateforme domain = mapper.toDomain(entity);

        assertThat(domain).isNotNull();
        assertThat(domain.getNom()).isEqualTo("Xbox");
        assertThat(domain.getDateDeSortie()).isEqualTo(LocalDate.of(2019, 9, 15));
    }

    @Test
    void toDto_fromDomain_mapsAllFields() {
        Plateforme domain = new Plateforme();
        domain.setNom("Switch");
        domain.setDateDeSortie(LocalDate.of(2017, 3, 3));  // <- LocalDate

        PlateformeDto dto = mapper.toDto(domain);

        assertThat(dto).isNotNull();
        assertThat(dto.getNom()).isEqualTo("Switch");
        assertThat(dto.getDateDeSortie()).isEqualTo("2017-03-03");  // DTO a string
        assertThat(dto.getJeux()).isNull();
    }

    @Test
    void toEntity_fromDomain_mapsAllFields() {
        Plateforme domain = new Plateforme();
        domain.setNom("PlayStation");
        domain.setDateDeSortie(LocalDate.of(2013, 11, 15));

        PlateformeEntity entity = mapper.toEntity(domain);

        assertThat(entity).isNotNull();
        assertThat(entity.getNom()).isEqualTo("PlayStation");
        assertThat(entity.getDateDeSortie()).isEqualTo(LocalDate.of(2013, 11, 15));
    }

    @Test
    void partialUpdate_shouldUpdateNonNullFieldsOnly() {
        Plateforme domain = new Plateforme();
        domain.setNom("Old Name");
        domain.setDateDeSortie(LocalDate.of(2000, 1, 1));

        PlateformeDto dto = new PlateformeDto("New Name", null, null);

        mapper.partialUpdate(dto, domain);

        assertThat(domain.getNom()).isEqualTo("New Name");
        assertThat(domain.getDateDeSortie()).isEqualTo(LocalDate.of(2000, 1, 1));  // inchangé car null dans DTO
    }
}
