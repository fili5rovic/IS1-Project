/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author fili5
 */
@Entity
@Table(name = "audio_snimak")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AudioSnimak.findAll", query = "SELECT a FROM AudioSnimak a"),
    @NamedQuery(name = "AudioSnimak.findByIda", query = "SELECT a FROM AudioSnimak a WHERE a.ida = :ida"),
    @NamedQuery(name = "AudioSnimak.findByNaziv", query = "SELECT a FROM AudioSnimak a WHERE a.naziv = :naziv"),
    @NamedQuery(name = "AudioSnimak.findByTrajanje", query = "SELECT a FROM AudioSnimak a WHERE a.trajanje = :trajanje"),
    @NamedQuery(name = "AudioSnimak.findByDatum", query = "SELECT a FROM AudioSnimak a WHERE a.datum = :datum")})
public class AudioSnimak implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ida")
    private Integer ida;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "naziv")
    private String naziv;
    @Basic(optional = false)
    @NotNull
    @Column(name = "trajanje")
    private int trajanje;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datum")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datum;
    @JoinTable(name = "audio_kategorija", joinColumns = {
        @JoinColumn(name = "id_audio", referencedColumnName = "ida")}, inverseJoinColumns = {
        @JoinColumn(name = "id_kategorija", referencedColumnName = "idk")})
    @ManyToMany
    private List<Kategorija> kategorijaList;
    @JoinTable(name = "omiljeni", joinColumns = {
        @JoinColumn(name = "id_audio", referencedColumnName = "ida")}, inverseJoinColumns = {
        @JoinColumn(name = "id_korisnika", referencedColumnName = "idk")})
    @ManyToMany
    private List<Korisnik> korisnikList;
    @JoinColumn(name = "vlasnik_idk", referencedColumnName = "idk")
    @ManyToOne(optional = false)
    private Korisnik vlasnikIdk;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "audioSnimak")
    private List<Ocena> ocenaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "audioSnimak")
    private List<Slusanje> slusanjeList;

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

    @XmlTransient
    public List<Kategorija> getKategorijaList() {
        return kategorijaList;
    }

    public void setKategorijaList(List<Kategorija> kategorijaList) {
        this.kategorijaList = kategorijaList;
    }

    @XmlTransient
    public List<Korisnik> getKorisnikList() {
        return korisnikList;
    }

    public void setKorisnikList(List<Korisnik> korisnikList) {
        this.korisnikList = korisnikList;
    }

    public Korisnik getVlasnikIdk() {
        return vlasnikIdk;
    }

    public void setVlasnikIdk(Korisnik vlasnikIdk) {
        this.vlasnikIdk = vlasnikIdk;
    }

    @XmlTransient
    public List<Ocena> getOcenaList() {
        return ocenaList;
    }

    public void setOcenaList(List<Ocena> ocenaList) {
        this.ocenaList = ocenaList;
    }

    @XmlTransient
    public List<Slusanje> getSlusanjeList() {
        return slusanjeList;
    }

    public void setSlusanjeList(List<Slusanje> slusanjeList) {
        this.slusanjeList = slusanjeList;
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
