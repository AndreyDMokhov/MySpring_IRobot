package classwork.my_spring.irobor_my_spring.boot;

import classwork.my_spring.irobor_my_spring.configurations.Config;
import classwork.my_spring.irobor_my_spring.configurations.JavaConfig;
import classwork.my_spring.irobor_my_spring.configurations.ObjectConfiguratorOfAnnotation;
import classwork.my_spring.irobor_my_spring.configurations.ProxyConfigurator;
import lombok.SneakyThrows;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ApplicationContext {
    private Config javaConfig;
    private List<ObjectConfiguratorOfAnnotation> configuratorsByAnnotations = new ArrayList<>();
    private List<ProxyConfigurator> proxyConfigurators = new ArrayList<>();
    private BeanFactory beanFactory = BeanFactory.getInstance();

    private ApplicationContext() {

    }

    public static ApplicationContext getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private Reflections scanner;

    private static class SingletonHolder {
        private static final ApplicationContext INSTANCE = new ApplicationContext();
    }

    public static ApplicationContext run(String packageName) {

        return getInstance().runner(packageName);
    }

    private ApplicationContext runner(String packageName) {

        createAppContext(packageName);
        return this;
    }

    public <T> T getBean(Class<T> beanClass) {
        if (beanClass.isInterface()) {
            beanClass = javaConfig.getImplClass(beanClass);
        }
        return (T) beanFactory.getBean(beanClass);
    }

    private void createAppContext(String packageName) {
        scanner = new Reflections(packageName);
        getAllObjectConfiguration(scanner);
        beanFactory.createContext(scanner);
        beanFactory.populateProperties(configuratorsByAnnotations);
        beanFactory.proxyWrapIfNeeded(proxyConfigurators);

    }

    @SneakyThrows
    private void getAllObjectConfiguration(Reflections scanner) {

        Set<Class<? extends ObjectConfiguratorOfAnnotation>> annotationConfig = scanner.getSubTypesOf(ObjectConfiguratorOfAnnotation.class);
        Set<Class<? extends ProxyConfigurator>> proxyConfig = scanner.getSubTypesOf(ProxyConfigurator.class);
        for (Class<? extends ObjectConfiguratorOfAnnotation> aClass : annotationConfig) {
            configuratorsByAnnotations.add(aClass.getDeclaredConstructor().newInstance());
        }
        for (Class<? extends ProxyConfigurator> aClass : proxyConfig) {
            proxyConfigurators.add(aClass.getDeclaredConstructor().newInstance());
        }
        javaConfig = new JavaConfig();
    }

     Config getJavaConfig() {
        return javaConfig;
    }


}
