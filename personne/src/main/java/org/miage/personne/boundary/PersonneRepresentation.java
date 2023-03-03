package org.miage.personne.boundary;
import java.net.URI;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.miage.personne.control.PersonneAssembler;
import org.miage.personne.Entity.Personne;
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
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class PersonneRepresentation {

    private final PersonneResource ir;
    private final PersonneAssembler ia;
    
    public PersonneRepresentation(PersonneResource ir, PersonneAssembler ia) {
        this.ir = ir;
        this.ia = ia;
    }

    // GET all
    @GetMapping
    public ResponseEntity<?> getAllPersonnes() {
        return ResponseEntity.ok(ia.toCollectionModel(ir.findAll()));
    }

    // // GET one
    // @GetMapping(value = "/{personneId}")
    // public ResponseEntity<?> getPersonneById(@PathVariable("personneId") String id) {
    //     return Optional.of(ir.findById(id))
    //             .filter(Optional::isPresent)
    //             .map(i -> ResponseEntity.ok(ia.toModel(i.get())))
    //             .orElse(ResponseEntity.notFound().build());
    // }

        // GET one by name
        @GetMapping(value = "/{personneName}") // obligé d'ajouter /user/ sinon une erreur niveau schéma d'URL
        public ResponseEntity<?> getPersonneByName(@PathVariable("personneName") String name) {
            // return Optional.of(ir.findyByNomUser(name))
            //         .filter(Optional::isPresent)
            //         .map(i -> ResponseEntity.ok(ia.toModel(i.get())))
            //         .orElse(ResponseEntity.notFound().build());
            //OPTIONAL donne l'erreur : method isPresent in class java.util.Optional<T> cannot be applied to given types . sauf qu'aucun type n'est donné...
            return ResponseEntity.ok(ia.toModel(ir.findByNomUser(name)));
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