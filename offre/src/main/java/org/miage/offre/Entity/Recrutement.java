package org.miage.offre.Entity;
import java.io.Serial;
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
/**
 * Classe qui modélise un processus de recurtement sur une candidature d'une personne.
 */
public class Recrutement implements Serializable {
    @Serial
    private static final long serialVersionUID = 4103191379L;
    @Id
    private String id;
    private String idCandidature;
    private String nombreEntretien;
    private String decision;



    public Recrutement(String decision){
        this.decision = decision;
    }
}
