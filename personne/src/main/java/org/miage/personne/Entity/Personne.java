package org.miage.personne.Entity;
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
 * Classe qui modélise une personne qui peut candidater sur une offre de stage.
 */
public class Personne implements Serializable {
    @Serial
    private static final long serialVersionUID = 1134148298L;
    @Id
    private String id;
    private String nomUser;
    private String telUser;

    public Personne(String nomUser, String telUser) {
        this.nomUser = nomUser;
        this.telUser = telUser;
    }


}
