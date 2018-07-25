package ftg.ps.project.ms.offres.encheres.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Offre.
 */
@Entity
@Table(name = "offre")
public class Offre implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "n_unique_offre", nullable = false)
    private Long nUniqueOffre;

    @Column(name = "date_lancement")
    private LocalDate dateLancement;

    @Column(name = "date_cloture_offre")
    private LocalDate dateClotureOffre;

    @Column(name = "quantite_produit")
    private Long quantiteProduit;

    @Column(name = "n_auteur_offre")
    private Long nAuteurOffre;

    @Column(name = "type_auteur_offre")
    private String typeAuteurOffre;

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

    public Long getnUniqueOffre() {
        return nUniqueOffre;
    }

    public Offre nUniqueOffre(Long nUniqueOffre) {
        this.nUniqueOffre = nUniqueOffre;
        return this;
    }

    public void setnUniqueOffre(Long nUniqueOffre) {
        this.nUniqueOffre = nUniqueOffre;
    }

    public LocalDate getDateLancement() {
        return dateLancement;
    }

    public Offre dateLancement(LocalDate dateLancement) {
        this.dateLancement = dateLancement;
        return this;
    }

    public void setDateLancement(LocalDate dateLancement) {
        this.dateLancement = dateLancement;
    }

    public LocalDate getDateClotureOffre() {
        return dateClotureOffre;
    }

    public Offre dateClotureOffre(LocalDate dateClotureOffre) {
        this.dateClotureOffre = dateClotureOffre;
        return this;
    }

    public void setDateClotureOffre(LocalDate dateClotureOffre) {
        this.dateClotureOffre = dateClotureOffre;
    }

    public Long getQuantiteProduit() {
        return quantiteProduit;
    }

    public Offre quantiteProduit(Long quantiteProduit) {
        this.quantiteProduit = quantiteProduit;
        return this;
    }

    public void setQuantiteProduit(Long quantiteProduit) {
        this.quantiteProduit = quantiteProduit;
    }

    public Long getnAuteurOffre() {
        return nAuteurOffre;
    }

    public Offre nAuteurOffre(Long nAuteurOffre) {
        this.nAuteurOffre = nAuteurOffre;
        return this;
    }

    public void setnAuteurOffre(Long nAuteurOffre) {
        this.nAuteurOffre = nAuteurOffre;
    }

    public String getTypeAuteurOffre() {
        return typeAuteurOffre;
    }

    public Offre typeAuteurOffre(String typeAuteurOffre) {
        this.typeAuteurOffre = typeAuteurOffre;
        return this;
    }

    public void setTypeAuteurOffre(String typeAuteurOffre) {
        this.typeAuteurOffre = typeAuteurOffre;
    }

    public Long getActivityID() {
        return activityID;
    }

    public Offre activityID(Long activityID) {
        this.activityID = activityID;
        return this;
    }

    public void setActivityID(Long activityID) {
        this.activityID = activityID;
    }

    public Long getnArticles() {
        return nArticles;
    }

    public Offre nArticles(Long nArticles) {
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
        Offre offre = (Offre) o;
        if (offre.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), offre.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Offre{" +
            "id=" + getId() +
            ", nUniqueOffre=" + getnUniqueOffre() +
            ", dateLancement='" + getDateLancement() + "'" +
            ", dateClotureOffre='" + getDateClotureOffre() + "'" +
            ", quantiteProduit=" + getQuantiteProduit() +
            ", nAuteurOffre=" + getnAuteurOffre() +
            ", typeAuteurOffre='" + getTypeAuteurOffre() + "'" +
            ", activityID=" + getActivityID() +
            ", nArticles=" + getnArticles() +
            "}";
    }
}
