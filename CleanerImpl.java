package classwork.my_spring.irobor_my_spring;

import classwork.my_spring.irobor_my_spring.annotations.InjectRandomInt.InjectRandomInt;
import classwork.my_spring.irobor_my_spring.annotations.benchmark.Benchmark;
import classwork.my_spring.irobor_my_spring.annotations.component.Component;

/**
 * @author Evgeny Borisov
 */
@Component
@Benchmark
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
