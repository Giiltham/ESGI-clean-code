package fr.esgi.fx.avis.adapter.controller.rest;

import fr.esgi.fx.avis.adapter.mapper.JeuMapper;
import fr.esgi.fx.avis.application.usecase.AjouterImageJeuUseCase;
import fr.esgi.fx.avis.application.usecase.AjouterJeuUseCase;
import fr.esgi.fx.avis.application.usecase.RecupererJeuUseCase;
import fr.esgi.fx.avis.adapter.dto.JeuDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/jeux")
@AllArgsConstructor
@Validated
public class JeuRestController {

    private final RecupererJeuUseCase recupererJeuUseCase;
    private final AjouterJeuUseCase ajouterJeuUseCase;
    private final AjouterImageJeuUseCase ajouterImageJeuUseCase;
    private final JeuMapper jeuMapper;

    @GetMapping("")
    public Page<JeuDto> getJeux(@PageableDefault(size = 10, sort = "nom", direction = Sort.Direction.DESC) Pageable pageable) {
        return recupererJeuUseCase.recupererJeux(pageable).map(jeuMapper::toDto);
    }

    @PostMapping
    @ResponseStatus(code= HttpStatus.CREATED)
    public JeuDto postJeu(@Valid @RequestBody JeuDto jeuDto, BindingResult bindingResult) {
        return jeuMapper.toDto(ajouterJeuUseCase.ajouterJeu(jeuMapper.toDomain(jeuDto)));
    }

    @PostMapping("/{idJeu}/image")
    public boolean postImage(@PathVariable Long idJeu, @RequestParam("file") MultipartFile image) {
      return ajouterImageJeuUseCase.ajouterImage(idJeu, image);
    }
}
