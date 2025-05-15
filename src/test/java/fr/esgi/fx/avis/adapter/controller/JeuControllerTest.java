package fr.esgi.fx.avis.adapter.controller;

import fr.esgi.fx.avis.application.usecase.AjouterImageJeuUseCase;
import fr.esgi.fx.avis.application.usecase.RecupererJeuUseCase;
import fr.esgi.fx.avis.domain.model.Jeu;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class JeuControllerTest {

    @Mock
    private RecupererJeuUseCase recupererJeuUseCase;

    @Mock
    private AjouterImageJeuUseCase ajouterImageJeuUseCase;

    @Mock
    private HttpServletRequest request;

    @Mock
    private MultipartFile image;

    @InjectMocks
    private JeuController jeuController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getJeux_succes() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Jeu jeu = new Jeu();
        jeu.setNom("Jeu Test");
        Page<Jeu> pageDeJeux = new PageImpl<>(List.of(jeu));

        when(recupererJeuUseCase.recupererJeux(pageable)).thenReturn(pageDeJeux);
        when(request.getAttribute("dateHeureDebut")).thenReturn(new Date().getTime());

        // Act
        ModelAndView mav = jeuController.getJeux(pageable, request);

        // Assert
        assertEquals("jeux", mav.getViewName());
        assertEquals(pageDeJeux, mav.getModel().get("pageDeJeux"));
        verify(recupererJeuUseCase, times(1)).recupererJeux(pageable);
    }

    @Test
    void getTeleversement_succes() {
        // Arrange
        Long idJeu = 1L;
        Jeu jeu = new Jeu();
        jeu.setNom("Jeu Test");

        when(recupererJeuUseCase.recupererJeu(idJeu)).thenReturn(jeu);

        // Act
        ModelAndView mav = jeuController.getTeleversement(idJeu);

        // Assert
        assertEquals("televersement", mav.getViewName());
        assertEquals(jeu, mav.getModel().get("jeu"));
    }

    @Test
    void postTeleversement_succes() {
        // Arrange
        Long idJeu = 1L;

        // Act
        ModelAndView mav = jeuController.postTeleversement(idJeu, image);

        // Assert
        assertEquals("redirect:/jeux", mav.getViewName());
        verify(ajouterImageJeuUseCase, times(1)).ajouterImage(idJeu, image);
    }
}
