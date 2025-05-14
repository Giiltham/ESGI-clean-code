package fr.esgi.fx.avis.adapters.controller;

import fr.esgi.fx.avis.application.usecase.AjouterEditeurUseCase;
import fr.esgi.fx.avis.application.usecase.RecupererEditeurUseCase;
import fr.esgi.fx.avis.domain.model.Editeur;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
public class EditeurController {

    private final RecupererEditeurUseCase recupererEditeurUseCase;
    private final AjouterEditeurUseCase ajouterEditeurUseCase;

    @GetMapping({ "editeurs", "index", "/" })
    public ModelAndView getEditeurs() {
        ModelAndView mav = new ModelAndView("editeurs");

        mav.addObject("editeurs", recupererEditeurUseCase.recupererEditeurs());

        return mav;
    }

    @PostMapping("editeurs")
    public ModelAndView postEditeurs(@RequestParam("NOM") String nom) {

        ModelAndView mav = new ModelAndView("editeurs");
        mav.addObject("editeurs", recupererEditeurUseCase.recupererEditeursParNomContenant(nom));
        mav.addObject("nom", nom);

        return mav;
    }

    @GetMapping("editeur")
    public ModelAndView getEditeur(@ModelAttribute Editeur editeur) {
        ModelAndView mav = new ModelAndView("editeur");

        return mav;
    }

    @PostMapping("editeur")
    public ModelAndView postEditeur(@Valid @ModelAttribute Editeur editeur, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return getEditeur(editeur);
        }
        ajouterEditeurUseCase.ajouterEditeur(editeur);
        return new ModelAndView("redirect:/editeurs");
    }
}
