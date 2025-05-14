package fr.esgi.fx.avis.application.usecase;

import fr.esgi.fx.avis.application.repository.JeuRepository;
import fr.esgi.fx.avis.domain.model.Jeu;
import org.springframework.stereotype.Service;

@Service
public class EnregistrerJeuUseCase {
    private JeuRepository jeuRepository;

    public EnregistrerJeuUseCase(JeuRepository jeuRepository){
        this.jeuRepository = jeuRepository;
    }

    public Jeu enregistrerJeu(Jeu jeu) {
        return jeuRepository.save(jeu);
    }

}
