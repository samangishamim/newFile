import org.hibernate.SessionFactory;

public class FileProject {
    public static void main(String[] args) {
        SessionFactory factory = SessionFactorySingleton.getInstance();
        PersonRepository repository = new PersonRepository();
        PersonService personService = new PersonService(factory, repository);
        personService.saveToDataBase("textFiles/people.txt");
    }
}
