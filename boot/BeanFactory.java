package classwork.my_spring.irobor_my_spring.boot;

import classwork.my_spring.irobor_my_spring.annotations.component.Component;
import classwork.my_spring.irobor_my_spring.configurations.ObjectConfiguratorOfAnnotation;
import lombok.SneakyThrows;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BeanFactory {

    private Reflections scanner;
    private List<ObjectConfiguratorOfAnnotation> configuratorsByAnnotations;
    private Map<Class<?>, Object> context = new HashMap<>();

    private BeanFactory() {

    }

    public static BeanFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public Map<Class<?>, Object> getContext() {
        return context;
    }

    @SneakyThrows
    public void createContext(String packageName) {
        scanner = new Reflections(packageName);
        Set<Class<?>> classes = scanner.getTypesAnnotatedWith(Component.class);
        for (Class<?> aClass : classes) {
           Component annotation = aClass.getAnnotation(Component.class);
            if (annotation.lazy()){
                continue;
            }
            putInContext(aClass);

        }
    }
    @SneakyThrows
    private Object putInContext(Class<?> clazz) {
        Object bean = context.put(clazz, clazz.getDeclaredConstructor().newInstance());
        return context.get(clazz);
    }


    public void populateProperties(List<ObjectConfiguratorOfAnnotation> configuratorsByAnnotations) {
        this.configuratorsByAnnotations = configuratorsByAnnotations;
        for (Object bean : context.values()) {
            populateProperty(bean);
        }
    }

    @SneakyThrows
    public Object getBean(Class<?> beanClass) {

        Object bean = context.get(beanClass) ;
        if (bean == null) {
            populateProperty(putInContext(beanClass));
         }
        return context.get(beanClass);
    }

    private void populateProperty( Object bean) {
        for (ObjectConfiguratorOfAnnotation configuratorsByAnnotation : configuratorsByAnnotations) {
            configuratorsByAnnotation.configurator(bean);
        }
    }

    private static class SingletonHolder {
        private static final BeanFactory INSTANCE = new BeanFactory();
    }

}
