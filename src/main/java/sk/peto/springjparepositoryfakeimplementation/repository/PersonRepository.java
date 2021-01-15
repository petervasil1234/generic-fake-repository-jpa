package sk.peto.springjparepositoryfakeimplementation.repository;

import org.springframework.data.repository.CrudRepository;
import sk.peto.springjparepositoryfakeimplementation.operations.FetchOperations;
import sk.peto.springjparepositoryfakeimplementation.operations.SaveOperations;
import sk.peto.springjparepositoryfakeimplementation.model.Person;

import java.util.Collection;
import java.util.Optional;

public interface PersonRepository extends CrudRepository<Person, Integer>,
        SaveOperations<Person>,
        FetchOperations<Person, Integer> {

    @Override
    <S extends Person> S save(S person);

    @Override
    Collection<Person> findAll();

    @Override
    Optional<Person> findById(Integer id);

    @Override
    <S extends Person> Collection<S> saveAll(Iterable<S> people);

}
