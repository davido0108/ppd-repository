package com.example.demo;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class PpdProiectClient {

    public static void sendSpectacol(Long id, List<Integer> locuri, int sum)  throws IOException {
        HttpURLConnection con = null;
        String url = "http://localhost:8080/vanzare/add";
        String locuriString = locuri.stream().map(String::valueOf)
                .collect(Collectors.joining(","));
        String urlParameters = String.format("sid=%d&num=%d&locuri=%s&suma=%d",id, locuri.size(), locuriString , sum);
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        try {

            URL myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();;
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Java client");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.connect();
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
            assert con != null;
            con.disconnect();
        }
        //System.out.println("done");
    }

    public static void sendSpectacol1(List<Integer> locuri)  throws IOException{
        sendSpectacol(2L, locuri, locuri.size() * 100);
    }

    public static void sendSpectacol2(List<Integer> locuri)  throws IOException{
        sendSpectacol(3L, locuri, locuri.size() * 200);
    }

    public static void sendSpectacol3(List<Integer> locuri)  throws IOException{
        sendSpectacol(4L, locuri, locuri.size() * 150);
    }

    public static void main(String[] args) throws IOException {

        Timer timer = new Timer();

        timer.schedule(new ClientWorker(), 0, 2000);
    }

    private static class ClientWorker extends TimerTask {

        @Override
        public void run() {
            List<Integer> elems = new ArrayList<>();
            for(int j=0;j<10;++j){
                for(int i=0; i<10; ++i) {
                    elems.add(i + 10 * j);

                }
                try {
                    sendSpectacol1(elems);
                    sendSpectacol2(elems);
                    sendSpectacol3(elems);

                    Thread.sleep(2000);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
                elems.clear();
            }
    }
    }

}
