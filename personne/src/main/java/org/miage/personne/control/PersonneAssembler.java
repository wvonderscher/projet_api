package org.miage.personne.control;

import java.util.List;
import org.miage.personne.boundary.PersonneRepresentation;
import org.miage.personne.Entity.Personne;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import java.util.stream.StreamSupport;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class PersonneAssembler implements RepresentationModelAssembler<Personne, EntityModel<Personne>> {

    @Override
    public EntityModel<Personne> toModel(Personne personne) {
        return EntityModel.of(personne,
                linkTo(methodOn(PersonneRepresentation.class).getAllPersonnes()).withRel("collection"),
                linkTo(methodOn(PersonneRepresentation.class).getPersonneByName(personne.getNomUser())).withSelfRel()
                );
    }

    @Override
    public CollectionModel<EntityModel<Personne>> toCollectionModel(Iterable<? extends Personne> entities) {
        List<EntityModel<Personne>> personneModel = StreamSupport
            .stream(entities.spliterator(), false)
            .map(this::toModel)
            .toList();

        return CollectionModel.of(personneModel, linkTo(methodOn(PersonneRepresentation.class)
            .getAllPersonnes()).withSelfRel());
    }
}