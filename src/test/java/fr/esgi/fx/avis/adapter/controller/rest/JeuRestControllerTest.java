import fr.esgi.fx.avis.adapter.dto.*;
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
import org.springframework.web.multipart.MultipartFile;
import fr.esgi.fx.avis.application.usecase.RecupererJeuUseCase;
import fr.esgi.fx.avis.application.usecase.AjouterJeuUseCase;
import fr.esgi.fx.avis.application.usecase.AjouterImageJeuUseCase;
import fr.esgi.fx.avis.adapter.mapper.JeuMapper;
import fr.esgi.fx.avis.adapter.controller.rest.JeuRestController;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class JeuRestControllerTest {

    @Mock
    private RecupererJeuUseCase recupererJeuUseCase;

    @Mock
    private AjouterJeuUseCase ajouterJeuUseCase;

    @Mock
    private AjouterImageJeuUseCase ajouterImageJeuUseCase;

    @Mock
    private JeuMapper jeuMapper;

    @InjectMocks
    private JeuRestController jeuRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getJeux_succes() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);

        Jeu jeu = new Jeu();  // à adapter si Jeu n'a pas constructeur vide

        // Construire un JeuDto complet avec tous les champs
        ClassificationDto classificationDto = new ClassificationDto(1L, "PEGI 18", "#FF0000");
        EditeurDto editeurDto = new EditeurDto(1L, "Ubisoft");
        GenreDto genreDto = new GenreDto(1L, "Action");
        PlateformeDto plateformeDto = new PlateformeDto("PC", "2020-01-01", List.of());

        JeuDto jeuDto = new JeuDto(
                1L,
                "Assassin's Creed",
                editeurDto,
                genreDto,
                classificationDto,
                "Un jeu d'action-aventure",
                LocalDate.of(2020, 1, 1),
                List.of(plateformeDto)
        );

        Page<Jeu> pageJeu = new PageImpl<>(List.of(jeu));

        when(recupererJeuUseCase.recupererJeux(pageable)).thenReturn(pageJeu);
        when(jeuMapper.toDto(jeu)).thenReturn(jeuDto);

        // Act
        Page<JeuDto> result = jeuRestController.getJeux(pageable);

        // Assert
        assertEquals(1, result.getContent().size());
        verify(recupererJeuUseCase, times(1)).recupererJeux(pageable);
    }

    @Test
    void postJeu_succes() {
        // Arrange
        ClassificationDto classificationDto = new ClassificationDto(1L, "PEGI 18", "#FF0000");
        EditeurDto editeurDto = new EditeurDto(1L, "Ubisoft");
        GenreDto genreDto = new GenreDto(1L, "Action");
        PlateformeDto plateformeDto = new PlateformeDto("PC", "2020-01-01", List.of());

        JeuDto jeuDto = new JeuDto(
                1L,
                "Assassin's Creed",
                editeurDto,
                genreDto,
                classificationDto,
                "Un jeu d'action-aventure",
                LocalDate.of(2020, 1, 1),
                List.of(plateformeDto)
        );

        Jeu jeu = new Jeu();

        when(jeuMapper.toDomain(jeuDto)).thenReturn(jeu);
        when(ajouterJeuUseCase.ajouterJeu(jeu)).thenReturn(jeu);
        when(jeuMapper.toDto(jeu)).thenReturn(jeuDto);

        // Act
        JeuDto result = jeuRestController.postJeu(jeuDto, null);

        // Assert
        assertEquals(jeuDto, result);
        verify(ajouterJeuUseCase, times(1)).ajouterJeu(jeu);
    }

    @Test
    void postImage_succes() {
        // Arrange
        Long idJeu = 1L;
        MultipartFile file = mock(MultipartFile.class);

        when(ajouterImageJeuUseCase.ajouterImage(idJeu, file)).thenReturn(true);

        // Act
        boolean result = jeuRestController.postImage(idJeu, file);

        // Assert
        assertEquals(true, result);
        verify(ajouterImageJeuUseCase, times(1)).ajouterImage(idJeu, file);
    }
}
