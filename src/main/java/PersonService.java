import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class PersonService {

    private final SessionFactory factory;
    private final PersonRepository repository;

    public PersonService(SessionFactory factory, PersonRepository repository) {
        this.factory = factory;
        this.repository = repository;
    }

    public Person saveOrUpdate(Person person) {
        log.info("Saving or updating person: {}", person);
        Person person1 = null;
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            person1 = repository.saveOrUpdate(person);
            session.getTransaction().commit();
            log.info("Person saved or updated successfully: {}", person);
        } catch (Exception e) {
            log.error("Error Saving or updating person: {}", person, e);
        }
        return person1;
    }

    private List<String> readFile(String path) {
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return lines;
    }

    public void writeToDB(String path) {
        List<String> lines = readFile(path);
        for (int i = 0; i < lines.size(); i += 4) {
            String firstName = lines.get(i).substring(lines.get(i).indexOf(":") + 1).trim();
            String lastName = lines.get(i + 1).substring(lines.get(i + 1).indexOf(":") + 1).trim();
            String username = lines.get(i + 2).substring(lines.get(i + 2).indexOf(":") + 1).trim();
            int age = Integer.parseInt(lines.get(i + 3).substring(lines.get(i + 3).indexOf(":") + 1).trim());

            saveOrUpdate(new Person(firstName, lastName, username, age));
        }
    }
}
