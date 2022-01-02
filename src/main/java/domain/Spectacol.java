package domain;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.transaction.annotation.Transactional;


import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.persistence.*;


@Entity
@Component
public class Spectacol{

    private @Id @GeneratedValue Long id;
    private Date dataSpectacol;
    private String titlu;
    private int pretBilet;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Long> listaLocuriVandute;
    private int sold;

    @ManyToOne
    @JsonIgnore
    private Sala sala;

    @OneToMany
    private Set<Vanzare> vanzari;

    public Spectacol(){
    }
    public Spectacol(Sala sala, Date dataSpectacol, String titlu, int pretBilet, List<Long> numarLocuriVandute, int sold) {
        this.sala = sala;
        this.dataSpectacol = dataSpectacol;
        this.titlu = titlu;
        this.pretBilet = pretBilet;
        this.listaLocuriVandute = numarLocuriVandute;
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

    public List<Long> getListaLocuriVandute() {
        return listaLocuriVandute;
    }

    public void setListaLocuriVandute(List<Long> listaLocuriVandute) {
        this.listaLocuriVandute = listaLocuriVandute;
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
                ", numarLocuriVandute=" + listaLocuriVandute +
                ", sold=" + sold +
                '}';
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public Set<Vanzare> getVanzari() {
        return vanzari;
    }

    public void setVanzari(Set<Vanzare> vanzari) {
        this.vanzari = vanzari;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
