package Exercicio_05;

import java.io.*;
import java.util.concurrent.*;

public class MainXThread {
    public static void main(String[] args) throws InterruptedException {
        File pasta = new File(System.getProperty("user.home") + "/Downloads/arquivosDNA");
        File[] arquivos = pasta.listFiles((dir, name) -> name.endsWith(".txt"));

        if (arquivos == null || arquivos.length == 0) {
            System.out.println("Nenhum arquivo .txt encontrado em: " + pasta.getPath());
            return;
        }

        int numThreads = Math.min(arquivos.length, Runtime.getRuntime().availableProcessors());
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        DnaXThread replicador = new DnaXThread();

        for (File arquivo : arquivos) {
            executor.submit(() -> replicador.processarArquivo(arquivo));
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.HOURS);
        System.out.println("Processamento finalizado.");
    }
}