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
    private Consumer<Throwable> errorEmitter;

    private Integer i1 = 0;
    private Integer i2 = 0;

    @PostConstruct
    public void init() {
        theLog.info("init");

        this.theFlux = Flux.create(sink -> {
            this.registerStringEmitter(str -> sink.next(str));
            //this.registerErrorEmitter(err -> sink.error(err));
        }).publish();

        this.theFlux.connect();
    }

    public ConnectableFlux<Object> getTheFlux() {
        return theFlux;
    }

    @Scheduled(fixedRate = 100)
    public void emit1()
    {
        if (i1 < 50) {
            String s = "type 1 emmited string " + getCurrentTimeStamp() + " " + i1;
            i1++;
            stringEmitter.accept(s);
            theLog.info("emitted " + s);
        }
    }

    @Scheduled(fixedRate = 200)
    public void emit2()
    {
        if (i2 < 50) {
            String s = "type 2 emmited string " + getCurrentTimeStamp() + " " + i2;
            i2++;
            stringEmitter.accept(s);
            theLog.info("emitted " + s);
        }
    }

    public void registerStringEmitter(Consumer<String> stringConsumer) {
        this.stringEmitter = stringConsumer;
    }

    public void registerErrorEmitter(Consumer<Throwable> errorEmitter) {
        this.errorEmitter = errorEmitter;

    }

    public static String getCurrentTimeStamp() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }
}
