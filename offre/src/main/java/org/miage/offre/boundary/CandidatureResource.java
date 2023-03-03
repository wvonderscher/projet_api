package org.miage.offre.boundary;
import java.util.List;
import org.miage.offre.Entity.Candidature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidatureResource extends JpaRepository<Candidature, String> {

    // JPA va fournir les SELECT, INSERT, UPDATE, DELETE
    //List<Intervenant> findByCodepostal(String codepostal);

    List<Candidature> findByIdOffre(String idOffre);
    
}