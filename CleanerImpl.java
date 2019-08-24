package classwork.my_spring.irobor_my_spring;

import classwork.my_spring.irobor_my_spring.annotations.inject_random_type.InjectRandomInt;

/**
 * @author Evgeny Borisov
 */
public class CleanerImpl implements Cleaner {
    @InjectRandomInt(min=3,max=7)
    private int repeat;

    @Override
    public void clean() {
        for (int i = 0; i < repeat; i++) {
            System.out.println("VVVVVVVVVVVVVvvvvvvvvvvvvv");
        }
    }
}
