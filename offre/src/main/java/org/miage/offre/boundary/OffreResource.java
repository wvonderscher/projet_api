package org.miage.offre.boundary;

import org.miage.offre.Entity.Offre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OffreResource extends JpaRepository<Offre, String> {

    // JPA va fournir les SELECT, INSERT, UPDATE, DELETE
    //List<Intervenant> findByCodepostal(String codepostal);
    
}