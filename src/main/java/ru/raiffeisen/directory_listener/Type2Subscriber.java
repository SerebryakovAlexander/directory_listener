package ru.raiffeisen.directory_listener;

import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.ParallelFlux;
import reactor.core.scheduler.Schedulers;

import javax.annotation.PostConstruct;

@Component
public class Type2Subscriber implements StringSubscriber {

    private static Logger theLog = LoggerFactory.getLogger(DirectoryListenerApplication.class);

    private ParallelFlux<Object> theFlux;

    private String type = "TYPE 2";

    @Autowired
    private StringEmitter stringEmitter;

    @PostConstruct
    public void init() {
        theLog.info("type 2 init");

        this.theFlux = Flux.from(stringEmitter.getTheFlux()).parallel().runOn(Schedulers.parallel());

        this.theFlux.map(o -> ((String)o).toUpperCase()).filter(str -> str.startsWith(type)).subscribe(this);
    }

    @Override
    public void onSubscribe(Subscription s) {
        s.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(String str) {
        theLog.info("subs 2 " + str);
        try
        {
            Thread.sleep(500);
        }
        catch (Exception e)
        {

        }
    }

    @Override
    public void onError(Throwable t) {
    }

    @Override
    public void onComplete() {
    }

}
