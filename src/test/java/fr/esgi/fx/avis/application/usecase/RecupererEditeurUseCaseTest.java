package fr.esgi.fx.avis.application.usecase;

import fr.esgi.fx.avis.application.exception.EditeurInexistantException;
import fr.esgi.fx.avis.application.repository.EditeurRepository;
import fr.esgi.fx.avis.domain.model.Editeur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecupererEditeurUseCaseTest {

    @Mock
    private EditeurRepository editeurRepository;

    @InjectMocks
    private RecupererEditeurUseCase recupererEditeurUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void recupererEditeurs_succes() {
        // Arrange
        Editeur editeur1 = new Editeur();
        editeur1.setNom("Ubisoft");

        Editeur editeur2 = new Editeur();
        editeur2.setNom("Electronic Arts");

        when(editeurRepository.findAll()).thenReturn(List.of(editeur1, editeur2));

        // Act
        List<Editeur> editeurs = recupererEditeurUseCase.recupererEditeurs();

        // Assert
        assertEquals(2, editeurs.size());
        assertEquals("Ubisoft", editeurs.get(0).getNom());
        assertEquals("Electronic Arts", editeurs.get(1).getNom());
        verify(editeurRepository, times(1)).findAll();
    }

    @Test
    void recupererEditeursParNomContenant_succes() {
        // Arrange
        Editeur editeur = new Editeur();
        editeur.setNom("Ubisoft");

        when(editeurRepository.findByNomContainingIgnoreCase("ubi")).thenReturn(List.of(editeur));

        // Act
        List<Editeur> editeurs = recupererEditeurUseCase.recupererEditeursParNomContenant("ubi");

        // Assert
        assertEquals(1, editeurs.size());
        assertEquals("Ubisoft", editeurs.get(0).getNom());
        verify(editeurRepository, times(1)).findByNomContainingIgnoreCase("ubi");
    }

    @Test
    void recupererEditeur_succes() {
        // Arrange
        Long id = 1L;
        Editeur editeur = new Editeur();
        editeur.setId(id);
        editeur.setNom("Square Enix");

        when(editeurRepository.findById(id)).thenReturn(Optional.of(editeur));

        // Act
        Editeur resultat = recupererEditeurUseCase.recupererEditeur(id);

        // Assert
        assertEquals("Square Enix", resultat.getNom());
        verify(editeurRepository, times(1)).findById(id);
    }

    @Test
    void recupererEditeur_inexistant() {
        // Arrange
        Long id = 99L;

        when(editeurRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EditeurInexistantException.class, () -> recupererEditeurUseCase.recupererEditeur(id));
        verify(editeurRepository, times(1)).findById(id);
    }
}
