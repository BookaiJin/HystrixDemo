package me.bokai.hystrix.HystrixCommand;

import com.netflix.hystrix.HystrixCommand;

/**
 * @author bokai
 * @version 10.0
 * Created by bokai on 2020-11-26
 */
public class HystrixCommandFactory {

    public HystrixCommand createIsolateThreadCommand(){
        return new AbstractHystrixCommand("name") {
            @Override
            protected Object run() throws Exception {
                return super.run();
            }
        };
    }
}
