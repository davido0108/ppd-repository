package domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Sala {

    private @Id @GeneratedValue Long id;
    int nrLocuri;
    @OneToMany
    List<Spectacol> listaSpectacole;
    @OneToMany
    List<Vanzare> listaVanzari;

    public Sala(){}

    public Sala(int nrLocuri, List<Spectacol> listaSpectacole, List<Vanzare> listaVanzari) {
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

    public List<Spectacol> getListaSpectacole() {
        return listaSpectacole;
    }

    public void setListaSpectacole(List<Spectacol> listaSpectacole) {
        this.listaSpectacole = listaSpectacole;
    }

    public List<Vanzare> getListaVanzari() {
        return listaVanzari;
    }

    public void setListaVanzari(List<Vanzare> listaVanzari) {
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
