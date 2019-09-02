package classwork.my_spring.irobor_my_spring.boot;

import classwork.my_spring.irobor_my_spring.annotations.component.Component;
import classwork.my_spring.irobor_my_spring.configurations.ObjectConfiguratorOfAnnotation;
import classwork.my_spring.irobor_my_spring.configurations.ProxyConfigurator;
import lombok.SneakyThrows;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BeanFactory {

   private BeanFactory() {
    }

     static BeanFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private List<ObjectConfiguratorOfAnnotation> configuratorsByAnnotations;
    private Map<Class<?>, Object> beanContext = new HashMap<>();


    @SneakyThrows
    void createContext(Reflections scanner) {
        Set<Class<?>> classes = scanner.getTypesAnnotatedWith(Component.class);
        for (Class<?> aClass : classes) {
            Component annotation = aClass.getAnnotation(Component.class);
            if (annotation.lazy()) {
                continue;
            }
            putInContext(aClass);

        }
    }

    @SneakyThrows
    private Object putInContext(Class<?> clazz) {
        beanContext.put(clazz, clazz.getDeclaredConstructor().newInstance());
        return beanContext.get(clazz);
    }


    void populateProperties(List<ObjectConfiguratorOfAnnotation> configuratorsByAnnotations) {
        this.configuratorsByAnnotations = configuratorsByAnnotations;
        for (Object bean : beanContext.values()) {
            populateProperty(bean);
        }
    }

    void proxyWrapIfNeeded(List<ProxyConfigurator> proxyConfigurators) {
        for (Map.Entry<Class<?>, Object> classObjectEntry : beanContext.entrySet()) {
            for (ProxyConfigurator proxyConfigurator : proxyConfigurators) {
                Object wrappedObj = proxyConfigurator.wrapObjectProxy(classObjectEntry.getValue(), classObjectEntry.getKey());
                beanContext.replace(classObjectEntry.getKey(), wrappedObj);
            }
        }
    }

    @SneakyThrows
    Object getBean(Class<?> beanClass) {
        Object bean = beanContext.get(beanClass);
        if (bean == null) {
            populateProperty(putInContext(beanClass));
        }
        return beanContext.get(beanClass);
    }

    private void populateProperty(Object bean) {
        for (ObjectConfiguratorOfAnnotation configuratorsByAnnotation : configuratorsByAnnotations) {
            configuratorsByAnnotation.configurator(bean);
        }
    }

    private static class SingletonHolder {
        private static final BeanFactory INSTANCE = new BeanFactory();
    }

}
