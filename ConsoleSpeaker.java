package classwork.my_spring.irobor_my_spring;

/**
 * @author Evgeny Borisov
 */
public class ConsoleSpeaker implements Speaker {
    @Override
    public void speak(String message) {
        System.out.println(message);
    }
}
