/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fili5
 */
@Entity
@Table(name = "slusanje")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Slusanje.findAll", query = "SELECT s FROM Slusanje s"),
    @NamedQuery(name = "Slusanje.findByIdKorisnik", query = "SELECT s FROM Slusanje s WHERE s.slusanjePK.idKorisnik = :idKorisnik"),
    @NamedQuery(name = "Slusanje.findByIdAudio", query = "SELECT s FROM Slusanje s WHERE s.slusanjePK.idAudio = :idAudio"),
    @NamedQuery(name = "Slusanje.findByDatumSlusanjaOd", query = "SELECT s FROM Slusanje s WHERE s.datumSlusanjaOd = :datumSlusanjaOd"),
    @NamedQuery(name = "Slusanje.findBySekundaOd", query = "SELECT s FROM Slusanje s WHERE s.sekundaOd = :sekundaOd"),
    @NamedQuery(name = "Slusanje.findBySekundaOdslusano", query = "SELECT s FROM Slusanje s WHERE s.sekundaOdslusano = :sekundaOdslusano")})
public class Slusanje implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SlusanjePK slusanjePK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datum_slusanja_od")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumSlusanjaOd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sekunda_od")
    private int sekundaOd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sekunda_odslusano")
    private int sekundaOdslusano;
    @JoinColumn(name = "id_audio", referencedColumnName = "ida", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private AudioSnimak audioSnimak;
    @JoinColumn(name = "id_korisnik", referencedColumnName = "idk", insertable = false, updatable = false)
    @ManyToOne(optional = false)
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
