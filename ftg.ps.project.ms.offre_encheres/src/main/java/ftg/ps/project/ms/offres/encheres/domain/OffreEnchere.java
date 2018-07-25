package ftg.ps.project.ms.offres.encheres.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A OffreEnchere.
 */
@Entity
@Table(name = "offre_enchere")
public class OffreEnchere implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "n_inscription", nullable = false)
    private Long nInscription;

    @Column(name = "n_unique_enchere")
    private Long nUniqueEnchere;

    @Column(name = "date_reponse_enchere")
    private LocalDate dateReponseEnchere;

    @Column(name = "n_acteur_ext")
    private Long nActeurExt;

    @OneToOne
    @JoinColumn(unique = true)
    private Enchere offreEnchere;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getnInscription() {
        return nInscription;
    }

    public OffreEnchere nInscription(Long nInscription) {
        this.nInscription = nInscription;
        return this;
    }

    public void setnInscription(Long nInscription) {
        this.nInscription = nInscription;
    }

    public Long getnUniqueEnchere() {
        return nUniqueEnchere;
    }

    public OffreEnchere nUniqueEnchere(Long nUniqueEnchere) {
        this.nUniqueEnchere = nUniqueEnchere;
        return this;
    }

    public void setnUniqueEnchere(Long nUniqueEnchere) {
        this.nUniqueEnchere = nUniqueEnchere;
    }

    public LocalDate getDateReponseEnchere() {
        return dateReponseEnchere;
    }

    public OffreEnchere dateReponseEnchere(LocalDate dateReponseEnchere) {
        this.dateReponseEnchere = dateReponseEnchere;
        return this;
    }

    public void setDateReponseEnchere(LocalDate dateReponseEnchere) {
        this.dateReponseEnchere = dateReponseEnchere;
    }

    public Long getnActeurExt() {
        return nActeurExt;
    }

    public OffreEnchere nActeurExt(Long nActeurExt) {
        this.nActeurExt = nActeurExt;
        return this;
    }

    public void setnActeurExt(Long nActeurExt) {
        this.nActeurExt = nActeurExt;
    }

    public Enchere getOffreEnchere() {
        return offreEnchere;
    }

    public OffreEnchere offreEnchere(Enchere enchere) {
        this.offreEnchere = enchere;
        return this;
    }

    public void setOffreEnchere(Enchere enchere) {
        this.offreEnchere = enchere;
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
        OffreEnchere offreEnchere = (OffreEnchere) o;
        if (offreEnchere.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), offreEnchere.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OffreEnchere{" +
            "id=" + getId() +
            ", nInscription=" + getnInscription() +
            ", nUniqueEnchere=" + getnUniqueEnchere() +
            ", dateReponseEnchere='" + getDateReponseEnchere() + "'" +
            ", nActeurExt=" + getnActeurExt() +
            "}";
    }
}
