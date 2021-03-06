package ru.raiffeisen.directory_listener;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Scanner;

@SpringBootApplication
@EnableScheduling
public class DirectoryListenerApplication implements CommandLineRunner {

	private static Logger theLog = LoggerFactory.getLogger(DirectoryListenerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DirectoryListenerApplication.class, args);
	}

	@Override
	public void run(String... args)
	{
		theLog.info("EXECUTING : command line runner");

		Scanner in = new Scanner(System.in);

		in.next();
	}
}
