package org.miage.offre.control;

import java.util.List;
import org.miage.offre.boundary.OffreRepresentation;
import org.miage.offre.Entity.Candidature;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import java.util.stream.StreamSupport;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class CandidatureAssembler implements RepresentationModelAssembler<Candidature, EntityModel<Candidature>> {

    @Override
    public EntityModel<Candidature> toModel(Candidature candidature) {
        return EntityModel.of(candidature,
                linkTo(methodOn(OffreRepresentation.class).getOffreById(candidature.getId())).withSelfRel(),
                linkTo(methodOn(OffreRepresentation.class).getCandidaturesByUser(candidature.getNomCandidat())).withRel("collection"),
                linkTo(methodOn(OffreRepresentation.class).getAllOffres()).withRel("collection"));
    }

    @Override
    public CollectionModel<EntityModel<Candidature>> toCollectionModel(Iterable<? extends Candidature> entities) {
        List<EntityModel<Candidature>> candidatureModel = StreamSupport
            .stream(entities.spliterator(), false)
            .map(this::toModel)
            .toList();
        return CollectionModel.of(candidatureModel, linkTo(methodOn(OffreRepresentation.class)
            .getAllOffres()).withSelfRel());
    }
}