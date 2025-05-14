package fr.esgi.fx.avis.application.usecase;

import fr.esgi.fx.avis.application.exception.JeuInexistantException;
import fr.esgi.fx.avis.application.repository.JeuRepository;
import fr.esgi.fx.avis.domain.model.Jeu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RecupererJeuUseCase {
    private JeuRepository jeuRepository;

    public RecupererJeuUseCase(JeuRepository jeuRepository){
        this.jeuRepository = jeuRepository;
    }

    public Page<Jeu> recupererJeux(Pageable pageable) {
        return jeuRepository.findAll(pageable);
    }

    public Jeu recupererJeu(Long idJeu) {
        return jeuRepository.findById(idJeu).orElseThrow(() -> new JeuInexistantException("Ce jeu n'existe pas"));
    }

}
