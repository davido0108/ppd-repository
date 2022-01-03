package service;


import domain.Spectacol;
import domain.Vanzare;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import repository.SpectacolRepository;
import repository.VanzareRepository;
import sun.net.www.http.HttpClient;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.security.RunAs;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class VanzareService {
    private static HttpURLConnection con;

    private ReentrantLock lock;

    //(spectacol_id, lock)
    private Map<Long, ReentrantLock> lockDictionary;

    private ThreadPoolExecutor threadPoolExecutor;

    @PostConstruct
    private void init() throws IOException {
        lock = new ReentrantLock();
        lockDictionary = new HashMap<>();
        threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);

        Timer timer = new Timer();
        timer.schedule(new CheckWorker(), 0 , 5000);

    }

    @PreDestroy
    private void dest() throws IOException {
        //join la worker

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
        System.out.println(spectacol.getVanzari());
        System.out.println(spectacol);
        int sold = spectacol.getSold();
        int vanzare_sold = 0;
        List<Long> vanzare_locuri = new ArrayList<>();
        for( Vanzare v : vanzari){
            vanzare_sold += v.getSuma();
            vanzare_locuri.addAll(v.getListaLocuriVandute());
        }

        if(sold != vanzare_sold  || !vanzare_locuri.containsAll(spectacol.getListaLocuriVandute())) {
            //TODO: Erorare
            System.out.println("EROARE");
        }
        else
            //TODO: Corect
            System.out.println("CORECT");

        lockDictionary.get(spectacol.getId()).unlock();

    }

    //Task verificare
    private class CheckWorker extends TimerTask implements Runnable {
        //TODO: implement
        @Override
        public void run() {
            List<Spectacol> spectacole = spectacolRepository.findAll();
            for(Spectacol s:spectacole){
                checkSpectacol(s);
            }

            //Checks every 5 secs every spectacol
        }
    }

   /* private class Client {
        @Override
        public void run(){

        }
    }*/


    public void client() throws IOException {
        String url = "http://localhost:8080/vanzare/add";
        String urlParameters = "sid=3&titlu=Hamlet&pret=200&sold=100";
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);

        try {

            URL myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();

            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Java client");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {

                wr.write(postData);
            }

            StringBuilder content;

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {

                String line;
                content = new StringBuilder();

                while ((line = br.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }

            System.out.println(content.toString());

        } finally {

            con.disconnect();
        }
        System.out.println("done");
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
                if(s.get().getListaLocuriVandute().contains(loc)){
                    lockDictionary.get(spectacolId).unlock();
                    return "Incorect, Locurile sunt deja ocupate";
                }
            }

            Vanzare v = new Vanzare(spectacol,data,nrLocuri,locuri, sum);

            vanzareRepository.save(v);

            //Update spectacol
            spectacol.setSold(spectacol.getSold() + sum);
            spectacol.getListaLocuriVandute().addAll(locuri);
            spectacolRepository.saveAndFlush(spectacol);

            lockDictionary.get(spectacolId).unlock();
            return "Corect";
        }
    }



}
