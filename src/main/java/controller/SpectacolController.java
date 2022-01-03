package controller;

import domain.Sala;
import domain.Spectacol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import repository.SalaRepository;
import repository.SpectacolRepository;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.*;

@RestController
@CrossOrigin
@Transactional
@RequestMapping(path="/spectacol")
public class SpectacolController {



    @Autowired
    private SpectacolRepository spectacolRepository;


    @Autowired
    private SalaRepository salaRepository;

    @GetMapping(path="/hw")
    public @ResponseBody String hw(){

        return "Hello World";
    }

    @GetMapping(path="/all")
    public @ResponseBody
    List<Spectacol> getAll(){
        return spectacolRepository.findAll();
    }

    @PostMapping(path="/add")
    public @ResponseBody String addSpectacol(@RequestParam(value = "sid", required = true) Long salaId ,
                                             @RequestParam(value = "data", required = false) Date data ,
                                             @RequestParam(value = "titlu", required = true) String titlu,
                                             @RequestParam(value = "pret", required = true) int pret,
                                             @RequestParam(value = "sold", required = true) int sold
                                             ){

        Optional<Sala> sal = salaRepository.findById(salaId);
        if(!sal.isPresent())
            return "Sala invalida";


        if(data == null)
            data = new Date();
        Spectacol s = new Spectacol(sal.get(),data,titlu,pret,new ArrayList<>(),sold);
        spectacolRepository.save(s);
        return String.format("Added %s", s);
    }

}
