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
    public EntityModel<Recrutement> toModel(Recrutement recrutement) {
        return EntityModel.of(recrutement,
                linkTo(methodOn(OffreRepresentation.class).getRecrutementById(recrutement.getId())).withSelfRel(),
                linkTo(methodOn(OffreRepresentation.class).getAllRecrutements()).withRel("collection"));
    }

    @Override
    public CollectionModel<EntityModel<Recrutement>> toCollectionModel(Iterable<? extends Recrutement> entities) {
        List<EntityModel<Recrutement>> recrutementModel = StreamSupport
            .stream(entities.spliterator(), false)
            .map(this::toModel)
            .toList();
        return CollectionModel.of(recrutementModel, linkTo(methodOn(OffreRepresentation.class)
            .getAllRecrutements()).withSelfRel());
    }
}