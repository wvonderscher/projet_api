package org.miage.offre.boundary;
import java.net.URI;
import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;
import org.miage.offre.control.OffreAssembler;
import org.miage.offre.control.CandidatureAssembler;
import org.miage.offre.Entity.Offre;
import org.miage.offre.Entity.Candidature;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

    private final OffreResource ir;
    private final OffreAssembler ia;
    private final CandidatureResource cr;
    private final CandidatureAssembler ca;
    
    public OffreRepresentation(OffreResource ir, OffreAssembler ia, CandidatureResource cr, CandidatureAssembler ca) {
        this.ir = ir;
        this.ia = ia;
        this.cr = cr;
        this.ca = ca;
    }

    // GET all
    @GetMapping
    public ResponseEntity<?> getAllOffres() {
        return ResponseEntity.ok(ia.toCollectionModel(ir.findAll()));
    }

 

    // GET one
    @GetMapping(value = "/{offreId}")
    public ResponseEntity<?> getOffreById(@PathVariable("offreId") String id) {
        return Optional.of(ir.findById(id))
                .filter(Optional::isPresent)
                .map(i -> ResponseEntity.ok(ia.toModel(i.get())))
                .orElse(ResponseEntity.notFound().build());
    }

    // GET all by Domaine
    @GetMapping(value = "/domaine/{offreDomaine}")
    public ResponseEntity<?> getOffreByDomaine(@PathVariable("offreDomaine") String domaine) {
        return ResponseEntity.ok(ia.toCollectionModel(ir.findByDomaine(domaine)));
    }   
    
    // GET all by organisation
    @GetMapping(value = "/organisation/{offreOrganisation}")
    public ResponseEntity<?> getOffreByOrganisation(@PathVariable("offreOrganisation") String organisation) {
        return ResponseEntity.ok(ia.toCollectionModel(ir.findByNomOrganisation(organisation)));
    }
    
    // GET all by DateDebutStage
    @GetMapping(value = "/dateDebut/{offreDateDebutStage}")
    public ResponseEntity<?> getOffreByDateDebut(@PathVariable("offreDateDebutStage") String dateDebutStage) {
        return ResponseEntity.ok(ia.toCollectionModel(ir.findByDateDebutStage(dateDebutStage)));
    }

     // GET all organisation
    @GetMapping(value = "/lieu/{offreLieuStageAdresse}")
    public ResponseEntity<?> getOffreByLieuStageAdresse(@PathVariable("offreLieuStageAdresse") String lieuStageAdresse) {
         return ResponseEntity.ok(ia.toCollectionModel(ir.findByLieuStageAdresse(lieuStageAdresse)));
     }
    

    // POST
    @PostMapping
    @Transactional
    public ResponseEntity<?> save(@RequestBody Offre offre) {
        Offre toSave = new Offre(UUID.randomUUID().toString(),
                offre.getNomStage(),
                offre.getDomaine(),
                offre.getNomOrganisation(),
                offre.getDescriptionStage(),
                offre.getDateDebutStage(),
                offre.getLieuStageAdresse());

        Offre saved = ir.save(toSave);
        URI location = linkTo(OffreRepresentation.class).slash(saved.getId()).toUri();
        return ResponseEntity.created(location).build();
    }


        // POST candidature
        @PostMapping("/{offreId}")
        @Transactional
        public ResponseEntity<?> saveCandidature(@RequestBody Candidature candidature, @PathVariable("offreId") String id) {
           Candidature toSave = new Candidature(UUID.randomUUID().toString(),
           id,
           candidature.getIdUser(),
           candidature.getNomCandidat(),
           "soumise");
    Candidature saved = cr.save(toSave);
    URI location = linkTo(OffreRepresentation.class).slash(saved.getId()).toUri();
    return ResponseEntity.created(location).build();
        }


    // GET all Candidature
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

    // DELETE
    @DeleteMapping(value = "/{offreId}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable("offreId") String id) {
        Optional<Offre> offre = ir.findById(id);
        offre.ifPresent(ir::delete);
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

    // PUT
    @PutMapping(value = "/{offreId}")
    @Transactional
    public ResponseEntity<?> update(@PathVariable("offreId") String id,
            @RequestBody Offre newOffre) {
        if (!ir.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Offre toSave = new Offre(UUID.randomUUID().toString(),
                newOffre.getNomStage(),
                newOffre.getDomaine(),
                newOffre.getNomOrganisation(),
                newOffre.getDescriptionStage(),
                newOffre.getDateDebutStage(),
                newOffre.getLieuStageAdresse());
        toSave.setId(id);
        ir.save(toSave);
        return ResponseEntity.ok().build();
    }
}