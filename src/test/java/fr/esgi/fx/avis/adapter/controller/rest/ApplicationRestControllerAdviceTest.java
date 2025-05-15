package fr.esgi.fx.avis.adapter.controller.rest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ApplicationRestControllerAdviceTest {

    private final ApplicationRestControllerAdvice advice = new ApplicationRestControllerAdvice();

    @Test
    void traiterConstraintViolationException_succes() {
        // Arrange
        ConstraintViolation<String> violation = mock(ConstraintViolation.class);
        when(violation.getMessage()).thenReturn("Champ requis manquant");
        Set<ConstraintViolation<String>> violations = Set.of(violation);

        ConstraintViolationException exception = new ConstraintViolationException(violations);

        // Act
        List<String> result = advice.traiterConstraintViolationException(exception);

        // Assert
        assertEquals(1, result.size());
        assertEquals("Champ requis manquant", result.get(0));
    }

    @Test
    void traiterMaxUploadSizeExceededException_succes() {
        // Act
        ResponseEntity<String> response = advice.traiterMaxUploadSizeExceededException();

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("La taille du fichier dépasse la limite autorisée", response.getBody());
    }
}
