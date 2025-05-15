package fr.esgi.fx.avis.application.usecase;

import fr.esgi.fx.avis.application.exception.EditeurInexistantException;
import fr.esgi.fx.avis.application.repository.EditeurRepository;
import org.springframework.stereotype.Service;

@Service
public class SupprimerEditeurUseCase {
    private EditeurRepository editeurRepository;

    public SupprimerEditeurUseCase(EditeurRepository editeurRepository) {
        this.editeurRepository = editeurRepository;
    }

    public void supprimerEditeur(Long id) {
        editeurRepository.findById(id).orElseThrow(() -> new EditeurInexistantException("Cet éditeur n'existe pas"));
        editeurRepository.deleteById(id);
    }
}
