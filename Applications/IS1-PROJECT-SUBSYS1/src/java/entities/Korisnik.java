/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fili5
 */
@Entity
@Table(name = "korisnik")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Korisnik.findAll", query = "SELECT k FROM Korisnik k"),
    @NamedQuery(name = "Korisnik.findByIdk", query = "SELECT k FROM Korisnik k WHERE k.idk = :idk"),
    @NamedQuery(name = "Korisnik.findByIme", query = "SELECT k FROM Korisnik k WHERE k.ime = :ime"),
    @NamedQuery(name = "Korisnik.findByEmail", query = "SELECT k FROM Korisnik k WHERE k.email = :email"),
    @NamedQuery(name = "Korisnik.findByGodiste", query = "SELECT k FROM Korisnik k WHERE k.godiste = :godiste"),
    @NamedQuery(name = "Korisnik.findByPol", query = "SELECT k FROM Korisnik k WHERE k.pol = :pol")})
public class Korisnik implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idk")
    private Integer idk;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "ime")
    private String ime;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Column(name = "godiste")
    @Temporal(TemporalType.DATE)
    private Date godiste;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 9)
    @Column(name = "pol")
    private String pol;
    @JoinColumn(name = "id_mesto", referencedColumnName = "idm")
    @ManyToOne(optional = false)
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
