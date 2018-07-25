package ftg.ps.project.ms.offres.encheres.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Contrat.
 */
@Entity
@Table(name = "contrat")
public class Contrat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "n_contrat")
    private Long nContrat;

    @Column(name = "date_signature_contrat")
    private LocalDate dateSignatureContrat;

    @Column(name = "n_fournisseur_ext")
    private Long nFournisseurExt;

    @OneToOne
    @JoinColumn(unique = true)
    private Offre contratOffre;

    @OneToOne
    @JoinColumn(unique = true)
    private Enchere contratEnchere;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getnContrat() {
        return nContrat;
    }

    public Contrat nContrat(Long nContrat) {
        this.nContrat = nContrat;
        return this;
    }

    public void setnContrat(Long nContrat) {
        this.nContrat = nContrat;
    }

    public LocalDate getDateSignatureContrat() {
        return dateSignatureContrat;
    }

    public Contrat dateSignatureContrat(LocalDate dateSignatureContrat) {
        this.dateSignatureContrat = dateSignatureContrat;
        return this;
    }

    public void setDateSignatureContrat(LocalDate dateSignatureContrat) {
        this.dateSignatureContrat = dateSignatureContrat;
    }

    public Long getnFournisseurExt() {
        return nFournisseurExt;
    }

    public Contrat nFournisseurExt(Long nFournisseurExt) {
        this.nFournisseurExt = nFournisseurExt;
        return this;
    }

    public void setnFournisseurExt(Long nFournisseurExt) {
        this.nFournisseurExt = nFournisseurExt;
    }

    public Offre getContratOffre() {
        return contratOffre;
    }

    public Contrat contratOffre(Offre offre) {
        this.contratOffre = offre;
        return this;
    }

    public void setContratOffre(Offre offre) {
        this.contratOffre = offre;
    }

    public Enchere getContratEnchere() {
        return contratEnchere;
    }

    public Contrat contratEnchere(Enchere enchere) {
        this.contratEnchere = enchere;
        return this;
    }

    public void setContratEnchere(Enchere enchere) {
        this.contratEnchere = enchere;
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
        Contrat contrat = (Contrat) o;
        if (contrat.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contrat.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Contrat{" +
            "id=" + getId() +
            ", nContrat=" + getnContrat() +
            ", dateSignatureContrat='" + getDateSignatureContrat() + "'" +
            ", nFournisseurExt=" + getnFournisseurExt() +
            "}";
    }
}
