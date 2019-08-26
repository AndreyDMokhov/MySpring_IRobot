package classwork.my_spring.irobor_my_spring.annotations.inject_random_type;

import classwork.my_spring.irobor_my_spring.boot.ObjectFactory;
import classwork.my_spring.irobor_my_spring.configurations.ObjectConfiguratorOfAnnotation;
import lombok.SneakyThrows;
import org.reflections.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Set;

public class InjectByTypeObjectConfiguratorImpl implements ObjectConfiguratorOfAnnotation {
    @Override
    @SneakyThrows
    public void configurator(Object o) {
        Set<Field> fields = ReflectionUtils.getAllFields(o.getClass(), field -> field.isAnnotationPresent(InjectByType.class));
        for (Field field: fields) {
            field.setAccessible(true);
            Object object = ObjectFactory.getInstance().createObject(field.getType());
            field.set(o,object);
        }
    }
}
