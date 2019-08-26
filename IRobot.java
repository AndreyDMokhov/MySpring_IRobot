package classwork.my_spring.irobor_my_spring;

import classwork.my_spring.irobor_my_spring.annotations.component.Component;
import classwork.my_spring.irobor_my_spring.annotations.inject_random_type.InjectByType;

/**
 * @author Evgeny Borisov
 */
@Component(lazy = true)
public class IRobot {

    @InjectByType
    private Speaker speaker;
    @InjectByType
    private Cleaner cleaner;

    public void cleanRoom() {
        speaker.speak("I started...");
        cleaner.clean();
        speaker.speak("I finished...");


    }
}
