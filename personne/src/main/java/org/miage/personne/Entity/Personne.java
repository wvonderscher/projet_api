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
@NoArgsConstructor // obligatoire si JPA
@Data
public class Personne implements Serializable {
    @Serial
    private static final long serialVersionUID = 1134148298L;
    @Id
    private String id;
    private String nomUser;
    private String telUser;


}
