package ru.keni0k.game.tanks.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.keni0k.game.tanks.services.WorldService;
import ru.keni0k.game.tanks.utils.PATH;

@Controller
public class MainController {

    private WorldService worldService;

    @Autowired
    public MainController(WorldService tankService){
        worldService = tankService;
    }

    @RequestMapping("/")
    public String getUI(ModelMap modelMap){
        modelMap.addAttribute("worlds", worldService.findAll());
        return "index";
    }

    @GetMapping(PATH.CLEAR_ALL)
    public RedirectView clearAll(){
        worldService.deleteAll();
        return new RedirectView("/");
    }

}
