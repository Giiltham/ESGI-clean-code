package fr.esgi.fx.avis.adapters.controller;

import fr.esgi.fx.avis.application.usecase.RecupererPlateformeUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
public class PlateformeController {

    private final RecupererPlateformeUseCase plateformeUseCase;
    @GetMapping({ "plateformes" })
    public ModelAndView getPlateformes() {
        ModelAndView mav = new ModelAndView("plateformes");

        mav.addObject("plateformes", plateformeUseCase.recupererPlateformes());

        return mav;
    }
}
