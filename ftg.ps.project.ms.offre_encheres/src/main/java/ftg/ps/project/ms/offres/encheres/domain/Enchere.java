package ftg.ps.project.ms.offres.encheres.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Enchere.
 */
@Entity
@Table(name = "enchere")
public class Enchere implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "n_unique_enchere", nullable = false)
    private Long nUniqueEnchere;

    @Column(name = "date_lancement_enchere")
    private LocalDate dateLancementEnchere;

    @Column(name = "date_cloture_enchere")
    private LocalDate dateClotureEnchere;

    @Column(name = "quantite_produit")
    private Long quantiteProduit;

    @Column(name = "n_auteur_enchere")
    private Long nAuteurEnchere;

    @Column(name = "type_auteur_enchere")
    private String typeAuteurEnchere;

    @Column(name = "prix_depart", precision = 10, scale = 2)
    private BigDecimal prixDepart;

    @Column(name = "prix_courant", precision = 10, scale = 2)
    private BigDecimal prixCourant;

    @Column(name = "prix_de_vente", precision = 10, scale = 2)
    private BigDecimal prixDeVente;

    @Column(name = "activity_id")
    private Long activityID;

    @Column(name = "n_articles")
    private Long nArticles;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getnUniqueEnchere() {
        return nUniqueEnchere;
    }

    public Enchere nUniqueEnchere(Long nUniqueEnchere) {
        this.nUniqueEnchere = nUniqueEnchere;
        return this;
    }

    public void setnUniqueEnchere(Long nUniqueEnchere) {
        this.nUniqueEnchere = nUniqueEnchere;
    }

    public LocalDate getDateLancementEnchere() {
        return dateLancementEnchere;
    }

    public Enchere dateLancementEnchere(LocalDate dateLancementEnchere) {
        this.dateLancementEnchere = dateLancementEnchere;
        return this;
    }

    public void setDateLancementEnchere(LocalDate dateLancementEnchere) {
        this.dateLancementEnchere = dateLancementEnchere;
    }

    public LocalDate getDateClotureEnchere() {
        return dateClotureEnchere;
    }

    public Enchere dateClotureEnchere(LocalDate dateClotureEnchere) {
        this.dateClotureEnchere = dateClotureEnchere;
        return this;
    }

    public void setDateClotureEnchere(LocalDate dateClotureEnchere) {
        this.dateClotureEnchere = dateClotureEnchere;
    }

    public Long getQuantiteProduit() {
        return quantiteProduit;
    }

    public Enchere quantiteProduit(Long quantiteProduit) {
        this.quantiteProduit = quantiteProduit;
        return this;
    }

    public void setQuantiteProduit(Long quantiteProduit) {
        this.quantiteProduit = quantiteProduit;
    }

    public Long getnAuteurEnchere() {
        return nAuteurEnchere;
    }

    public Enchere nAuteurEnchere(Long nAuteurEnchere) {
        this.nAuteurEnchere = nAuteurEnchere;
        return this;
    }

    public void setnAuteurEnchere(Long nAuteurEnchere) {
        this.nAuteurEnchere = nAuteurEnchere;
    }

    public String getTypeAuteurEnchere() {
        return typeAuteurEnchere;
    }

    public Enchere typeAuteurEnchere(String typeAuteurEnchere) {
        this.typeAuteurEnchere = typeAuteurEnchere;
        return this;
    }

    public void setTypeAuteurEnchere(String typeAuteurEnchere) {
        this.typeAuteurEnchere = typeAuteurEnchere;
    }

    public BigDecimal getPrixDepart() {
        return prixDepart;
    }

    public Enchere prixDepart(BigDecimal prixDepart) {
        this.prixDepart = prixDepart;
        return this;
    }

    public void setPrixDepart(BigDecimal prixDepart) {
        this.prixDepart = prixDepart;
    }

    public BigDecimal getPrixCourant() {
        return prixCourant;
    }

    public Enchere prixCourant(BigDecimal prixCourant) {
        this.prixCourant = prixCourant;
        return this;
    }

    public void setPrixCourant(BigDecimal prixCourant) {
        this.prixCourant = prixCourant;
    }

    public BigDecimal getPrixDeVente() {
        return prixDeVente;
    }

    public Enchere prixDeVente(BigDecimal prixDeVente) {
        this.prixDeVente = prixDeVente;
        return this;
    }

    public void setPrixDeVente(BigDecimal prixDeVente) {
        this.prixDeVente = prixDeVente;
    }

    public Long getActivityID() {
        return activityID;
    }

    public Enchere activityID(Long activityID) {
        this.activityID = activityID;
        return this;
    }

    public void setActivityID(Long activityID) {
        this.activityID = activityID;
    }

    public Long getnArticles() {
        return nArticles;
    }

    public Enchere nArticles(Long nArticles) {
        this.nArticles = nArticles;
        return this;
    }

    public void setnArticles(Long nArticles) {
        this.nArticles = nArticles;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Enchere enchere = (Enchere) o;
        if (enchere.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), enchere.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Enchere{" +
            "id=" + getId() +
            ", nUniqueEnchere=" + getnUniqueEnchere() +
            ", dateLancementEnchere='" + getDateLancementEnchere() + "'" +
            ", dateClotureEnchere='" + getDateClotureEnchere() + "'" +
            ", quantiteProduit=" + getQuantiteProduit() +
            ", nAuteurEnchere=" + getnAuteurEnchere() +
            ", typeAuteurEnchere='" + getTypeAuteurEnchere() + "'" +
            ", prixDepart=" + getPrixDepart() +
            ", prixCourant=" + getPrixCourant() +
            ", prixDeVente=" + getPrixDeVente() +
            ", activityID=" + getActivityID() +
            ", nArticles=" + getnArticles() +
            "}";
    }
}
