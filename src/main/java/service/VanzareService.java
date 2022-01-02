package service;


import domain.Spectacol;
import domain.Vanzare;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import repository.SpectacolRepository;
import repository.VanzareRepository;

import javax.annotation.PostConstruct;
import javax.annotation.security.RunAs;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class VanzareService {

    private ReentrantLock lock;

    //(spectacol_id, lock)
    private Map<Long, ReentrantLock> lockDictionary;

    private ThreadPoolExecutor threadPoolExecutor;

    @PostConstruct
    private void init(){
        lock = new ReentrantLock();
        lockDictionary = new HashMap<>();
        threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
    }

    @Autowired
    private SpectacolRepository spectacolRepository;

    @Autowired
    private VanzareRepository vanzareRepository;


//    public String tryAddVanzare(Long spectacolId, Date date, int num, List<Long> locuriVandute, int sum){
//
//        Optional<Spectacol> spectacol = spectacolRepository.findById(spectacolId);
//        if (!spectacol.isPresent())
//            return "Incorect, Spectacolul nu a fost gasit";
//
//        //verifica locurile vandute
//        for (Long loc:locuriVandute) {
//            if(spectacol.get().getListaLocuriVandute().contains(loc))
//                return "Incorect, Locurile sunt deja ocupate";
//        }
//
//        //save vanzare
//        Vanzare v = new Vanzare(spectacol.get(),date,num,locuriVandute,sum);
//        vanzareRepository.save(v);
//
//        //update spectacol
//        spectacol.get().getListaLocuriVandute().addAll(locuriVandute);
//        spectacol.get().setSold(spectacol.get().getSold() + sum);
//
//        return "Corect";
//
//    }

    private void checkSpectacol(Spectacol spectacol){
        lock.lock();

        if(!lockDictionary.containsKey(spectacol.getId()))
            lockDictionary.put(spectacol.getId(), new ReentrantLock());

        lockDictionary.get(spectacol.getId()).lock();
        lock.unlock();

        List<Vanzare> vanzari =  new ArrayList<>(spectacol.getVanzari());
        int sold = spectacol.getSold();
        int vanzare_sold = 0;
        List<Long> vanzare_locuri = new ArrayList<>();
        for( Vanzare v : vanzari){
            vanzare_sold += v.getSuma();
            vanzare_locuri.addAll(v.getListaLocuriVandute());
        }

        if(sold != vanzare_sold || vanzare_locuri.containsAll(spectacol.getListaLocuriVandute())) {
            //TODO: Erorare
            System.out.println("EROARE");
        }

        //TODO: Corect
        System.out.println("CORECT");
    }

    //Task verificare
    private class CheckWorker implements Runnable{
        //TODO: implement
        @Override
        public void run() {
            List<Spectacol> spectacole = spectacolRepository.findAll();
            for(Spectacol s:spectacole){
                checkSpectacol(s);
            }
            //Checks every 2 secs every spectacol
        }
    }

    @Async
    public Future<String> addVanzare(Long spectacolId, Date date, int numSeats, List<Long> seats, int sum) throws ExecutionException, InterruptedException {
        return threadPoolExecutor.submit(new VanzareWorker(spectacolId,date,numSeats,seats,sum));

    }

    //Task vanzare
    private class VanzareWorker implements Callable<String>{

        private final Long spectacolId;
        private final Date data;
        private final int nrLocuri;
        private final List<Long> locuri;
        private final int sum;

        public VanzareWorker(Long spectacolId, Date data, int nrLocuri, List<Long> locuri, int sum) {
            this.spectacolId = spectacolId;
            this.data = data;
            this.nrLocuri = nrLocuri;
            this.locuri = locuri;
            this.sum = sum;
        }


        @Override
        public String call() throws Exception {

            Optional<Spectacol> s = spectacolRepository.findById(spectacolId);

            if (!s.isPresent()) {
                return "Incorect, Spectacolul nu a fost gasit";
            }

            Spectacol spectacol = spectacolRepository.getById(spectacolId);
            lock.lock();

            if(!lockDictionary.containsKey(spectacolId))
                lockDictionary.put(spectacolId, new ReentrantLock());

            lockDictionary.get(spectacolId).lock();

            lock.unlock();


            for (Long loc:locuri) {
                if(spectacol.getListaLocuriVandute().contains(loc)){
                    lockDictionary.get(spectacolId).unlock();
                    return "Incorect, Locurile sunt deja ocupate";
                }
            }

            Vanzare v = new Vanzare(spectacol,data,nrLocuri,locuri, sum);

            vanzareRepository.save(v);

            spectacol.setSold(spectacol.getSold() + sum);
            spectacol.getListaLocuriVandute().addAll(locuri);


            lockDictionary.get(spectacolId).unlock();
            return "Corect";
        }
    }



}
