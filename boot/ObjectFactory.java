package classwork.my_spring.irobor_my_spring.boot;

import classwork.my_spring.irobor_my_spring.configurations.Config;
import classwork.my_spring.irobor_my_spring.configurations.JavaConfig;
import classwork.my_spring.irobor_my_spring.configurations.ObjectConfiguratorOfAnnotation;
import lombok.SneakyThrows;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Evgeny Borisov
 */
public class ObjectFactory {
    private ApplicationContext applicationContext;
    private static ObjectFactory ourInstance = new ObjectFactory();

    public static ObjectFactory getInstance() {
        return ourInstance;
    }

    @SneakyThrows
    private ObjectFactory() {
        applicationContext = ApplicationContext.getInstance();
    }

    @SneakyThrows
    public <T> T createObject(Class<T> type) {
        type=resolveImpl(type);
        T t = createInst(type);
        return t;
    }

    private <T> T createInst(Class<T> type) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
      return   type.getDeclaredConstructor().newInstance();
    }

    private <T> Class<T> resolveImpl(Class<T> type) {

        if (type.isInterface()){
            Config config = applicationContext.getJavaConfig();
            type = config.getImplClass(type);
        }
        return type;
    }


}
