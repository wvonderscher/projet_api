package org.miage.offre.control;

import java.util.List;
import org.miage.offre.boundary.OffreRepresentation;
import org.miage.offre.Entity.Offre;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import java.util.stream.StreamSupport;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class OffreAssembler implements RepresentationModelAssembler<Offre, EntityModel<Offre>> {

    @Override
    public EntityModel<Offre> toModel(Offre offre) {
        return EntityModel.of(offre,
                linkTo(methodOn(OffreRepresentation.class).getOffreById(offre.getId())).withSelfRel(),
                linkTo(methodOn(OffreRepresentation.class).getAllOffres()).withRel("collection"));
    }

    @Override
    public CollectionModel<EntityModel<Offre>> toCollectionModel(Iterable<? extends Offre> entities) {
        List<EntityModel<Offre>> intervenanModel = StreamSupport
            .stream(entities.spliterator(), false)
            .map(this::toModel)
            .toList();
        return CollectionModel.of(intervenanModel, linkTo(methodOn(OffreRepresentation.class)
            .getAllOffres()).withSelfRel());
    }
}