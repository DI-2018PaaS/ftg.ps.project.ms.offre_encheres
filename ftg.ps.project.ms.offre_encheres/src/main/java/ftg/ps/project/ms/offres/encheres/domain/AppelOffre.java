package ftg.ps.project.ms.offres.encheres.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A AppelOffre.
 */
@Entity
@Table(name = "appel_offre")
public class AppelOffre implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "n_inscription", nullable = false)
    private Long nInscription;

    @Column(name = "date_reponse_offre")
    private LocalDate dateReponseOffre;

    @Column(name = "n_unique_offre")
    private Long nUniqueOffre;

    @Column(name = "n_fournisseur_ext")
    private Long nFournisseurExt;

    @OneToOne
    @JoinColumn(unique = true)
    private Offre appelOffre;

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

    public AppelOffre nInscription(Long nInscription) {
        this.nInscription = nInscription;
        return this;
    }

    public void setnInscription(Long nInscription) {
        this.nInscription = nInscription;
    }

    public LocalDate getDateReponseOffre() {
        return dateReponseOffre;
    }

    public AppelOffre dateReponseOffre(LocalDate dateReponseOffre) {
        this.dateReponseOffre = dateReponseOffre;
        return this;
    }

    public void setDateReponseOffre(LocalDate dateReponseOffre) {
        this.dateReponseOffre = dateReponseOffre;
    }

    public Long getnUniqueOffre() {
        return nUniqueOffre;
    }

    public AppelOffre nUniqueOffre(Long nUniqueOffre) {
        this.nUniqueOffre = nUniqueOffre;
        return this;
    }

    public void setnUniqueOffre(Long nUniqueOffre) {
        this.nUniqueOffre = nUniqueOffre;
    }

    public Long getnFournisseurExt() {
        return nFournisseurExt;
    }

    public AppelOffre nFournisseurExt(Long nFournisseurExt) {
        this.nFournisseurExt = nFournisseurExt;
        return this;
    }

    public void setnFournisseurExt(Long nFournisseurExt) {
        this.nFournisseurExt = nFournisseurExt;
    }

    public Offre getAppelOffre() {
        return appelOffre;
    }

    public AppelOffre appelOffre(Offre offre) {
        this.appelOffre = offre;
        return this;
    }

    public void setAppelOffre(Offre offre) {
        this.appelOffre = offre;
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
        AppelOffre appelOffre = (AppelOffre) o;
        if (appelOffre.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), appelOffre.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AppelOffre{" +
            "id=" + getId() +
            ", nInscription=" + getnInscription() +
            ", dateReponseOffre='" + getDateReponseOffre() + "'" +
            ", nUniqueOffre=" + getnUniqueOffre() +
            ", nFournisseurExt=" + getnFournisseurExt() +
            "}";
    }
}
