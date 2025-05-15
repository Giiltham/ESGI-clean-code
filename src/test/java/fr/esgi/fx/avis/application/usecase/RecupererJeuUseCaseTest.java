package fr.esgi.fx.avis.application.usecase;

import fr.esgi.fx.avis.application.exception.JeuInexistantException;
import fr.esgi.fx.avis.application.repository.JeuRepository;
import fr.esgi.fx.avis.domain.model.Jeu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecupererJeuUseCaseTest {

    @Mock
    private JeuRepository jeuRepository;

    @InjectMocks
    private RecupererJeuUseCase recupererJeuUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void recupererJeux_succes() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Jeu jeu1 = new Jeu();
        jeu1.setNom("Zelda");

        Jeu jeu2 = new Jeu();
        jeu2.setNom("Mario Kart");

        Page<Jeu> pageJeux = new PageImpl<>(List.of(jeu1, jeu2));
        when(jeuRepository.findAll(pageable)).thenReturn(pageJeux);

        // Act
        Page<Jeu> result = recupererJeuUseCase.recupererJeux(pageable);

        // Assert
        assertEquals(2, result.getTotalElements());
        assertEquals("Zelda", result.getContent().get(0).getNom());
        verify(jeuRepository, times(1)).findAll(pageable);
    }

    @Test
    void recupererJeu_succes() {
        // Arrange
        Long idJeu = 1L;
        Jeu jeu = new Jeu();
        jeu.setId(idJeu);
        jeu.setNom("Zelda");

        when(jeuRepository.findById(idJeu)).thenReturn(Optional.of(jeu));

        // Act
        Jeu result = recupererJeuUseCase.recupererJeu(idJeu);

        // Assert
        assertEquals("Zelda", result.getNom());
        verify(jeuRepository, times(1)).findById(idJeu);
    }

    @Test
    void recupererJeu_inexistant() {
        // Arrange
        Long idJeu = 99L;

        when(jeuRepository.findById(idJeu)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(JeuInexistantException.class, () -> recupererJeuUseCase.recupererJeu(idJeu));
        verify(jeuRepository, times(1)).findById(idJeu);
    }
}
