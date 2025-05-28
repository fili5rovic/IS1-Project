/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
import java.util.Date;


public class AudioSnimak implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer ida;

    private String naziv;

    private int trajanje;

    private Date datum;
    private Korisnik vlasnikIdk;


    public AudioSnimak() {
    }

    public AudioSnimak(Integer ida) {
        this.ida = ida;
    }

    public AudioSnimak(Integer ida, String naziv, int trajanje, Date datum) {
        this.ida = ida;
        this.naziv = naziv;
        this.trajanje = trajanje;
        this.datum = datum;
    }

    public Integer getIda() {
        return ida;
    }

    public void setIda(Integer ida) {
        this.ida = ida;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getTrajanje() {
        return trajanje;
    }

    public void setTrajanje(int trajanje) {
        this.trajanje = trajanje;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public Korisnik getVlasnikIdk() {
        return vlasnikIdk;
    }

    public void setVlasnikIdk(Korisnik vlasnikIdk) {
        this.vlasnikIdk = vlasnikIdk;
    }



    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ida != null ? ida.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AudioSnimak)) {
            return false;
        }
        AudioSnimak other = (AudioSnimak) object;
        if ((this.ida == null && other.ida != null) || (this.ida != null && !this.ida.equals(other.ida))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.AudioSnimak[ ida=" + ida + " ]";
    }
    
}
