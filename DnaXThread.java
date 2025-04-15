package Exercicio_05;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class DnaXThread {

    private static final Map<Character, Character> complemento = Map.of(
        'A', 'T',
        'T', 'A',
        'C', 'G',
        'G', 'C'
    );

    public void processarArquivo(File arquivo) {
        try {
            List<String> linhas = Files.readAllLines(arquivo.toPath());
            List<String> resultado = new ArrayList<>();
            int total = 0, validas = 0, invalidas = 0;
            StringBuilder invalidasDetalhes = new StringBuilder();

            for (int i = 0; i < linhas.size(); i++) {
            	String linha = linhas.get(i).trim().toUpperCase();
                total++;

                if (linha.matches("[ATCG]+")) {
                    resultado.add(gerarComplementar(linha));
                    validas++;
                } else {
                    resultado.add("****FITA INVALIDA - " + linha);
                    invalidas++;
                    invalidasDetalhes.append("Fita inválida #").append(i + 1).append(": ").append(linha).append("\n");
                }
            }

            Path saida = Paths.get(arquivo.getParent(), "resultado_" + arquivo.getName());
            Files.write(saida, resultado);

            synchronized (System.out) {
                System.out.println("\nArquivo: " + arquivo.getName());
                System.out.println("Total de fitas: " + total);
                System.out.println("Fitas válidas: " + validas);
                System.out.println("Fitas inválidas: " + invalidas);
                if (invalidas > 0) {
                    System.out.print(invalidasDetalhes);
                }
            }

        } catch (IOException e) {
            System.err.println("Erro ao processar " + arquivo.getName() + ": " + e.getMessage());
        }
    }

    private String gerarComplementar(String fita) {
        StringBuilder sb = new StringBuilder();
        for (char c : fita.toCharArray()) {
            sb.append(complemento.get(c));
        }
        return sb.toString();
    }
}
