package controller;

import domain.Sala;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import repository.SalaRepository;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path="/sala")
public class SalaController {
    @Autowired
    private SalaRepository salaRepository;

    @GetMapping(path="/hw")
    public @ResponseBody String hw(){

        return "Hello World";
    }

    @GetMapping(path="/all")
    public @ResponseBody
    List<Sala> getAll(){
        return salaRepository.findAll();
    }

    @PostMapping(path="/add")
    public @ResponseBody String addSala(@RequestParam(value = "nrLocuri", required = true) int num){
        Sala sala = new Sala();
        sala.setNrLocuri(num);
        salaRepository.save(sala);
        return String.format("Added %d", num);
    }

}
