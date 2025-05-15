package fr.esgi.fx.avis.adapter.controller;

import jakarta.servlet.MultipartConfigElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ConfigurationControllerTest {

    @Mock
    private MultipartConfigElement multipartConfigElement;

    @InjectMocks
    private ConfigurationController configurationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getConfiguration_succes() {
        // Arrange
        long maxFileSize = 10485760L; // 10 MB
        when(multipartConfigElement.getMaxFileSize()).thenReturn(maxFileSize);

        // Act
        ModelAndView mav = configurationController.getConfiguration();

        // Assert
        assertEquals("configuration", mav.getViewName());
        assertEquals(maxFileSize, mav.getModel().get("maxFileSize"));
        verify(multipartConfigElement, times(1)).getMaxFileSize();
    }
}
