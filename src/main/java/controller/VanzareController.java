package controller;

import domain.Vanzare;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import repository.VanzareRepository;
import service.VanzareService;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin
@RequestMapping(path="/vanzare")
public class VanzareController {
    @Autowired
    private VanzareRepository vanzareRepository;

    @Autowired
    private VanzareService vanzareService;

    @GetMapping(path="/hw")
    public @ResponseBody String hw(){

        return "Hello World";
    }

    @GetMapping(path="/all")
    public @ResponseBody
    List<Vanzare> getAll(){
        return vanzareRepository.findAll();
    }

    @PostMapping(path="/add")
    public @ResponseBody String addVanzare( @RequestParam(value = "sid", required = true) Long sid ,
                                             @RequestParam(value = "data", required = false) Date data ,
                                             @RequestParam(value = "num", required = true) int num,
                                             @RequestParam(value = "locuri", required = true) List<Long> listaLocuriVandute,
                                             @RequestParam(value = "suma", required = true) int sum
                                             ){
//        Vanzare vanzare = new Vanzare(data,num,listaLocuriVandute,sum);
//        vanzareRepository.save(vanzare);
        if(data == null)
            data = new Date();

        try {
            return vanzareService.addVanzare(sid,data,num,listaLocuriVandute,sum).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return "Eroare";
    }

}
