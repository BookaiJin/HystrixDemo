package me.bokai.hystrix.HystrixCommand;

import java.sql.Connection;
import java.util.concurrent.Callable;

/**
 * @author bokai
 * @version 10.0
 * Created by bokai on 2020-11-26
 */
public class DatabaseCreateConnectionCommand extends AbstractHystrixCommand<Connection> {

    private Callable<Connection> callable;

    public DatabaseCreateConnectionCommand(String name, Callable<Connection> callable) {
        super(name);
        this.callable = callable;
    }


    @Override
    protected Connection run() throws Exception {
        return callable.call();
    }
}
