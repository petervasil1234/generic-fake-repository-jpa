package sk.peto.springjparepositoryfakeimplementation.repository;

import org.springframework.data.repository.CrudRepository;
import sk.peto.springjparepositoryfakeimplementation.operations.FetchOperations;
import sk.peto.springjparepositoryfakeimplementation.operations.SaveOperations;
import sk.peto.springjparepositoryfakeimplementation.model.Account;

import java.util.Collection;
import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Integer>,
        SaveOperations<Account>,
        FetchOperations<Account, Integer> {

    @Override
    <S extends Account> S save(S account);

    @Override
    Collection<Account> findAll();

    @Override
    Optional<Account> findById(Integer id);

    @Override
    <S extends Account> Collection<S> saveAll(Iterable<S> accounts);

}
