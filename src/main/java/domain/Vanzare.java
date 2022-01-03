package domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

@Entity
@Component
public class Vanzare{
    private @Id @GeneratedValue Long id;
    Date dataVanzare;
    int nrBileteVandute;
    @ElementCollection
    List<Long> listaLocuriVandute;
    int suma;

    @ManyToOne
    @JsonIgnore
    private Spectacol spectacol;

    @ManyToOne
    @JsonIgnore
    private Sala sala;

    public Vanzare(){}

    public Vanzare(Spectacol spectacol, Date dataVanzare, int nrBileteVandute, List<Long> listaLocuriVandute, int suma) {
        this.spectacol = spectacol;
        sala = spectacol.getSala();
        this.dataVanzare = dataVanzare;
        this.nrBileteVandute = nrBileteVandute;
        this.listaLocuriVandute = listaLocuriVandute;
        this.suma = suma;
    }

    public Date getDataVanzare() {
        return dataVanzare;
    }

    public void setDataVanzare(Date dataVanzare) {
        this.dataVanzare = dataVanzare;
    }

    public int getNrBileteVandute() {
        return nrBileteVandute;
    }

    public void setNrBileteVandute(int nrBileteVandute) {
        this.nrBileteVandute = nrBileteVandute;
    }

    public List<Long> getListaLocuriVandute() {
        return listaLocuriVandute;
    }

    public void setListaLocuriVandute(List<Long> listaLocuriVandute) {
        this.listaLocuriVandute = listaLocuriVandute;
    }

    public int getSuma() {
        return suma;
    }

    public void setSuma(int suma) {
        this.suma = suma;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vanzare vanzare = (Vanzare) o;
        return nrBileteVandute == vanzare.nrBileteVandute &&
                suma == vanzare.suma &&
                id.equals(vanzare.id) &&
                Objects.equals(dataVanzare, vanzare.dataVanzare) &&
                Objects.equals(listaLocuriVandute, vanzare.listaLocuriVandute);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dataVanzare, nrBileteVandute, listaLocuriVandute, suma);
    }

    @Override
    public String toString() {
        return "Vanzare{" +
                "id=" + id +
                ", dataVanzare=" + dataVanzare +
                ", nrBileteVandute=" + nrBileteVandute +
                ", listaLocuriVandute=" + listaLocuriVandute +
                ", suma=" + suma +
                '}';
    }

    public Spectacol getSpectacol() {
        return spectacol;
    }

    public void setSpectacol(Spectacol spectacol) {
        this.spectacol = spectacol;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }
}
