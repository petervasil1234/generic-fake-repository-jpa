package sk.peto.springjparepositoryfakeimplementation.operations;

import java.util.Collection;

public interface SaveOperations<T> {

    <S extends T> S save(S entity);

    <S extends T> Collection<S> saveAll(Iterable<S> entities);

}
