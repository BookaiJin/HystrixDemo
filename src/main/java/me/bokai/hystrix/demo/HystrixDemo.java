package me.bokai.hystrix.demo;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import me.bokai.hystrix.dependency.AnnotationPretreated;

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
        ExecutorService service = Executors.newFixedThreadPool(10);
        System.out.println(new Date(System.currentTimeMillis()));

        for (int i = 0; i < 10; i++) {
            if (i == 5) {
                Thread.sleep(5999);
            }
            service.execute(AnnotationPretreated.enhanceByAnnotation());
//            Thread.sleep(1000);
        }

        if (!service.isShutdown()) {
            service.shutdown();
        }

    }

}
