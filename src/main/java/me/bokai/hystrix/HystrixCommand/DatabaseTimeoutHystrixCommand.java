package me.bokai.hystrix.HystrixCommand;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import java.sql.ResultSet;
import java.util.concurrent.Callable;

/**
 * @author bokai
 * @version 10.0
 * Created by bokai on 2020-11-24
 */
public class DatabaseTimeoutHystrixCommand extends HystrixCommand<ResultSet> {

    private final String name;
    private final Callable<ResultSet> callable;

    public DatabaseTimeoutHystrixCommand(String name, Callable<ResultSet> callable) {
        super(HystrixCommand.Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey(name))
                .andCommandPropertiesDefaults(
                        HystrixCommandProperties.Setter()
                                .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD)
                                .withExecutionTimeoutEnabled(true)
                                .withExecutionTimeoutInMilliseconds(5000))
                .andThreadPoolPropertiesDefaults
                        (HystrixThreadPoolProperties.Setter()
                                .withCoreSize(5)
                                .withMaxQueueSize(10)
                                .withMaximumSize(10)));
        this.callable = callable;
        this.name = name;
    }

    @Override
    protected ResultSet run() throws Exception {
        return callable.call();
    }

    @Override
    protected ResultSet getFallback() {
        throw new RuntimeException("aaa");
    }
}
