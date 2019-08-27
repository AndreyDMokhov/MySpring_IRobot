package classwork.my_spring.irobor_my_spring.configurations;

public interface ProxyConfigurator  {

     <T> Object wrapObjectProxy(Object o, Class<T> type);
}
