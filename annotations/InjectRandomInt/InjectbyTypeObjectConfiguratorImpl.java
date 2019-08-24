package classwork.my_spring.irobor_my_spring.annotations.InjectRandomInt;

import classwork.my_spring.irobor_my_spring.ObjectFactory;
import classwork.my_spring.irobor_my_spring.configurations.ObjectConfigurator;
import lombok.SneakyThrows;
import org.reflections.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Set;

public class InjectbyTypeObjectConfiguratorImpl implements ObjectConfigurator {
    @Override
    @SneakyThrows
    public void configurator(Object o) {
        Set<Field> fields = ReflectionUtils.getAllFields(o.getClass(), field -> field.isAnnotationPresent(InjectByType.class));
        for (Field field: fields) {
            field.setAccessible(true);
            Object object = ObjectFactory.getInstance().createObject(field.getType());
            field.set(o,object);
        }

                ;
    }
}
