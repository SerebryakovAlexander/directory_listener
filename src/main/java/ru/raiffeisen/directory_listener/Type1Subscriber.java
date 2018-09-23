package ru.raiffeisen.directory_listener;

import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.BufferOverflowStrategy;
import reactor.core.publisher.Flux;
import reactor.core.publisher.ParallelFlux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import javax.annotation.PostConstruct;
import java.util.Comparator;

@Component
public class Type1Subscriber implements StringSubscriber {

    private static Logger theLog = LoggerFactory.getLogger(DirectoryListenerApplication.class);

    private ParallelFlux<String> theFlux;

    private Subscription subscription;

    private String type = "TYPE 1";

    @Autowired
    private StringEmitter stringEmitter;

    @PostConstruct
    public void init() {
        theLog.info("type 1 init");

        this.theFlux = Flux.from(stringEmitter.getTheFlux()).parallel().runOn(Schedulers.newSingle("ss1"));

        this.theFlux.filter(str -> str.startsWith(type)).subscribe(this);
    }

    @Override
    public void onSubscribe(Subscription s) {
        this.subscription = s;
        s.request(1);
    }

    @Override
    public void onNext(String str) {
        theLog.info("subs 1 " + str);

        try
        {
            Thread.sleep(2000);
        }
        catch (Exception e)
        {

        }

        this.subscription.request(1);
    }

    @Override
    public void onError(Throwable t) {
        theLog.error(t.toString());
    }

    @Override
    public void onComplete() {
        theLog.info("subs1 completed!");
    }

}
