package classwork.my_spring.irobor_my_spring.configurations;

/**
 * @author Evgeny Borisov
 */
public interface Config {
    <T> Class<T> getImplClass(Class<T> type);
}
