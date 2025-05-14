package fr.esgi.fx.avis.application.usecase;

import fr.esgi.fx.avis.application.repository.JeuRepository;
import fr.esgi.fx.avis.domain.model.Jeu;
import org.springframework.stereotype.Service;

@Service
public class AjouterJeuUseCase {
    private JeuRepository jeuRepository;

    public AjouterJeuUseCase(JeuRepository jeuRepository){
        this.jeuRepository = jeuRepository;
    }

    public Jeu ajouterJeu(Jeu jeu) {
        return jeuRepository.save(jeu);
    }

}
