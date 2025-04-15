package Exercicio_05;

import java.io.*;

public class Main1Thread {
    public static void main(String[] args) {
        File pasta = new File(System.getProperty("user.home") + "/Downloads/arquivosDNA");
        File[] arquivos = pasta.listFiles((dir, name) -> name.endsWith(".txt"));

        if (arquivos == null || arquivos.length == 0) {
            System.out.println("Nenhum arquivo .txt encontrado em: " + pasta.getPath());
            return;
        }

        Dna1Thread replicador = new Dna1Thread();

        for (File arquivo : arquivos) {
            try {
                replicador.processarArquivo(arquivo);
            } catch (IOException e) {
                System.err.println("Erro ao processar " + arquivo.getName() + ": " + e.getMessage());
            }
        }
    }
}