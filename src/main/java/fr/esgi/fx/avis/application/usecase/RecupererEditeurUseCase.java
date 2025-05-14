package fr.esgi.fx.avis.application.usecase;

import fr.esgi.fx.avis.application.exception.EditeurInexistantException;
import fr.esgi.fx.avis.application.repository.EditeurRepository;
import fr.esgi.fx.avis.domain.model.Editeur;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecupererEditeurUseCase {
    private EditeurRepository editeurRepository;

    public RecupererEditeurUseCase(EditeurRepository editeurRepository) {
        this.editeurRepository = editeurRepository;
    }

    public List<Editeur> recupererEditeurs() {
        return editeurRepository.findAll();
    }

    public List<Editeur> recupererEditeursParNomContenant(String nom) {
        return editeurRepository.findByNomContainingIgnoreCase(nom);
    }


    public Editeur recupererEditeur(Long id) {
        return editeurRepository.findById(id).orElseThrow(() -> new EditeurInexistantException("Cet éditeur n'existe pas"));
    }

}
