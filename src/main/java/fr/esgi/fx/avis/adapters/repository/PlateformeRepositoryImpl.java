package fr.esgi.fx.avis.adapters.repository;

import fr.esgi.fx.avis.adapters.mapper.PlateformeMapper;
import fr.esgi.fx.avis.application.repository.PlateformeRepository;
import fr.esgi.fx.avis.domain.model.Plateforme;
import fr.esgi.fx.avis.infrastructure.repository.PlateformeJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Repository
public class PlateformeRepositoryImpl implements PlateformeRepository {
    private final PlateformeJpaRepository plateformeJpaRepository;
    private final PlateformeMapper plateformeMapper;

    @Override
    public List<Plateforme> findAll() {
        return plateformeJpaRepository.findAll()
                .stream()
                .map(plateformeMapper::toDomain)
                .collect(Collectors.toList());
    }
}
