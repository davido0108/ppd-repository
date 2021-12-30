package domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Entity
public class Spectacol implements Serializable {

    private @Id @GeneratedValue Long id;
    Date dataSpectacol;
    String titlu;
    int pretBilet;
    @ElementCollection
    List<Long> numarLocuriVandute;
    int sold;

    public Spectacol(){
    }
    public Spectacol(Date dataSpectacol, String titlu, int pretBilet, List<Long> numarLocuriVandute, int sold) {
        this.dataSpectacol = dataSpectacol;
        this.titlu = titlu;
        this.pretBilet = pretBilet;
        this.numarLocuriVandute = numarLocuriVandute;
        this.sold = sold;
    }

    public Date getDataSpectacol() {
        return dataSpectacol;
    }

    public void setDataSpectacol(Date dataSpectacol) {
        this.dataSpectacol = dataSpectacol;
    }

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public int getPretBilet() {
        return pretBilet;
    }

    public void setPretBilet(int pretBilet) {
        this.pretBilet = pretBilet;
    }

    public List<Long> getNumarLocuriVandute() {
        return numarLocuriVandute;
    }

    public void setNumarLocuriVandute(List<Long> numarLocuriVandute) {
        this.numarLocuriVandute = numarLocuriVandute;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    @Override
    public String toString() {
        return "Spectacol{" +
                "dataSpectacol=" + dataSpectacol +
                ", titlu='" + titlu + '\'' +
                ", pretBilet=" + pretBilet +
                ", numarLocuriVandute=" + numarLocuriVandute +
                ", sold=" + sold +
                '}';
    }
}
