package ru.raiffeisen.directory_listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Consumer;

@Component
public class StringEmitter {

    private static Logger theLog = LoggerFactory.getLogger(DirectoryListenerApplication.class);

    private ConnectableFlux<Object> theFlux;

    private Consumer<String> stringEmitter;

    @PostConstruct
    public void init() {
        theLog.info("init");

        this.theFlux = Flux.create(sink -> {
            this.registerStringEmitter(str -> sink.next(str));
        }).publish();

        this.theFlux.connect();
    }

    public ConnectableFlux<Object> getTheFlux() {
        return theFlux;
    }

    @Scheduled(fixedRate = 100)
    public void emit1()
    {
        if (stringEmitter != null) {
            stringEmitter.accept("type 1 emmited string " + getCurrentTimeStamp());
        }
    }

    @Scheduled(fixedRate = 500)
    public void emit2()
    {
        if (stringEmitter != null) {
            stringEmitter.accept("type 2 emmited string " + getCurrentTimeStamp());
        }
    }


    public void registerStringEmitter(Consumer<String> stringConsumer) {
        this.stringEmitter = stringConsumer;
    }

    public static String getCurrentTimeStamp() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }
}
