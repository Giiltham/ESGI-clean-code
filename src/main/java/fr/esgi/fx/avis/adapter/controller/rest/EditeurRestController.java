package fr.esgi.fx.avis.adapter.controller.rest;

import fr.esgi.fx.avis.adapter.mapper.EditeurMapper;
import fr.esgi.fx.avis.application.usecase.AjouterEditeurUseCase;
import fr.esgi.fx.avis.application.usecase.RecupererEditeurUseCase;
import fr.esgi.fx.avis.application.usecase.SupprimerEditeurUseCase;
import fr.esgi.fx.avis.adapter.dto.EditeurDto;
import fr.esgi.fx.avis.application.exception.EditeurDejaPresentException;
import fr.esgi.fx.avis.application.exception.EditeurInexistantException;
import fr.esgi.fx.avis.adapter.util.ReponseApi;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/editeurs")
@AllArgsConstructor
@Validated
public class EditeurRestController {

    private final RecupererEditeurUseCase recupererEditeurUseCase;
    private final AjouterEditeurUseCase ajouterEditeurUseCase;
    private final SupprimerEditeurUseCase supprimerEditeurUseCase;
    private final EditeurMapper editeurMapper;

    @GetMapping("")
    public List<EditeurDto> getEditeurs() {
        return recupererEditeurUseCase.recupererEditeurs()
                .stream()
                .map(editeurMapper::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping("")
    @ResponseStatus(code= HttpStatus.CREATED)
    public EditeurDto postEditeur(@Valid @RequestBody EditeurDto editeurDto, BindingResult bindingResult) {
        return editeurMapper.toDto(ajouterEditeurUseCase.ajouterEditeur(editeurMapper.toDomain(editeurDto)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEditeur(@PathVariable Long id) {
        supprimerEditeurUseCase.supprimerEditeur(id);
    }

    @ExceptionHandler(EditeurDejaPresentException.class)
    @ResponseStatus(code=HttpStatus.CONFLICT)
    public ReponseApi<String> traiterEditeurDejaPresentException(EditeurDejaPresentException e) {
        return new ReponseApi<String>("409", e.getMessage(), null);

    }

    @ExceptionHandler(EditeurInexistantException.class)
    @ResponseStatus(code=HttpStatus.NOT_FOUND)
    public ReponseApi<String> traiterEditeurInexistantException(EditeurInexistantException e) {
        return new ReponseApi<>("404", e.getMessage(), null);
    }

}
