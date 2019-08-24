package classwork.my_spring.irobor_my_spring;

import classwork.my_spring.irobor_my_spring.configurations.Config;
import classwork.my_spring.irobor_my_spring.configurations.JavaConfig;
import classwork.my_spring.irobor_my_spring.configurations.ObjectConfigurator;
import lombok.SneakyThrows;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Evgeny Borisov
 */
public class ObjectFactory {
    private static ObjectFactory ourInstance = new ObjectFactory();
    private Config config = new JavaConfig();
    private List<ObjectConfigurator> configurators = new ArrayList<>();

    public static ObjectFactory getInstance() {
        return ourInstance;
    }

    @SneakyThrows
    private ObjectFactory() {
        Reflections scanner = new Reflections("classwork.my_spring.irobor_my_spring");
        Set<Class<? extends ObjectConfigurator>> subTypesOf = scanner.getSubTypesOf(ObjectConfigurator.class);
        for (Class<? extends ObjectConfigurator> aClass : subTypesOf) {
            configurators.add(aClass.getDeclaredConstructor().newInstance());
        }
    }


    @SneakyThrows
    public <T> T createObject(Class<T> type) {
        type=resolveImpl(type);
        T t = createInst(type);
        configure(t);
        return t;
    }



    private <T> void configure(T t) {
        configurators.forEach(configurator -> configurator.configurator(t));
    }

    private <T> T createInst(Class<T> type) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
      return   type.getDeclaredConstructor().newInstance();
    }

    private <T> Class<T> resolveImpl(Class<T> type) {
        if (type.isInterface()){
            type = config.getImplClass(type);
        }
        return type;
    }


}
