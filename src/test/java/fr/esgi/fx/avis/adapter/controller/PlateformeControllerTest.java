package fr.esgi.fx.avis.adapter.controller;

import fr.esgi.fx.avis.application.usecase.RecupererPlateformeUseCase;
import fr.esgi.fx.avis.domain.model.Plateforme;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PlateformeControllerTest {

    @Mock
    private RecupererPlateformeUseCase plateformeUseCase;

    @InjectMocks
    private PlateformeController plateformeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getPlateformes_succes() {
        // Arrange
        Plateforme plateforme = new Plateforme();
        plateforme.setNom("PlayStation");

        when(plateformeUseCase.recupererPlateformes()).thenReturn(List.of(plateforme));

        // Act
        ModelAndView mav = plateformeController.getPlateformes();

        // Assert
        assertEquals("plateformes", mav.getViewName());
        assertEquals(1, ((List<?>) mav.getModel().get("plateformes")).size());
        verify(plateformeUseCase, times(1)).recupererPlateformes();
    }
}
