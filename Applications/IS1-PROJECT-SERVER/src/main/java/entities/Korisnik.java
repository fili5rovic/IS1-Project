package entities;

import java.io.Serializable;
import java.util.Date;


public class Korisnik implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Integer idk;
    private String ime;
    private String email;
    private Date godiste;
    private String pol;
    private Mesto idMesto;

    public Korisnik() {
    }

    public Korisnik(Integer idk) {
        this.idk = idk;
    }

    public Korisnik(Integer idk, String ime, String email, Date godiste, String pol) {
        this.idk = idk;
        this.ime = ime;
        this.email = email;
        this.godiste = godiste;
        this.pol = pol;
    }

    public Integer getIdk() {
        return idk;
    }

    public void setIdk(Integer idk) {
        this.idk = idk;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getGodiste() {
        return godiste;
    }

    public void setGodiste(Date godiste) {
        this.godiste = godiste;
    }

    public String getPol() {
        return pol;
    }

    public void setPol(String pol) {
        this.pol = pol;
    }

    public Mesto getIdMesto() {
        return idMesto;
    }

    public void setIdMesto(Mesto idMesto) {
        this.idMesto = idMesto;
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
        if (!(object instanceof Korisnik)) {
            return false;
        }
        Korisnik other = (Korisnik) object;
        if ((this.idk == null && other.idk != null) || (this.idk != null && !this.idk.equals(other.idk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Korisnik[ idk=" + idk + " ]";
    }
    
}

