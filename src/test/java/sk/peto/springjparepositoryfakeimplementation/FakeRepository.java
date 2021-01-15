package sk.peto.springjparepositoryfakeimplementation;

import sk.peto.springjparepositoryfakeimplementation.operations.FetchOperations;
import sk.peto.springjparepositoryfakeimplementation.operations.SaveOperations;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class FakeRepository<T> implements SaveOperations<T>, FetchOperations<T, Integer> {

    private final Map<Integer, T> db;
    private final String idFieldName;
    private final AtomicInteger sequence;

    public FakeRepository() {
        this("id");
    }

    public FakeRepository(final String idFieldName) {
        if (idFieldName == null || idFieldName.trim().isEmpty()) {
            throw new IllegalArgumentException("idFieldName can not be null or blank");
        }
        this.idFieldName = idFieldName;
        this.db = new ConcurrentHashMap<>();
        this.sequence = new AtomicInteger(1);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <S extends T> S save(final S entity) {
        try {
            final Integer id = getOrGenerateEntityId(entity);
            setEntityId(entity, id);
            db.put(id, entity);
            return (S) db.get(id);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <S extends T> Collection<S> saveAll(final Iterable<S> entities) {
        return StreamSupport.stream(entities.spliterator(), false)
                .map(this::save)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<T> findAll() {
        return new ArrayList<>(db.values());
    }

    @Override
    public Optional<T> findById(final Integer id) {
        return Optional.ofNullable(db.get(id));
    }

    private <S extends T> Integer getOrGenerateEntityId(final S entity) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        return (Integer) Optional.ofNullable(entity.getClass().getMethod("get" + firstLetterUppercase(idFieldName)).invoke(entity))
                .orElseGet(sequence::getAndIncrement);
    }

    private <S extends T> void setEntityId(final S entity, final Integer id) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        entity.getClass().getMethod("set" + firstLetterUppercase(idFieldName), Integer.class).invoke(entity, id);
    }

    private String firstLetterUppercase(final String word) {
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }

}
