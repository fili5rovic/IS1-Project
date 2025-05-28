/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;

public class OcenaPK implements Serializable {


    private int idKorisnik;
    private int idAudio;

    public OcenaPK() {
    }

    public OcenaPK(int idKorisnik, int idAudio) {
        this.idKorisnik = idKorisnik;
        this.idAudio = idAudio;
    }

    public int getIdKorisnik() {
        return idKorisnik;
    }

    public void setIdKorisnik(int idKorisnik) {
        this.idKorisnik = idKorisnik;
    }

    public int getIdAudio() {
        return idAudio;
    }

    public void setIdAudio(int idAudio) {
        this.idAudio = idAudio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idKorisnik;
        hash += (int) idAudio;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OcenaPK)) {
            return false;
        }
        OcenaPK other = (OcenaPK) object;
        if (this.idKorisnik != other.idKorisnik) {
            return false;
        }
        if (this.idAudio != other.idAudio) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.OcenaPK[ idKorisnik=" + idKorisnik + ", idAudio=" + idAudio + " ]";
    }
    
}
