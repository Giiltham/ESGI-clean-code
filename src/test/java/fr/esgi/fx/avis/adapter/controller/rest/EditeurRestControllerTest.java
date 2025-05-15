import fr.esgi.fx.avis.adapter.dto.EditeurDto;
import fr.esgi.fx.avis.adapter.mapper.EditeurMapper;
import fr.esgi.fx.avis.application.usecase.AjouterEditeurUseCase;
import fr.esgi.fx.avis.application.usecase.RecupererEditeurUseCase;
import fr.esgi.fx.avis.application.usecase.SupprimerEditeurUseCase;
import fr.esgi.fx.avis.domain.model.Editeur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import fr.esgi.fx.avis.adapter.controller.rest.EditeurRestController;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EditeurRestControllerTest {

    @Mock
    private RecupererEditeurUseCase recupererEditeurUseCase;

    @Mock
    private AjouterEditeurUseCase ajouterEditeurUseCase;

    @Mock
    private SupprimerEditeurUseCase supprimerEditeurUseCase;

    @Mock
    private EditeurMapper editeurMapper;

    @InjectMocks
    private EditeurRestController editeurRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getEditeurs_succes() {
        // Arrange
        Editeur editeur = new Editeur();
        EditeurDto editeurDto = new EditeurDto(1L, "NomEditeur");

        when(recupererEditeurUseCase.recupererEditeurs()).thenReturn(List.of(editeur));
        when(editeurMapper.toDto(editeur)).thenReturn(editeurDto);

        // Act
        List<EditeurDto> result = editeurRestController.getEditeurs();

        // Assert
        assertEquals(1, result.size());
        verify(recupererEditeurUseCase, times(1)).recupererEditeurs();
    }

    @Test
    void postEditeur_succes() {
        // Arrange
        EditeurDto editeurDto = new EditeurDto(1L, "NomEditeur");
        Editeur editeur = new Editeur();

        when(editeurMapper.toDomain(editeurDto)).thenReturn(editeur);
        when(ajouterEditeurUseCase.ajouterEditeur(editeur)).thenReturn(editeur);
        when(editeurMapper.toDto(editeur)).thenReturn(editeurDto);

        // Act
        EditeurDto result = editeurRestController.postEditeur(editeurDto, null);

        // Assert
        assertEquals(editeurDto, result);
        verify(ajouterEditeurUseCase, times(1)).ajouterEditeur(editeur);
    }

    @Test
    void deleteEditeur_succes() {
        // Arrange
        Long id = 1L;

        // Act
        editeurRestController.deleteEditeur(id);

        // Assert
        verify(supprimerEditeurUseCase, times(1)).supprimerEditeur(id);
    }
}
