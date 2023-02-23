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
@NoArgsConstructor // obligatoire si JPA
@Data
public class Offre implements Serializable {
    @Serial
    private static final long serialVersionUID = 1837198248L;
    @Id
    private String id;
    private String nomStage;
    private String domaine;
    private String nomOrganisation;
    private String descriptionStage;
    // private String datePublicationOffre;
    // private String niveauEtudeStage;
    // private String experienceRequiseStage;
    // private String dateDebutStage;
    // private String dureeStage;
    // private String salaireStage;
    // private String indemnisation;
    // private String organisationAdresse;
    // private String organisationMail;
    // private String organisationTel;
    // private String organisationURL;
    // private String lieuStageAdresse;
    // private String lieuStageTel;
    // private String lieuStageURL;



}
