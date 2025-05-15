package fr.esgi.fx.avis.adapter.controller;

import fr.esgi.fx.avis.domain.model.Editeur;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class StatistiquesControllerTest {

    @Mock
    private EntityManagerFactory entityManagerFactory;

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Statistics statistics;

    @Mock
    private org.hibernate.Cache cache; // ✅ On ajoute le mock du cache Hibernate

    @InjectMocks
    private StatistiquesController statistiquesController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(entityManagerFactory.unwrap(SessionFactory.class)).thenReturn(sessionFactory);
        when(sessionFactory.getStatistics()).thenReturn(statistics);
        when(sessionFactory.getCache()).thenReturn(cache);

        // Création explicite avec le mock EntityManagerFactory
        statistiquesController = new StatistiquesController(entityManagerFactory);
    }

    @Test
    void getConfiguration_succes() {
        // Arrange
        when(statistics.getQueries()).thenReturn(new String[]{"SELECT * FROM Jeu"});
        when(statistics.getEntityNames()).thenReturn(new String[]{"Jeu", "Editeur"});
        when(cache.containsEntity(Editeur.class, 1L)).thenReturn(true); // ✅ On vérifie que le cache contient l'entité

        // Act
        ModelAndView mav = statistiquesController.getConfiguration();

        // Assert
        assertEquals("statistiques", mav.getViewName());
        verify(statistics, times(1)).getQueries();
        verify(statistics, times(1)).getEntityNames();
        verify(cache, times(1)).containsEntity(Editeur.class, 1L);
    }
}
