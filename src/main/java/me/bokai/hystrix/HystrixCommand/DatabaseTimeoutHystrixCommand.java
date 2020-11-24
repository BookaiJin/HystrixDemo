package me.bokai.hystrix.HystrixCommand;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * @author bokai
 * @version 10.0
 * Created by bokai on 2020-11-24
 */
public class DatabaseTimeoutHystrixCommand extends HystrixCommand<String> {

    private final String name;

    public DatabaseTimeoutHystrixCommand(String name) {
        super(HystrixCommandGroupKey.Factory.asKey(name));
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        return null;
    }
}
