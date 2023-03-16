package org.miage.offre.boundary;
import java.net.URI;
import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;
import org.miage.offre.control.OffreAssembler;
import org.miage.offre.control.RecrutementAssembler;
import org.miage.offre.control.CandidatureAssembler;
import org.miage.offre.Entity.Offre;
import org.miage.offre.Entity.Recrutement;
import org.miage.offre.Entity.Candidature;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Controller
@ResponseBody
@RequestMapping(value = "/offres", produces = MediaType.APPLICATION_JSON_VALUE)
public class OffreRepresentation {

    private final OffreResource or;
    private final OffreAssembler oa;
    private final CandidatureResource cr;
    private final CandidatureAssembler ca;
    private final RecrutementAssembler ra;
    private final RecrutementResource rr;
    
    public OffreRepresentation(OffreResource or, OffreAssembler oa, CandidatureResource cr, CandidatureAssembler ca, RecrutementAssembler ra, RecrutementResource rr) {
        this.or = or;
        this.oa = oa;
        this.cr = cr;
        this.ca = ca;
        this.ra = ra;
        this.rr = rr;
    }

    // GET all offres
    @GetMapping
    public ResponseEntity<?> getAllOffres() {
        return ResponseEntity.ok(oa.toCollectionModel(or.findAll()));
    }


    // GET one offre
    @GetMapping(value = "/{offreId}")
    public ResponseEntity<?> getOffreById(@PathVariable("offreId") String id) {
        return Optional.of(or.findById(id))
                .filter(Optional::isPresent)
                .map(i -> ResponseEntity.ok(oa.toModel(i.get())))
                .orElse(ResponseEntity.notFound().build());
    }

    // GET all offres by Domaine 
    @GetMapping(value = "/domaine/{offreDomaine}")
    public ResponseEntity<?> getOffreByDomaine(@PathVariable("offreDomaine") String domaine) {
        return ResponseEntity.ok(oa.toCollectionModel(or.findByDomaine(domaine)));
    }   
    
    // GET all offres by organisation
    @GetMapping(value = "/organisation/{offreOrganisation}")
    public ResponseEntity<?> getOffreByOrganisation(@PathVariable("offreOrganisation") String organisation) {
        return ResponseEntity.ok(oa.toCollectionModel(or.findByNomOrganisation(organisation)));
    }
    
    // GET all offres by DateDebutStage
    @GetMapping(value = "/dateDebut/{offreDateDebutStage}")
    public ResponseEntity<?> getOffreByDateDebut(@PathVariable("offreDateDebutStage") String dateDebutStage) {
        return ResponseEntity.ok(oa.toCollectionModel(or.findByDateDebutStage(dateDebutStage)));
    }

     // GET all offres organisation
    @GetMapping(value = "/lieu/{offreLieuStageAdresse}")
    public ResponseEntity<?> getOffreByLieuStageAdresse(@PathVariable("offreLieuStageAdresse") String lieuStageAdresse) {
         return ResponseEntity.ok(oa.toCollectionModel(or.findByLieuStageAdresse(lieuStageAdresse)));
     }
    

    // POST offre
    @PostMapping
    @Transactional
    public ResponseEntity<?> save(@RequestBody Offre offre) {
        Offre toSave = new Offre(UUID.randomUUID().toString(),
        offre.getNomStage(),
        offre.getDomaine(),
        offre.getNomOrganisation(),
        offre.getDescriptionStage(),
        offre.getDatePublicationOffre(),
        offre.getNiveauEtudeStage(),
        offre.getExperienceRequiseStage(),
        offre.getDateDebutStage(),
        offre.getDureeStage(),
        offre.getSalaireStage(),
        offre.getIndemnisation(),
        offre.getOrganisationAdresse(),
        offre.getOrganisationMail(),
        offre.getOrganisationTel(),
        offre.getOrganisationURL(),
        offre.getLieuStageAdresse(),
        offre.getLieuStageTel(),
        offre.getLieuStageURL(),
        true);

        Offre saved = or.save(toSave);
        URI location = linkTo(OffreRepresentation.class).slash(saved.getId()).toUri();
        return ResponseEntity.created(location).build();
    }


        // POST candidature
        @PostMapping("/{offreId}")
        @Transactional
        public ResponseEntity<?> saveCandidature(@RequestBody Candidature candidature, @PathVariable("offreId") String id) {
            if (!or.existsById(id)) {
                return ResponseEntity.notFound().build();
            }
            Offre offre = or.findById(id).get();
            // On vérifie si l'offre est vacante et que le candidat n'a pas deja candidate sur l'offre
            if((!offre.isVacante()) || (cr.findByNomCandidatAndIdOffre(candidature.getNomCandidat(), id)!=null)){
                return ResponseEntity.badRequest().build();
            }
           Candidature toSave = new Candidature(UUID.randomUUID().toString(),
           id,
           candidature.getIdUser(),
           candidature.getNomCandidat()
           );
    Candidature saved = cr.save(toSave);
    URI location = linkTo(OffreRepresentation.class).slash(saved.getId()).toUri();
    return ResponseEntity.created(location).build();
        }


    // GET all Candidatures
    @GetMapping(value = "/{offreId}/users")
    public ResponseEntity<?> getCandidature(@PathVariable("offreId") String offreId) {
        return ResponseEntity.ok(ca.toCollectionModel(cr.findByIdOffre(offreId)));
    }

