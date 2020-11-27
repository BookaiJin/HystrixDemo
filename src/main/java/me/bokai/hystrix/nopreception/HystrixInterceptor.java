package me.bokai.hystrix.nopreception;

import com.fr.third.net.bytebuddy.implementation.bind.annotation.AllArguments;
import com.fr.third.net.bytebuddy.implementation.bind.annotation.Origin;
import com.fr.third.net.bytebuddy.implementation.bind.annotation.RuntimeType;
import com.fr.third.net.bytebuddy.implementation.bind.annotation.SuperCall;
import com.fr.third.net.bytebuddy.implementation.bind.annotation.This;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.concurrent.Callable;
import me.bokai.hystrix.HystrixCommand.DatabaseCreateConnectionCommand;
import me.bokai.hystrix.HystrixCommand.DatabaseTimeoutHystrixCommand;
import me.bokai.hystrix.scene.HystrixScene;

/**
 * @author bokai
 * @version 10.0
 * Created by bokai on 2020-11-23
 */
public class HystrixInterceptor {

    @RuntimeType
    public static Object intercept(@This Object self, @Origin Method method, @SuperCall Callable callable, @AllArguments Object[] args) throws Exception {
        Hystrix hystrix = method.getAnnotation(Hystrix.class);
        if (hystrix.scene() == HystrixScene.CREATE_CONNECTION) {
            if (self instanceof Connection) {
                DatabaseCreateConnectionCommand databaseCreateConnectionCommand = new DatabaseCreateConnectionCommand("DbCreation", callable);
                return databaseCreateConnectionCommand.execute();
            }
            return null;
        } else if (hystrix.scene() == HystrixScene.EXECUTE_QUERY) {
            DatabaseTimeoutHystrixCommand databaseTimeoutHystrixCommand = new DatabaseTimeoutHystrixCommand("DbExecutor", callable);
            return databaseTimeoutHystrixCommand.execute();
        }
        return null;
    }

}
