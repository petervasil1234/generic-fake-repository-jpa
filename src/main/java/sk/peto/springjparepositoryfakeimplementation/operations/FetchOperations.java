package sk.peto.springjparepositoryfakeimplementation.operations;

import java.util.Collection;
import java.util.Optional;

public interface FetchOperations<T, ID> {

    Collection<T> findAll();

    Optional<T> findById(ID id);

}
