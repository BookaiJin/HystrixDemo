package me.bokai.hystrix.demo;

import me.bokai.hystrix.dependency.AnnotationPretreated;
import me.bokai.hystrix.jdbc.MysqlConnector;

/**
 * @author bokai
 * @version 10.0
 * Created by bokai on 2020-11-23
 */
public class HystrixDemo {

    static {
//        AnnotationPretreated.annotate();
    }

    public static void main(String... args) throws Exception {
//        ExecutorService service = Executors.newFixedThreadPool(10);
//
//        for (int i = 0; i < 50; i++) {
//            service.execute(new MysqlConnector());
//            Thread.sleep(300);
//        }
//
//        if (!service.isShutdown()) {
//            service.shutdown();
//        }

        MysqlConnector connector = AnnotationPretreated.enhanceByAnnotation();
        connector.run();
    }

}
