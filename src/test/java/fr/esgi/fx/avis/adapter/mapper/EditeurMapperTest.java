package fr.esgi.fx.avis.adapter.mapper;

import fr.esgi.fx.avis.adapter.dto.EditeurDto;
import fr.esgi.fx.avis.domain.model.Editeur;
import fr.esgi.fx.avis.infrastructure.entity.EditeurEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class EditeurMapperTest {

    private EditeurMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(EditeurMapper.class);
    }

    @Test
    void toDomain_shouldMapAllFields() {
        EditeurDto dto = new EditeurDto(1L, "Ubisoft");
        Editeur editeur = mapper.toDomain(dto);

        assertNotNull(editeur);
        assertEquals(dto.getId(), editeur.getId());
        assertEquals(dto.getNom(), editeur.getNom());
    }

    @Test
    void toDto_shouldMapAllFields() {
        Editeur editeur = new Editeur();
        editeur.setId(2L);
        editeur.setNom("Nintendo");

        EditeurDto dto = mapper.toDto(editeur);

        assertNotNull(dto);
        assertEquals(editeur.getId(), dto.getId());
        assertEquals(editeur.getNom(), dto.getNom());
    }

    @Test
    void toEntity_shouldMapAllFields() {
        Editeur editeur = new Editeur();
        editeur.setId(3L);
        editeur.setNom("Sony");

        EditeurEntity entity = mapper.toEntity(editeur);

        assertNotNull(entity);
        assertEquals(editeur.getId(), entity.getId());
        assertEquals(editeur.getNom(), entity.getNom());
    }

    @Test
    void toDomain_fromEntity_shouldMapAllFields() {
        EditeurEntity entity = new EditeurEntity();
        entity.setId(4L);
        entity.setNom("Microsoft");

        Editeur editeur = mapper.toDomain(entity);

        assertNotNull(editeur);
        assertEquals(entity.getId(), editeur.getId());
        assertEquals(entity.getNom(), editeur.getNom());
    }

    @Test
    void partialUpdate_shouldUpdateNonNullFields() {
        Editeur editeur = new Editeur();
        editeur.setId(5L);
        editeur.setNom("OldName");

        EditeurDto dto = new EditeurDto(null, "NewName");

        Editeur updated = mapper.partialUpdate(dto, editeur);

        assertEquals(5L, updated.getId()); // id unchanged
        assertEquals("NewName", updated.getNom()); // nom updated
    }

    @Test
    void partialUpdate_shouldIgnoreNullFields() {
        Editeur editeur = new Editeur();
        editeur.setId(6L);
        editeur.setNom("OldName");

        EditeurDto dto = new EditeurDto(null, null);

        Editeur updated = mapper.partialUpdate(dto, editeur);

        assertEquals(6L, updated.getId());
        assertEquals("OldName", updated.getNom()); // unchanged because dto.nom is null
    }
}
