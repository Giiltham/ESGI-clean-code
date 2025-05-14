package fr.esgi.fx.avis.application.usecase;

import fr.esgi.fx.avis.adapters.dto.EditeurDto;
import fr.esgi.fx.avis.application.exception.EditeurDejaPresentException;
import fr.esgi.fx.avis.application.repository.EditeurRepository;
import fr.esgi.fx.avis.domain.model.Editeur;
import org.springframework.stereotype.Service;

@Service
public class AjouterEditeurUseCase {
    private EditeurRepository editeurRepository;

    public AjouterEditeurUseCase(EditeurRepository editeurRepository) {
        this.editeurRepository = editeurRepository;
    }

    public Editeur ajouterEditeur(Editeur editeur) {
        editeurRepository.findByNomIgnoreCase(editeur.getNom()).ifPresent((editeur1) ->
                new EditeurDejaPresentException(String.format("L'éditeur %s est déjà présent", editeur1.getNom()))
        );
        return editeurRepository.save(editeur);
    }
}
