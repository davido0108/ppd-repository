package domain;

import org.springframework.stereotype.Component;


import javax.persistence.*;
import java.util.Set;

@Entity
@Component
public class Sala {
    private @Id @GeneratedValue Long id;
    int nrLocuri;
    @OneToMany(mappedBy = "sala")
    Set<Spectacol> listaSpectacole;

    @OneToMany(mappedBy = "sala")
    Set<Vanzare> listaVanzari;

    public Sala(){}

    public Sala(int nrLocuri, Set<Spectacol> listaSpectacole, Set<Vanzare> listaVanzari) {
        this.nrLocuri = nrLocuri;
        this.listaSpectacole = listaSpectacole;
        this.listaVanzari = listaVanzari;
    }

    public int getNrLocuri() {
        return nrLocuri;
    }

    public void setNrLocuri(int nrLocuri) {
        this.nrLocuri = nrLocuri;
    }

    public Set<Spectacol> getListaSpectacole() {
        return listaSpectacole;
    }

    public void setListaSpectacole(Set<Spectacol> listaSpectacole) {
        this.listaSpectacole = listaSpectacole;
    }

    public Set<Vanzare> getListaVanzari() {
        return listaVanzari;
    }

    public void setListaVanzari(Set<Vanzare> listaVanzari) {
        this.listaVanzari = listaVanzari;
    }

    @Override
    public String toString() {
        return "Sala{" +
                "nrLocuri=" + nrLocuri +
                ", listaSpectacole=" + listaSpectacole +
                ", listaVanzari=" + listaVanzari +
                '}';
    }
}
