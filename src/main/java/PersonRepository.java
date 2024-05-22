import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

@Slf4j
public class PersonRepository {

    private SessionFactory factory = SessionFactorySingleton.getInstance();
    public Person saveOrUpdate(Person person) {
        log.info("Save or update the person: {}", person);
        Session session = factory.getCurrentSession();
        if (person.getId() == null) {
            session.persist(person);
            log.info("Persist  person: {}", person);
        } else {
            session.merge(person);
            log.info("Merge  person: {}", person);
        }
        return person;
    }
}
