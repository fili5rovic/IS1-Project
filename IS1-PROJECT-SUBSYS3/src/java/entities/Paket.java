/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author fili5
 */
@Entity
@Table(name = "paket")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Paket.findAll", query = "SELECT p FROM Paket p"),
    @NamedQuery(name = "Paket.findByIdp", query = "SELECT p FROM Paket p WHERE p.idp = :idp"),
    @NamedQuery(name = "Paket.findByCena", query = "SELECT p FROM Paket p WHERE p.cena = :cena")})
public class Paket implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idp")
    private Integer idp;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "cena")
    private BigDecimal cena;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPaket")
    private List<Pretplata> pretplataList;

    public Paket() {
    }

    public Paket(Integer idp) {
        this.idp = idp;
    }

    public Paket(Integer idp, BigDecimal cena) {
        this.idp = idp;
        this.cena = cena;
    }

    public Integer getIdp() {
        return idp;
    }

    public void setIdp(Integer idp) {
        this.idp = idp;
    }

    public BigDecimal getCena() {
        return cena;
    }

    public void setCena(BigDecimal cena) {
        this.cena = cena;
    }

    @XmlTransient
    public List<Pretplata> getPretplataList() {
        return pretplataList;
    }

    public void setPretplataList(List<Pretplata> pretplataList) {
        this.pretplataList = pretplataList;
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
        if (!(object instanceof Paket)) {
            return false;
        }
        Paket other = (Paket) object;
        if ((this.idp == null && other.idp != null) || (this.idp != null && !this.idp.equals(other.idp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Paket[ idp=" + idp + " ]";
    }
    
}
