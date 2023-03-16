package org.miage.offre.boundary;
import java.util.List;
import org.miage.offre.Entity.Candidature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidatureResource extends JpaRepository<Candidature, String> {
    List<Candidature> findByIdOffre(String idOffre);
    List<Candidature> findByNomCandidat(String nomCandidat);
    Candidature findByNomCandidatAndIdOffre(String nomCandidat, String idOffre);
    Boolean existsByNomCandidatAndIdOffre(String nomCandidat, String idOffre);
}