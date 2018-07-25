package ftg.ps.project.ms.offres.encheres.repository;

import ftg.ps.project.ms.offres.encheres.domain.AppelOffre;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AppelOffre entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppelOffreRepository extends JpaRepository<AppelOffre, Long> {

}
