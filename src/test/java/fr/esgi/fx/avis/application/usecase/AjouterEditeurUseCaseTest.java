package fr.esgi.fx.avis.application.usecase;

import fr.esgi.fx.avis.application.exception.EditeurDejaPresentException;
import fr.esgi.fx.avis.application.repository.EditeurRepository;
import fr.esgi.fx.avis.domain.model.Editeur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AjouterEditeurUseCaseTest {

    @Mock
    private EditeurRepository editeurRepository;

    @InjectMocks
    private AjouterEditeurUseCase ajouterEditeurUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void ajouterEditeur_succes() {
        // Arrange
        Editeur editeur = new Editeur();
        editeur.setNom("Ubisoft");

        when(editeurRepository.findByNomIgnoreCase("Ubisoft")).thenReturn(Optional.empty());
        when(editeurRepository.save(editeur)).thenReturn(editeur);

        // Act
        Editeur resultat = ajouterEditeurUseCase.ajouterEditeur(editeur);

        // Assert
        assertNotNull(resultat);
        assertEquals("Ubisoft", resultat.getNom());
        verify(editeurRepository, times(1)).save(editeur);
    }

    @Test
    void ajouterEditeur_dejaPresent() {
        // Arrange
        Editeur editeur = new Editeur();
        editeur.setNom("Ubisoft");

        when(editeurRepository.findByNomIgnoreCase("Ubisoft")).thenReturn(Optional.of(editeur));

        // Act & Assert
        assertThrows(EditeurDejaPresentException.class, () -> ajouterEditeurUseCase.ajouterEditeur(editeur));
        verify(editeurRepository, never()).save(editeur);
    }
}
