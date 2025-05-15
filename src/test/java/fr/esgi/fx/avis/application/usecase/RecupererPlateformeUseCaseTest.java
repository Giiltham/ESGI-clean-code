package fr.esgi.fx.avis.application.usecase;

import fr.esgi.fx.avis.application.repository.PlateformeRepository;
import fr.esgi.fx.avis.domain.model.Plateforme;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RecupererPlateformeUseCaseTest {

    @Mock
    private PlateformeRepository plateformeRepository;

    @InjectMocks
    private RecupererPlateformeUseCase recupererPlateformeUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void recupererPlateformes_succes() {
        // Arrange
        Plateforme plateforme1 = new Plateforme();
        plateforme1.setNom("PlayStation");

        Plateforme plateforme2 = new Plateforme();
        plateforme2.setNom("Xbox");

        when(plateformeRepository.findAll()).thenReturn(List.of(plateforme1, plateforme2));

        // Act
        List<Plateforme> result = recupererPlateformeUseCase.recupererPlateformes();

        // Assert
        assertEquals(2, result.size());
        assertEquals("PlayStation", result.get(0).getNom());
        assertEquals("Xbox", result.get(1).getNom());
        verify(plateformeRepository, times(1)).findAll();
    }
}
