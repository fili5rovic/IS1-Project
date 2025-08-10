/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
import java.util.Date;


public class Ocena implements Serializable {

    private static final long serialVersionUID = 1L;
    protected OcenaPK ocenaPK;

    private int ocena;

    private Date datum;

    private AudioSnimak audioSnimak;
    private Korisnik korisnik;

    public Ocena() {
    }

    public Ocena(OcenaPK ocenaPK) {
        this.ocenaPK = ocenaPK;
    }

    public Ocena(OcenaPK ocenaPK, int ocena, Date datum) {
        this.ocenaPK = ocenaPK;
        this.ocena = ocena;
        this.datum = datum;
    }

    public Ocena(int idKorisnik, int idAudio) {
        this.ocenaPK = new OcenaPK(idKorisnik, idAudio);
    }

    public OcenaPK getOcenaPK() {
        return ocenaPK;
    }

    public void setOcenaPK(OcenaPK ocenaPK) {
        this.ocenaPK = ocenaPK;
    }

    public int getOcena() {
        return ocena;
    }

    public void setOcena(int ocena) {
        this.ocena = ocena;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public AudioSnimak getAudioSnimak() {
        return audioSnimak;
    }

    public void setAudioSnimak(AudioSnimak audioSnimak) {
        this.audioSnimak = audioSnimak;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ocenaPK != null ? ocenaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ocena)) {
            return false;
        }
        Ocena other = (Ocena) object;
        if ((this.ocenaPK == null && other.ocenaPK != null) || (this.ocenaPK != null && !this.ocenaPK.equals(other.ocenaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Ocena[ ocenaPK=" + ocenaPK + " ]";
    }
    
}
