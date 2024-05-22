import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

public class SessionFactorySingleton {
    private SessionFactorySingleton() {
    }

    private static class SessionFactoryHelper {
        static ServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        private static final SessionFactory INSTANCE = new MetadataSources(registry)
                .addAnnotatedClass(Person.class)
                .buildMetadata()
                .buildSessionFactory();
    }

    public static SessionFactory getInstance() {
        return SessionFactoryHelper.INSTANCE;
    }
}
