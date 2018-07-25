package ftg.ps.project.ms.offres.encheres.repository;

import ftg.ps.project.ms.offres.encheres.domain.Enchere;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Enchere entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EnchereRepository extends JpaRepository<Enchere, Long> {

}
