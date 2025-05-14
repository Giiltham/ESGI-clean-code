package fr.esgi.fx.avis.application.usecase;

import fr.esgi.fx.avis.application.repository.PlateformeRepository;
import fr.esgi.fx.avis.domain.model.Plateforme;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecupererPlateformeUseCase {

    private PlateformeRepository plateformeRepository;

    public RecupererPlateformeUseCase(PlateformeRepository plateformeRepository) {
        this.plateformeRepository = plateformeRepository;
    }

    public List<Plateforme> recupererPlateformes() {
        return plateformeRepository.findAll();
    }
}
