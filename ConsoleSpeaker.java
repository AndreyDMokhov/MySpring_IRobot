package classwork.my_spring.irobor_my_spring;

import classwork.my_spring.irobor_my_spring.annotations.component.Component;

/**
 * @author Evgeny Borisov
 */
@Component
public class ConsoleSpeaker implements Speaker {
    @Override
    public void speak(String message) {
        System.out.println(message);
    }
}
