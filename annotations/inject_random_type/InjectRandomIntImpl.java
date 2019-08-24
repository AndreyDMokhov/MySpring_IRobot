package classwork.my_spring.irobor_my_spring.annotations.inject_random_type;

import classwork.my_spring.irobor_my_spring.configurations.ObjectConfigurator;
import homework.exercises.RandomUtil;
import lombok.SneakyThrows;
import org.reflections.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Set;

public class InjectRandomIntImpl implements ObjectConfigurator {

        @Override
        @SneakyThrows
        public void configurator(Object o) {
                Class<?> type = o.getClass();
                Set<Field> fields = ReflectionUtils.getAllFields(type, field -> field.isAnnotationPresent(InjectRandomInt.class));
                for (Field field:fields) {
                        InjectRandomInt annotation = field.getAnnotation(InjectRandomInt.class);
                        int min = annotation.min();
                        int max = annotation.max();
                        int value = RandomUtil.getRandomInRangeInteger(min, max);
                        field.setAccessible(true);
                        field.set(o,value);
                }
        }
}
