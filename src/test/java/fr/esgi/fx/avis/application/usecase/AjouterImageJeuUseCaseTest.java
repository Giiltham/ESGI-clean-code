package fr.esgi.fx.avis.application.usecase;

import fr.esgi.fx.avis.application.repository.JeuRepository;
import fr.esgi.fx.avis.domain.model.Jeu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AjouterImageJeuUseCaseTest {

    @Mock
    private JeuRepository jeuRepository;

    @Mock
    private MultipartFile image;

    @InjectMocks
    private AjouterImageJeuUseCase ajouterImageJeuUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void ajouterImage_succes() throws IOException {
        // Arrange
        Long jeuId = 1L;
        Jeu jeu = new Jeu();
        jeu.setId(jeuId);

        Path chemin = Paths.get("src/main/resources/static/images/");
        if (!Files.exists(chemin)) {
            Files.createDirectories(chemin);  // Création du dossier s'il n'existe pas
        }

        Path cheminFichier = chemin.resolve(jeuId + ".jpg");

        // On mock le fichier image
        InputStream inputStream = mock(InputStream.class);
        when(image.getInputStream()).thenReturn(inputStream);
        when(jeuRepository.findById(jeuId)).thenReturn(Optional.of(jeu));

        // Act
        boolean resultat = ajouterImageJeuUseCase.ajouterImage(jeuId, image);

        // Assert
        assertTrue(resultat);
        assertEquals("1.jpg", jeu.getImage());
        verify(jeuRepository, times(1)).save(jeu);

        // Nettoyage du fichier après le test
        if (Files.exists(cheminFichier)) {
            Files.delete(cheminFichier);
        }
    }


    @Test
    void ajouterImage_jeuInexistant() {
        // Arrange
        Long jeuId = 1L;

        when(jeuRepository.findById(jeuId)).thenReturn(Optional.empty());

        // Act
        boolean resultat = ajouterImageJeuUseCase.ajouterImage(jeuId, image);

        // Assert
        assertFalse(resultat);
        verify(jeuRepository, never()).save(any());
    }

    @Test
    void ajouterImage_erreurIO() throws IOException {
        // Arrange
        Long jeuId = 1L;
        Jeu jeu = new Jeu();
        jeu.setId(jeuId);

        when(jeuRepository.findById(jeuId)).thenReturn(Optional.of(jeu));
        when(image.getInputStream()).thenThrow(new IOException("Erreur IO"));

        // Act
        boolean resultat = ajouterImageJeuUseCase.ajouterImage(jeuId, image);

        // Assert
        assertFalse(resultat);
        verify(jeuRepository, never()).save(any());
    }
}
