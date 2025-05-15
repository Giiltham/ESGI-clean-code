package fr.esgi.fx.avis.adapter.repository;

import fr.esgi.fx.avis.adapter.mapper.JeuMapper;
import fr.esgi.fx.avis.application.repository.JeuRepository;
import fr.esgi.fx.avis.domain.model.Jeu;
import fr.esgi.fx.avis.infrastructure.repository.JeuJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
@AllArgsConstructor
public class JeuRepositoryImpl implements JeuRepository {

    private final JeuJpaRepository jeuJpaRepository;
    private final JeuMapper jeuMapper;

    @Override
    public Jeu save(Jeu jeu) {
        return jeuMapper.toDomain(jeuJpaRepository.save(jeuMapper.toEntity(jeu)));
    }

    @Override
    public Page<Jeu> findAll(Pageable pageable) {
        return jeuJpaRepository.findAll(pageable).map(jeuMapper::toDomain);
    }

    @Override
    public Optional<Jeu> findById(Long idJeu) {
        return jeuJpaRepository.findById(idJeu).map(jeuMapper::toDomain);
    }
}
