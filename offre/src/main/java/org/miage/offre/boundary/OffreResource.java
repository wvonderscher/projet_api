package org.miage.offre.boundary;
import java.util.List;
import org.miage.offre.Entity.Offre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OffreResource extends JpaRepository<Offre, String> {

    // JPA va fournir les SELECT, INSERT, UPDATE, DELETE
    //List<Intervenant> findByCodepostal(String codepostal);
    List<Offre> findByDomaine(String domaine);
    List<Offre> findByNomOrganisation(String nomOrganisation);
    List<Offre> findByDateDebutStage(String dateDebutStage);
    List<Offre> findByLieuStageAdresse(String lieuStageAdresse);
    
}