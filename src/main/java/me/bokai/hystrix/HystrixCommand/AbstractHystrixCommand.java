package me.bokai.hystrix.HystrixCommand;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * @author bokai
 * @version 10.0
 * Created by bokai on 2020-11-26
 */
public abstract class AbstractHystrixCommand<T> extends HystrixCommand<T> {

    public AbstractHystrixCommand(String name) {
        super(HystrixCommandGroupKey.Factory.asKey(name));
    }

    @Override
    protected T run() throws Exception {
        return null;
    }
}
