package org.miage.offre.boundary;
import org.miage.offre.Entity.Recrutement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecrutementResource extends JpaRepository<Recrutement, String> {

    Recrutement findByIdCandidature(String idCandidature);
    
}