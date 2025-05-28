/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;


public class Kategorija implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idk;

    private String naziv;


    public Kategorija() {
    }

    public Kategorija(Integer idk) {
        this.idk = idk;
    }

    public Kategorija(Integer idk, String naziv) {
        this.idk = idk;
        this.naziv = naziv;
    }

    public Integer getIdk() {
        return idk;
    }

    public void setIdk(Integer idk) {
        this.idk = idk;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idk != null ? idk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Kategorija)) {
            return false;
        }
        Kategorija other = (Kategorija) object;
        if ((this.idk == null && other.idk != null) || (this.idk != null && !this.idk.equals(other.idk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Kategorija[ idk=" + idk + " ]";
    }
    
}
