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
 * Classe qui modélise une candidature à une offre de stage
 */
public class Candidature implements Serializable {
    @Serial
    private static final long serialVersionUID = 1146193271L;
    @Id
    private String id;
    private String idOffre;
    private String idUser;
    private String nomCandidat;
}
