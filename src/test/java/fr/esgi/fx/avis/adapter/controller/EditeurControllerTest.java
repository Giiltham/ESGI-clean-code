package fr.esgi.fx.avis.adapter.controller;

import fr.esgi.fx.avis.application.usecase.AjouterEditeurUseCase;
import fr.esgi.fx.avis.application.usecase.RecupererEditeurUseCase;
import fr.esgi.fx.avis.domain.model.Editeur;
import jakarta.validation.Valid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EditeurControllerTest {

    @Mock
    private RecupererEditeurUseCase recupererEditeurUseCase;

    @Mock
    private AjouterEditeurUseCase ajouterEditeurUseCase;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private EditeurController editeurController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getEditeurs_succes() {
        // Arrange
        Editeur editeur1 = new Editeur();
        editeur1.setNom("Ubisoft");

        Editeur editeur2 = new Editeur();
        editeur2.setNom("EA Games");

        when(recupererEditeurUseCase.recupererEditeurs()).thenReturn(List.of(editeur1, editeur2));

        // Act
        ModelAndView mav = editeurController.getEditeurs();

        // Assert
        assertEquals("editeurs", mav.getViewName());
        assertEquals(2, ((List<?>) mav.getModel().get("editeurs")).size());
        verify(recupererEditeurUseCase, times(1)).recupererEditeurs();
    }

    @Test
    void postEditeurs_succes() {
        // Arrange
        String nomRecherche = "Ubisoft";
        Editeur editeur = new Editeur();
        editeur.setNom("Ubisoft");

        when(recupererEditeurUseCase.recupererEditeursParNomContenant(nomRecherche))
                .thenReturn(List.of(editeur));

        // Act
        ModelAndView mav = editeurController.postEditeurs(nomRecherche);

        // Assert
        assertEquals("editeurs", mav.getViewName());
        assertEquals(1, ((List<?>) mav.getModel().get("editeurs")).size());
        assertEquals(nomRecherche, mav.getModel().get("nom"));
        verify(recupererEditeurUseCase, times(1)).recupererEditeursParNomContenant(nomRecherche);
    }

    @Test
    void getEditeur_succes() {
        // Arrange
        Editeur editeur = new Editeur();

        // Act
        ModelAndView mav = editeurController.getEditeur(editeur);

        // Assert
        assertEquals("editeur", mav.getViewName());
    }

    @Test
    void postEditeur_succes() {
        // Arrange
        Editeur editeur = new Editeur();
        editeur.setNom("Ubisoft");

        when(bindingResult.hasErrors()).thenReturn(false);

        // Act
        ModelAndView mav = editeurController.postEditeur(editeur, bindingResult);

        // Assert
        assertEquals("redirect:/editeurs", mav.getViewName());
        verify(ajouterEditeurUseCase, times(1)).ajouterEditeur(editeur);
    }

    @Test
    void postEditeur_erreurValidation() {
        // Arrange
        Editeur editeur = new Editeur();
        when(bindingResult.hasErrors()).thenReturn(true);

        // Act
        ModelAndView mav = editeurController.postEditeur(editeur, bindingResult);

        // Assert
        assertEquals("editeur", mav.getViewName());
        verify(ajouterEditeurUseCase, never()).ajouterEditeur(editeur);
    }
}
