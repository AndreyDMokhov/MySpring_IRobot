package classwork.my_spring.irobor_my_spring;

import classwork.my_spring.irobor_my_spring.boot.ApplicationContext;

/**
 * @author Evgeny Borisov
 */
public class Main {
    public static void main(String[] args) {

        ApplicationContext applicationContext = ApplicationContext.run("classwork.my_spring.irobor_my_spring");
        IRobot iRobot = applicationContext.getBean(IRobot.class);
        iRobot.cleanRoom();
    }


 /*   public static void main(String[] args) {
        Reflections scanner = new Reflections("classwork.my_spring.irobor_my_spring");
        Set<Class<? extends ObjectConfiguratorOfAnnotation>> subTypesOf = scanner.getSubTypesOf(ObjectConfiguratorOfAnnotation.class);
        for (Class<? extends ObjectConfiguratorOfAnnotation> aClass : subTypesOf) {
*/
}



