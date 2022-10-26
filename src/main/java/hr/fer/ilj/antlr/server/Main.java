package hr.fer.ilj.antlr.server;

import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hr.tel.fer.lab1.logging.LogEntry;
import hr.tel.fer.lab1.logging.LogLoader;

public class Main {

  @SuppressWarnings("resource")
public static void main(String[] args) throws IOException {
    LogLoader loader = new LogLoader();
    List<LogEntry> logs = loader.load(new FileReader("logs.txt"));

    ServerSocket ssc = new ServerSocket(4444);
    ExecutorService pool = Executors.newFixedThreadPool(1);

    while (true) {
      Socket scClient = ssc.accept();
      ServerExecutor st = new ServerExecutor(scClient, logs);
      pool.execute(st);
    }
  }
}
