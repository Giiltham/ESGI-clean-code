package fr.esgi.fx.avis.application.usecase;

import fr.esgi.fx.avis.application.exception.EditeurInexistantException;
import fr.esgi.fx.avis.application.repository.EditeurRepository;
import fr.esgi.fx.avis.domain.model.Editeur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class SupprimerEditeurUseCaseTest {

    @Mock
    private EditeurRepository editeurRepository;

    @InjectMocks
    private SupprimerEditeurUseCase supprimerEditeurUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void supprimerEditeur_succes() {
        // Arrange
        Long id = 1L;
        Editeur editeur = new Editeur();
        editeur.setId(id);

        when(editeurRepository.findById(id)).thenReturn(Optional.of(editeur));

        // Act
        supprimerEditeurUseCase.supprimerEditeur(id);

        // Assert
        verify(editeurRepository, times(1)).findById(id);
        verify(editeurRepository, times(1)).deleteById(id);
    }

    @Test
    void supprimerEditeur_inexistant() {
        // Arrange
        Long id = 99L;
        when(editeurRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EditeurInexistantException.class, () -> supprimerEditeurUseCase.supprimerEditeur(id));
        verify(editeurRepository, times(1)).findById(id);
        verify(editeurRepository, never()).deleteById(id);
    }
}
