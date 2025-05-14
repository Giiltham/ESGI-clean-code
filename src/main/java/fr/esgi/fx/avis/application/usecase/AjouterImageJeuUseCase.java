package fr.esgi.fx.avis.application.usecase;

import fr.esgi.fx.avis.application.repository.JeuRepository;
import fr.esgi.fx.avis.domain.model.Jeu;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class AjouterImageJeuUseCase {
    private JeuRepository jeuRepository;

    public AjouterImageJeuUseCase(JeuRepository jeuRepository){
        this.jeuRepository = jeuRepository;
    }

    public boolean ajouterImage(Long id, MultipartFile image) {
        Path chemin = Paths.get("src/main/resources/static/images/");
        Jeu jeu = jeuRepository.findById(id).orElse(null);

        if (jeu!=null) {
            try (InputStream inputStream = image.getInputStream()) {
                Path cheminFichier = chemin.resolve(id + ".jpg");
                Files.copy(inputStream, cheminFichier, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ioe) {
                return false;
            }
            jeu.setImage(id + ".jpg");
            jeuRepository.save(jeu);
            return true;
        }
        else {
            return false;
        }
    }
}
