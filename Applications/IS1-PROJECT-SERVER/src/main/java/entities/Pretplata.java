/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Pretplata implements Serializable {

    private static final long serialVersionUID = 1L;
  
    private Integer idp;
  
    private Date datumOd;
  
    private BigDecimal cena;
 
    private Korisnik idk;
 
    private Paket idPaket;

    public Pretplata() {
    }

    public Pretplata(Integer idp) {
        this.idp = idp;
    }

    public Pretplata(Integer idp, Date datumOd, BigDecimal cena) {
        this.idp = idp;
        this.datumOd = datumOd;
        this.cena = cena;
    }

    public Integer getIdp() {
        return idp;
    }

    public void setIdp(Integer idp) {
        this.idp = idp;
    }

    public Date getDatumOd() {
        return datumOd;
    }

    public void setDatumOd(Date datumOd) {
        this.datumOd = datumOd;
    }

    public BigDecimal getCena() {
        return cena;
    }

    public void setCena(BigDecimal cena) {
        this.cena = cena;
    }

    public Korisnik getIdk() {
        return idk;
    }

    public void setIdk(Korisnik idk) {
        this.idk = idk;
    }

    public Paket getIdPaket() {
        return idPaket;
    }

    public void setIdPaket(Paket idPaket) {
        this.idPaket = idPaket;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idp != null ? idp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pretplata)) {
            return false;
        }
        Pretplata other = (Pretplata) object;
        if ((this.idp == null && other.idp != null) || (this.idp != null && !this.idp.equals(other.idp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Pretplata[ idp=" + idp + " ]";
    }
    
}