     // GET all candidatures for a user
     @GetMapping("/{nomCandidat}/candidatures")
     public ResponseEntity<?> getCandidaturesByUser(@PathVariable("nomCandidat") String nomCandidat) {
         return ResponseEntity.ok(ca.toCollectionModel(cr.findByNomCandidat(nomCandidat)));
     }

        //GET one candidature for a user
        @GetMapping("/{nomCandidat}/candidatures/{offreId}")
        public ResponseEntity<?> getCandidatureByUser(@PathVariable("nomCandidat") String nomCandidat,@PathVariable("offreId") String offreId ) {
         return ResponseEntity.ok(ca.toModel(cr.findByNomCandidatAndIdOffre(nomCandidat, offreId)));
     }

    // DELETE offre
    @DeleteMapping(value = "/{offreId}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable("offreId") String id) {
        Optional<Offre> offre = or.findById(id);
        offre.ifPresent(or::delete);
        return ResponseEntity.noContent().build();
    }


    // DELETE candidature
    @DeleteMapping(value = "/{nomCandidat}/candidatures/{offreId}/delete")
    @Transactional
    public ResponseEntity<?> deleteCandidature(@PathVariable("nomCandidat") String nomCandidat,@PathVariable("offreId") String offreId) {
        Candidature candidature = cr.findByNomCandidatAndIdOffre(nomCandidat, offreId);
        if(candidature != null){
            cr.delete(candidature);
        }
        return ResponseEntity.noContent().build();
    }


        // PATCH recrutement acceptation
        @PatchMapping(value = "/recrutement/{recrutementId}/accepter")
        @Transactional
        public ResponseEntity<?> updateRecrutementAccepter(@PathVariable("recrutementId") String id) {
            if (!rr.existsById(id)) {
                return ResponseEntity.notFound().build();
            }
            Recrutement recru = rr.findById(id).get();
            recru.setDecision("accepté");
            return ResponseEntity.ok().build();
        }


                // PATCH offre fermer candidature
                @PatchMapping(value = "/offre/{offreId}/fermer")
                @Transactional
                public ResponseEntity<?> updateOffreFermer(@PathVariable("offreId") String id) {
                    if (!or.existsById(id)) {
                        return ResponseEntity.notFound().build();
                    }
                    Offre offre = or.findById(id).get();
                    offre.setVacante(false);
                    return ResponseEntity.ok().build();
                }

    // PUT offre
    @PutMapping(value = "/{offreId}")
    @Transactional
    public ResponseEntity<?> update(@PathVariable("offreId") String id,
            @RequestBody Offre newOffre) {
        if (!or.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Offre toSave = new Offre(
                newOffre.getNomStage(),
                newOffre.getDomaine(),
                newOffre.getNomOrganisation(),
                newOffre.getDescriptionStage(),
                newOffre.getDatePublicationOffre(),
                newOffre.getNiveauEtudeStage(),
                newOffre.getExperienceRequiseStage(),
                newOffre.getDateDebutStage(),
                newOffre.getDureeStage(),
                newOffre.getSalaireStage(),
                newOffre.getIndemnisation(),
                newOffre.getOrganisationAdresse(),
                newOffre.getOrganisationMail(),
                newOffre.getOrganisationTel(),
                newOffre.getOrganisationURL(),
                newOffre.getLieuStageAdresse(),
                newOffre.getLieuStageTel(),
                newOffre.getLieuStageURL(),
                true);
        toSave.setId(id);
        or.save(toSave);
        return ResponseEntity.ok().build();
    }

        // GET all recrutement
        @GetMapping("/recrutements")
        public ResponseEntity<?> getAllRecrutements() {
            return ResponseEntity.ok(ra.toCollectionModel(rr.findAll()));
        }


            // GET one recrutement
    @GetMapping(value = "/recrutements/{recrutementId}")
    public ResponseEntity<?> getRecrutementById(@PathVariable("recrutementId") String recrutementId) {
        return Optional.of(rr.findById(recrutementId))
                .filter(Optional::isPresent)
                .map(i -> ResponseEntity.ok(ra.toModel(i.get())))
                .orElse(ResponseEntity.notFound().build());
    }

            // POST Recrutement
            @PostMapping("/candidatures/{candidatureId}")
            @Transactional
            public ResponseEntity<?> saveRecrutement(@RequestBody Recrutement recrutement, @PathVariable("candidatureId") String id) {
                if (!cr.existsById(id)) {
                    return ResponseEntity.notFound().build();
                }
                Recrutement toSave = new Recrutement(UUID.randomUUID().toString(),
               id,
               recrutement.getNombreEntretien(),
               "aucune");
        Recrutement saved = rr.save(toSave);
        URI location = linkTo(OffreRepresentation.class).slash(saved.getId()).toUri();
        return ResponseEntity.created(location).build();
            }

    // DELETE candidature
    @DeleteMapping(value = "/recrutements/{recrutementId}")
    @Transactional
    public ResponseEntity<?> deleteRecrutement(@PathVariable("recrutementId") String id) {
        Optional<Recrutement> recrutement = rr.findById(id);
        recrutement.ifPresent(rr::delete);
        return ResponseEntity.noContent().build();
    }

    
}