package ru.raiffeisen.directory_listener;

import org.reactivestreams.Subscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Consumer;

@Component
public class DirectoryListener {

    private static Logger theLog = LoggerFactory.getLogger(DirectoryListenerApplication.class);

    private Flux<String> theFlux;

    private Consumer<String> stringConsumer;

    @PostConstruct
    public void init() {
        theLog.info("init");

        this.theFlux = Flux.create(sink -> {
            this.registerConsumer(str -> sink.next(str));
        });
    }

    public void subscribe(Subscriber<String> consumer) {
        this.theFlux.map(str -> str.toUpperCase()).filter(str -> str.length() < 50).subscribe(consumer);
    }

    @Scheduled(fixedRate = 100)
    public void checkDirectory()
    {
        if (stringConsumer != null) {
            stringConsumer.accept("emmited string " + getCurrentTimeStamp());
        }
    }

    public void registerConsumer(Consumer<String> stringConsumer) {
        this.stringConsumer = stringConsumer;
    }

    public static String getCurrentTimeStamp() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }
}
