package ftg.ps.project.ms.offres.encheres.repository;

import ftg.ps.project.ms.offres.encheres.domain.OffreEnchere;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OffreEnchere entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OffreEnchereRepository extends JpaRepository<OffreEnchere, Long> {

}
