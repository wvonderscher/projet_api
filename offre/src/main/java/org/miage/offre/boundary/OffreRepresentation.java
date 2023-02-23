package org.miage.offre.boundary;
import java.net.URI;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.miage.offre.control.OffreAssembler;
import org.miage.offre.Entity.Offre;
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
    
    public OffreRepresentation(OffreResource ir, OffreAssembler ia) {
        this.ir = ir;
        this.ia = ia;
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

    // POST
    @PostMapping
    @Transactional
    public ResponseEntity<?> save(@RequestBody Offre offre) {
        Offre toSave = new Offre(UUID.randomUUID().toString(),
                offre.getNomStage(),
                offre.getDomaine(),
                offre.getNomOrganisation(),
                offre.getDescriptionStage());
        Offre saved = ir.save(toSave);
        URI location = linkTo(OffreRepresentation.class).slash(saved.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    // DELETE
    @DeleteMapping(value = "/{offreId}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable("offreId") String id) {
        Optional<Offre> offre = ir.findById(id);
        offre.ifPresent(ir::delete);
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
                newOffre.getDescriptionStage());
        toSave.setId(id);
        ir.save(toSave);
        return ResponseEntity.ok().build();
    }


}