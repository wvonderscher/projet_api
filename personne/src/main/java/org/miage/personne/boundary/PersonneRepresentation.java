package org.miage.personne.boundary;
import java.net.URI;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;


import org.miage.personne.control.PersonneAssembler;
import org.miage.personne.Entity.Personne;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
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
import org.springframework.web.client.RestTemplate;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Controller
@ResponseBody
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class PersonneRepresentation {

    private final PersonneResource ir;
    private final PersonneAssembler ia;
    private final RestTemplate template;
    
    public PersonneRepresentation(PersonneResource ir, PersonneAssembler ia, RestTemplate rt) {
        this.ir = ir;
        this.ia = ia;
        this.template = rt;
    }

    // GET all
    @GetMapping
    public ResponseEntity<?> getAllPersonnes() {
        return ResponseEntity.ok(ia.toCollectionModel(ir.findAll()));
    }


    // GET all candidature
    @GetMapping("/{nomCandidat}/candidatures")
    public ResponseEntity<?> getAllCandidature(@PathVariable("nomCandidat") String nomCandidat) {
        String url ="http://offreService:8000/offres/{nomCandidat}/candidatures";
        return ResponseEntity.ok(template.getForEntity(url, CollectionModel.class, nomCandidat)).getBody();
    }

    // GET one candidature
    @GetMapping("/{nomCandidat}/candidatures/{offreId}")
    public ResponseEntity<?> getOneCandidature(@PathVariable("nomCandidat") String nomCandidat, @PathVariable("offreId") String offreId){
        String url ="http://offreService:8000/offres/{nomCandidat}/candidatures/{offreId}";
        return ResponseEntity.ok(template.getForEntity(url, EntityModel.class, nomCandidat, offreId)).getBody();
    }

    // GET one by name
    @GetMapping(value = "/{personneName}") 
    public ResponseEntity<?> getPersonneByName(@PathVariable("personneName") String name) {
        //OPTIONAL donne l'erreur : method isPresent in class java.util.Optional<T> cannot be applied to given types . sauf qu'aucun type n'est donné...
        return ResponseEntity.ok(ia.toModel(ir.findByNomUser(name)));
     }

    @DeleteMapping("/{nomCandidat}/candidatures/{offreId}")
    public ResponseEntity<?> deleteCandidature(@PathVariable("nomCandidat") String nomCandidat, @PathVariable("offreId") String offreId){
        String url = "http://offreService:8000/offres/{nomCandidat}/candidatures/{offreId}/delete";
        template.delete(url, nomCandidat, offreId);
        return ResponseEntity.noContent().build();
    }

    // POST
    @PostMapping
    @Transactional
    public ResponseEntity<?> save(@RequestBody Personne personne) {
        Personne toSave = new Personne(UUID.randomUUID().toString(),
                personne.getNomUser(),
                personne.getTelUser());
        Personne saved = ir.save(toSave);
        URI location = linkTo(PersonneRepresentation.class).slash(saved.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    // DELETE
    @DeleteMapping(value = "/{personneId}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable("personneId") String id) {
        Optional<Personne> personne = ir.findById(id);
        personne.ifPresent(ir::delete);
        return ResponseEntity.noContent().build();
    }

    // PUT
    @PutMapping(value = "/{personneId}")
    @Transactional
    public ResponseEntity<?> update(@PathVariable("personneId") String id,
            @RequestBody Personne newPersonne) {
        if (!ir.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Personne toSave = new Personne(UUID.randomUUID().toString(),
                newPersonne.getNomUser(),
                newPersonne.getTelUser());
        toSave.setId(id);
        ir.save(toSave);
        return ResponseEntity.ok().build();
    }


}