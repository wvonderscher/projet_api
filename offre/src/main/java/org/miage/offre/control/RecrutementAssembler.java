package org.miage.offre.control;

import java.util.List;
import org.miage.offre.boundary.OffreRepresentation;
import org.miage.offre.Entity.Recrutement;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import java.util.stream.StreamSupport;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class RecrutementAssembler implements RepresentationModelAssembler<Recrutement, EntityModel<Recrutement>> {

    @Override
    public EntityModel<Recrutement> toModel(Recrutement candidature) {
        return EntityModel.of(candidature,
                linkTo(methodOn(OffreRepresentation.class).getOffreById(candidature.getId())).withSelfRel(),
                linkTo(methodOn(OffreRepresentation.class).getAllOffres()).withRel("collection"));
    }

    @Override
    public CollectionModel<EntityModel<Recrutement>> toCollectionModel(Iterable<? extends Recrutement> entities) {
        List<EntityModel<Recrutement>> candidatureModel = StreamSupport
            .stream(entities.spliterator(), false)
            .map(this::toModel)
            .toList();
        return CollectionModel.of(candidatureModel, linkTo(methodOn(OffreRepresentation.class)
            .getAllOffres()).withSelfRel());
    }
}