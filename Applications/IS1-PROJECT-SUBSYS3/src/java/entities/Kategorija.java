/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author fili5
 */
@Entity
@Table(name = "kategorija")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kategorija.findAll", query = "SELECT k FROM Kategorija k"),
    @NamedQuery(name = "Kategorija.findByIdk", query = "SELECT k FROM Kategorija k WHERE k.idk = :idk"),
    @NamedQuery(name = "Kategorija.findByNaziv", query = "SELECT k FROM Kategorija k WHERE k.naziv = :naziv")})
public class Kategorija implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idk")
    private Integer idk;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "naziv")
    private String naziv;
    @ManyToMany(mappedBy = "kategorijaList")
    private List<AudioSnimak> audioSnimakList;

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

    @XmlTransient
    public List<AudioSnimak> getAudioSnimakList() {
        return audioSnimakList;
    }

    public void setAudioSnimakList(List<AudioSnimak> audioSnimakList) {
        this.audioSnimakList = audioSnimakList;
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
