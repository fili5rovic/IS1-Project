/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
import java.util.Date;

public class Slusanje implements Serializable {

    private static final long serialVersionUID = 1L;
    protected SlusanjePK slusanjePK;

    private Date datumSlusanjaOd;

    private int sekundaOd;

    private int sekundaOdslusano;
    private AudioSnimak audioSnimak;
    private Korisnik korisnik;

    public Slusanje() {
    }

    public Slusanje(SlusanjePK slusanjePK) {
        this.slusanjePK = slusanjePK;
    }

    public Slusanje(SlusanjePK slusanjePK, Date datumSlusanjaOd, int sekundaOd, int sekundaOdslusano) {
        this.slusanjePK = slusanjePK;
        this.datumSlusanjaOd = datumSlusanjaOd;
        this.sekundaOd = sekundaOd;
        this.sekundaOdslusano = sekundaOdslusano;
    }

    public Slusanje(int idKorisnik, int idAudio) {
        this.slusanjePK = new SlusanjePK(idKorisnik, idAudio);
    }

    public SlusanjePK getSlusanjePK() {
        return slusanjePK;
    }

    public void setSlusanjePK(SlusanjePK slusanjePK) {
        this.slusanjePK = slusanjePK;
    }

    public Date getDatumSlusanjaOd() {
        return datumSlusanjaOd;
    }

    public void setDatumSlusanjaOd(Date datumSlusanjaOd) {
        this.datumSlusanjaOd = datumSlusanjaOd;
    }

    public int getSekundaOd() {
        return sekundaOd;
    }

    public void setSekundaOd(int sekundaOd) {
        this.sekundaOd = sekundaOd;
    }

    public int getSekundaOdslusano() {
        return sekundaOdslusano;
    }

    public void setSekundaOdslusano(int sekundaOdslusano) {
        this.sekundaOdslusano = sekundaOdslusano;
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
        hash += (slusanjePK != null ? slusanjePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Slusanje)) {
            return false;
        }
        Slusanje other = (Slusanje) object;
        if ((this.slusanjePK == null && other.slusanjePK != null) || (this.slusanjePK != null && !this.slusanjePK.equals(other.slusanjePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Slusanje[ slusanjePK=" + slusanjePK + " ]";
    }
    
}
