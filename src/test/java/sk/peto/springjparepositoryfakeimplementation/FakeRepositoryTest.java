package sk.peto.springjparepositoryfakeimplementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.peto.springjparepositoryfakeimplementation.model.Person;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class FakeRepositoryTest {

    private FakeRepository<Person> personFakeRepository;

    @BeforeEach
    void setUp() {
        this.personFakeRepository = new FakeRepository<>();
    }

    @Test
    void idAssignedAfterSave() {
        final Person person = personFakeRepository.save(new Person("Peter"));

        assertThat(person)
                .extracting(Person::getId, Person::getName)
                .containsExactly(1, "Peter");
    }

    @Test
    void savePersonWithId() {
        final Person person = personFakeRepository.save(new Person(3, "Peter"));

        assertThat(person)
                .extracting(Person::getId, Person::getName)
                .containsExactly(3, "Peter");
    }

    @Test
    void saveAll() {
        final Collection<Person> people = personFakeRepository.saveAll(
                Arrays.asList(new Person("Peter"), new Person("Jano"))
        );

        assertThat(people)
                .extracting(Person::getId, Person::getName)
                .containsExactly(
                        tuple(1, "Peter"),
                        tuple(2, "Jano")
                );
    }

    @Test
    void findAllReturnsAllSavedPersons() {
        Stream.of("Peter", "Jano", "Mizu")
                .map(Person::new)
                .forEach(personFakeRepository::save);

        assertThat(personFakeRepository.findAll())
                .extracting(Person::getId, Person::getName)
                .containsExactlyInAnyOrder(
                        tuple(1, "Peter"),
                        tuple(2, "Jano"),
                        tuple(3, "Mizu")
                );
    }

    @Test
    void findByIdReturnsEmptyOptionalWhenPersonWithSpecifiedIdNotExists() {
        Stream.of(new Person(2, "Peter"), new Person(3, "Jano"))
                .forEach(personFakeRepository::save);

        assertThat(personFakeRepository.findById(1)).isEmpty();
        assertThat(personFakeRepository.findById(4)).isEmpty();
    }

    @Test
    void findByIdReturnsCorrectPersonWhenExists() {
        Stream.of(new Person(2, "Peter"), new Person(3, "Jano"))
                .forEach(personFakeRepository::save);

        assertThat(personFakeRepository.findById(2).get())
                .extracting(Person::getId, Person::getName)
                .containsExactly(2, "Peter");

        assertThat(personFakeRepository.findById(3).get())
                .extracting(Person::getId, Person::getName)
                .containsExactly(3, "Jano");
    }


}
