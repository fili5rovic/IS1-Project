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
@Table(name = "ocena")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ocena.findAll", query = "SELECT o FROM Ocena o"),
    @NamedQuery(name = "Ocena.findByIdKorisnik", query = "SELECT o FROM Ocena o WHERE o.ocenaPK.idKorisnik = :idKorisnik"),
    @NamedQuery(name = "Ocena.findByIdAudio", query = "SELECT o FROM Ocena o WHERE o.ocenaPK.idAudio = :idAudio"),
    @NamedQuery(name = "Ocena.findByOcena", query = "SELECT o FROM Ocena o WHERE o.ocena = :ocena"),
    @NamedQuery(name = "Ocena.findByDatum", query = "SELECT o FROM Ocena o WHERE o.datum = :datum")})
public class Ocena implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected OcenaPK ocenaPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ocena")
    private int ocena;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datum")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datum;
    @JoinColumn(name = "id_audio", referencedColumnName = "ida", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private AudioSnimak audioSnimak;
    @JoinColumn(name = "id_korisnik", referencedColumnName = "idk", insertable = false, updatable = false)
    @ManyToOne(optional = false)
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
