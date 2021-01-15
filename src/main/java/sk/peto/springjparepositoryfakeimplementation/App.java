package sk.peto.springjparepositoryfakeimplementation;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sk.peto.springjparepositoryfakeimplementation.model.Account;
import sk.peto.springjparepositoryfakeimplementation.repository.AccountRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.Objects;

@SpringBootApplication
public class App implements CommandLineRunner {

    private final AccountRepository accountRepository;

    public App(final AccountRepository accountRepository) {
        this.accountRepository = Objects.requireNonNull(accountRepository, "accountRepository can not be null");
    }

    public static void main(final String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(final String... args) {
        accountRepository.save(new Account("0001234/0800"));
        accountRepository.saveAll(Arrays.asList(new Account("0005678/1100"), new Account("000333/0200")));

        System.out.println("ACCOUNTS");
        System.out.println(accountRepository.findAll());
        System.out.println(accountRepository.findById(1).orElseThrow(EntityNotFoundException::new));
    }

}
