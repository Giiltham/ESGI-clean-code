package fr.esgi.fx.avis.adapter.repository;

import fr.esgi.fx.avis.adapter.mapper.EditeurMapper;
import fr.esgi.fx.avis.application.repository.EditeurRepository;
import fr.esgi.fx.avis.domain.model.Editeur;
import fr.esgi.fx.avis.infrastructure.repository.EditeurJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Repository
public class EditeurRepositoryImpl implements EditeurRepository {

    private final EditeurJpaRepository editeurJpaRepository;
    private final EditeurMapper editeurMapper;

    @Override
    public Optional<Editeur> findById(Long id) {
        return editeurJpaRepository.findById(id).map(editeurMapper::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        editeurJpaRepository.deleteById(id);
    }

    @Override
    public List<Editeur> findAll() {
        return editeurJpaRepository.findAll()
                .stream()
                .map(editeurMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Editeur> findByNomContainingIgnoreCase(String nom) {
        return editeurJpaRepository.findByNomContainingIgnoreCase(nom)
                .stream()
                .map(editeurMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Editeur> findByNomIgnoreCase(String nom) {
        return editeurJpaRepository.findByNomIgnoreCase(nom).map(editeurMapper::toDomain);
    }

    @Override
    public Editeur save(Editeur editeur) {
        return editeurMapper.toDomain(editeurJpaRepository.save(editeurMapper.toEntity(editeur)));
    }
}
