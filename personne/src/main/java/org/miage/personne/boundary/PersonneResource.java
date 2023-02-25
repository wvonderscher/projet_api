package org.miage.personne.boundary;
//import java.util.List;
import org.miage.personne.Entity.Personne;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonneResource extends JpaRepository<Personne, String> {

    Personne findByNomUser(String nomUser);
}