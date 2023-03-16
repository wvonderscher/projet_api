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
public class Offre implements Serializable {
    @Serial
    private static final long serialVersionUID = 1837198248L;

    @Id
    private String id;
    private String nomStage;
    private String domaine; // trier
    private String nomOrganisation; //trier
    private String descriptionStage;
    private String datePublicationOffre;
    private String niveauEtudeStage;
    private String experienceRequiseStage;
    private String dateDebutStage; //trier
    private String dureeStage;
    private String salaireStage;
    private String indemnisation;
    private String organisationAdresse;
    private String organisationMail;
    private String organisationTel;
    private String organisationURL;
    private String lieuStageAdresse; //trier
    private String lieuStageTel;
    private String lieuStageURL;
    private boolean vacante;

    
    public Offre(String nomStage, String domaine, String nomOrganisation, String descriptionStage,
            String datePublicationOffre, String niveauEtudeStage, String experienceRequiseStage, String dateDebutStage,
            String dureeStage, String salaireStage, String indemnisation, String organisationAdresse,
            String organisationMail, String organisationTel, String organisationURL, String lieuStageAdresse,
            String lieuStageTel, String lieuStageURL, boolean vacante) {
        this.nomStage = nomStage;
        this.domaine = domaine;
        this.nomOrganisation = nomOrganisation;
        this.descriptionStage = descriptionStage;
        this.datePublicationOffre = datePublicationOffre;
        this.niveauEtudeStage = niveauEtudeStage;
        this.experienceRequiseStage = experienceRequiseStage;
        this.dateDebutStage = dateDebutStage;
        this.dureeStage = dureeStage;
        this.salaireStage = salaireStage;
        this.indemnisation = indemnisation;
        this.organisationAdresse = organisationAdresse;
        this.organisationMail = organisationMail;
        this.organisationTel = organisationTel;
        this.organisationURL = organisationURL;
        this.lieuStageAdresse = lieuStageAdresse;
        this.lieuStageTel = lieuStageTel;
        this.lieuStageURL = lieuStageURL;
        this.vacante = vacante;
    }


    
}
