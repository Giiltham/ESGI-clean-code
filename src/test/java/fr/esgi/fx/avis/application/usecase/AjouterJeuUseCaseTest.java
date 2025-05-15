package fr.esgi.fx.avis.application.usecase;

import fr.esgi.fx.avis.application.repository.JeuRepository;
import fr.esgi.fx.avis.domain.model.Jeu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AjouterJeuUseCaseTest {

    @Mock
    private JeuRepository jeuRepository;

    @InjectMocks
    private AjouterJeuUseCase ajouterJeuUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void ajouterJeu_succes() {
        // Arrange
        Jeu jeu = new Jeu();
        jeu.setNom("The Witcher 3");

        when(jeuRepository.save(jeu)).thenReturn(jeu);

        // Act
        Jeu resultat = ajouterJeuUseCase.ajouterJeu(jeu);

        // Assert
        assertEquals("The Witcher 3", resultat.getNom());
        verify(jeuRepository, times(1)).save(jeu);
    }
}
