package classwork.my_spring.irobor_my_spring.configurations;

import classwork.my_spring.irobor_my_spring.annotations.benchmark.Benchmark;
import lombok.SneakyThrows;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class BenchmarkProxyConfiguratorImpl implements ProxyConfigurator {
    @Override
    public <T> Object wrapObjectProxy(Object o, Class<T> type) {
        if (type.isAnnotationPresent(Benchmark.class)) {
            return Proxy.newProxyInstance(type.getClassLoader(), type.getInterfaces(), ((proxy, method, args) -> getInvocationHandler(o, type, method, args)));
        }

        return o;
    }

    @SneakyThrows
    private <T> Object getInvocationHandler(Object o, Class<T> type, Method method, Object[] args) {
        Method typeMethod = type.getMethod(method.getName(), method.getParameterTypes());
        if (type.isAnnotationPresent(Benchmark.class)) {
            System.out.println("*********BENCHMARK for method " + method.getName() + " started");
            long start = System.nanoTime();
            Object retVal = method.invoke(o, args);
            long end = System.nanoTime();
            System.out.println(end - start);
            System.out.println("*********BENCHMARK for method " + method.getName() + " finished");

            return retVal;
        }
        return method.invoke(o, args);
    }
}
