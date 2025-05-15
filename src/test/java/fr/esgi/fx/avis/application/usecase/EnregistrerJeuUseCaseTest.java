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

class EnregistrerJeuUseCaseTest {

    @Mock
    private JeuRepository jeuRepository;

    @InjectMocks
    private EnregistrerJeuUseCase enregistrerJeuUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void enregistrerJeu_succes() {
        // Arrange
        Jeu jeu = new Jeu();
        jeu.setNom("Cyberpunk 2077");

        when(jeuRepository.save(jeu)).thenReturn(jeu);

        // Act
        Jeu resultat = enregistrerJeuUseCase.enregistrerJeu(jeu);

        // Assert
        assertEquals("Cyberpunk 2077", resultat.getNom());
        verify(jeuRepository, times(1)).save(jeu);
    }
}
