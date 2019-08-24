package classwork.my_spring.irobor_my_spring.configurations;

import classwork.my_spring.irobor_my_spring.Cleaner;
import classwork.my_spring.irobor_my_spring.CleanerImpl;
import classwork.my_spring.irobor_my_spring.ConsoleSpeaker;
import classwork.my_spring.irobor_my_spring.Speaker;
import classwork.my_spring.irobor_my_spring.configurations.Config;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Evgeny Borisov
 */
public class JavaConfig implements Config {
    private Map<Class, Class> ifc2ImplClass = new HashMap<>();

    public JavaConfig() {
        ifc2ImplClass.put(Speaker.class, ConsoleSpeaker.class);
        ifc2ImplClass.put(Cleaner.class, CleanerImpl.class);
    }

    @Override
    public <T> Class<T> getImplClass(Class<T> type) {
        return ifc2ImplClass.get(type);
    }
}




