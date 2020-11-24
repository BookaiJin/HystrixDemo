package me.bokai.hystrix.nopreception;

import com.fr.third.net.bytebuddy.implementation.bind.annotation.AllArguments;
import com.fr.third.net.bytebuddy.implementation.bind.annotation.RuntimeType;
import com.fr.third.net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.util.concurrent.Callable;

/**
 * @author bokai
 * @version 10.0
 * Created by bokai on 2020-11-23
 */
public class HystrixInterceptor {

    @RuntimeType
    public static void intercept(@SuperCall Callable<?> callable, @AllArguments Object[] args) throws Exception {
        System.out.println("--------------aaaaaaaaaaaaaa-aaaaaaaaaaaaaa-----------------");
        callable.call();
    }

}
