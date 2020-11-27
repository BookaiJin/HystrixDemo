package me.bokai.hystrix.dependency;

import com.fr.third.net.bytebuddy.ByteBuddy;
import com.fr.third.net.bytebuddy.implementation.MethodDelegation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import me.bokai.hystrix.demo.HystrixDemo;
import me.bokai.hystrix.jdbc.MysqlConnector;
import me.bokai.hystrix.nopreception.Hystrix;
import me.bokai.hystrix.nopreception.HystrixInterceptor;

/**
 * @author bokai
 * @version 10.0
 * Created by bokai on 2020-11-23
 */
public class AnnotationPretreated {
    /**
     * 把 MysqlConnector 中所有带有 @Hystrix 注解的方法都过滤出来
     */
    static List<String> methodsWithHystrix = Stream.of(MysqlConnector.class.getDeclaredMethods())
            .filter(AnnotationPretreated::isAnnotationWithHystrix)
            .map(Method::getName)
            .collect(Collectors.toList());

    public static MysqlConnector enhanceByAnnotation() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        return new ByteBuddy()
                // 创建用于生成子类的 builder
                .subclass(MysqlConnector.class)
                // 控制目标子类上具有哪些方法
                .method(method -> methodsWithHystrix.contains(method.getName()))
                // 对匹配到的方法进行拦截，传入定制化的方法实现，继续返回 builder
                .intercept(MethodDelegation.to(HystrixInterceptor.class))
                // 根据 builder 中的信息生成尚未加载的动态类型（目标子类）
                .make()
                // 尝试加载该动态类型
                .load(HystrixDemo.class.getClassLoader())
                // 获取加载之后的 class 对象
                .getLoaded()
                .getConstructor()
                .newInstance();
    }

    private static boolean isAnnotationWithHystrix(Method method) {
        // 尝试通过反射获取方法上的注解
        return method.getAnnotation(Hystrix.class) != null;
    }
}
