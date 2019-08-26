package classwork.my_spring.irobor_my_spring.boot;

import classwork.my_spring.irobor_my_spring.configurations.Config;
import classwork.my_spring.irobor_my_spring.configurations.JavaConfig;
import classwork.my_spring.irobor_my_spring.configurations.ObjectConfiguratorOfAnnotation;
import lombok.SneakyThrows;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ApplicationContext {
    private Config javaConfig;
    private List<ObjectConfiguratorOfAnnotation> configuratorsByAnnotations = new ArrayList<>();
    private BeanFactory beanFactory = BeanFactory.getInstance();

    private ApplicationContext() {

    }

    public static ApplicationContext getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private Reflections reflections = new Reflections();

    private static class SingletonHolder {
        private static final ApplicationContext INSTANCE = new ApplicationContext();
    }

    public static ApplicationContext run(String packageName) {

        return getInstance().runner(packageName);
    }

    private ApplicationContext runner(String... args) {

        createAppContext(args[0]);
        return this;
    }

    public <T> T getBean(Class<T> beanName) {

         return (T) beanFactory.getBean(beanName);
    }

    private void createAppContext(String packageName) {
        getAllObjectConfiguration(packageName);
        beanFactory.createContext(packageName);
        beanFactory.populateProperties(configuratorsByAnnotations);
    }

    @SneakyThrows
    private void getAllObjectConfiguration(String packageName) {
        Reflections scanner = new Reflections(packageName);
        Set<Class<? extends ObjectConfiguratorOfAnnotation>> subTypesOf = scanner.getSubTypesOf(ObjectConfiguratorOfAnnotation.class);
        for (Class<? extends ObjectConfiguratorOfAnnotation> aClass : subTypesOf) {
            configuratorsByAnnotations.add(aClass.getDeclaredConstructor().newInstance());
        }
        javaConfig = new JavaConfig();
    }


    private <T> void configure(T t) {
        configuratorsByAnnotations.forEach(configurator -> configurator.configurator(t));
    }

    Config getJavaConfig() {
        return javaConfig;
    }



}
