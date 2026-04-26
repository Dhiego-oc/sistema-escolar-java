import java.util.Scanner;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Sistema escolar simples que:
 * - Coleta notas de um aluno
 * - Calcula média final
 * - Define situação (Aprovado, Recuperação ou Reprovado)
 * - Armazena os dados em arquivo texto
 */
public class SistemaEscolar {

    private static final int TOTAL_NOTAS = 4;
    private static final double NOTA_MINIMA = 0.0;
    private static final double NOTA_MAXIMA = 10.0;
    private static final double MEDIA_APROVADO = 6.0;
    private static final double MEDIA_RECUPERACAO = 5.0;
    private static final String ARQUIVO_DADOS = "arquivo_de_notas.txt";

   
    private static final Scanner leitor = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("SISTEMA ESCOLAR");
        System.out.println("=".repeat(50) + "\n");

        String nome = coletarNomeAluno();
        double[] notas = coletarNotas();
        double media = calcularMedia(notas);
        String situacao = definirSituacao(media);

        exibirResultados(nome, media, situacao, notas);
        salvarDados(nome, media, situacao);
        exibirAlunosSalvos();

        leitor.close(); 
        System.out.println("\nPrograma finalizado com sucesso.\n");
    }

    private static String coletarNomeAluno() {
        System.out.print("Digite o nome do aluno: ");
        String nome = leitor.nextLine().trim();

        while (nome.isEmpty()) {
            System.out.print("Nome inválido. Tente novamente: ");
            nome = leitor.nextLine().trim();
        }

        return nome;
    }

    private static double[] coletarNotas() {
        double[] notas = new double[TOTAL_NOTAS];

        System.out.println("\nENTRADA DE NOTAS");
        System.out.println("-".repeat(50));

        for (int i = 0; i < TOTAL_NOTAS; i++) {
            notas[i] = obterNotaValida(i + 1);
        }

        return notas;
    }

    private static double obterNotaValida(int numeroNota) {
        double nota = 0;
        boolean entradaValida = false;

        while (!entradaValida) {
            try {
                System.out.print("Digite a nota " + numeroNota + " [0-10]: ");

                if (!leitor.hasNextDouble()) {
                    System.out.println("Erro: digite um número válido.");
                    leitor.nextLine();
                    continue;
                }

                nota = leitor.nextDouble();
                leitor.nextLine(); 

                if (nota < NOTA_MINIMA || nota > NOTA_MAXIMA) {
                    System.out.println("Erro: a nota deve ser entre " + NOTA_MINIMA + " e " + NOTA_MAXIMA + ".");
                    continue;
                }

                entradaValida = true;

            } catch (Exception e) {
                System.out.println("Erro inesperado: " + e.getMessage());
                leitor.nextLine();
            }
        }

        return nota;
    }

    private static double calcularMedia(double[] notas) {
        double soma = 0;
        for (double nota : notas) {
            soma += nota;
        }
        return soma / notas.length;
    }

    private static String definirSituacao(double media) {
        if (media >= MEDIA_APROVADO) {
            return "APROVADO";
        } else if (media >= MEDIA_RECUPERACAO) {
            return "RECUPERAÇÃO";
        } else {
            return "REPROVADO";
        }
    }

    private static void exibirResultados(String nome, double media, String situacao, double[] notas) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("RESULTADO ESCOLAR");
        System.out.println("=".repeat(50) + "\n");

        System.out.printf("Aluno: %s%n", nome);

       
        System.out.print("Notas: ");
        for (int i = 0; i < notas.length; i++) {
            System.out.printf("%.1f", notas[i]);
            if (i < notas.length - 1) {
                System.out.print(" | ");
            }
        }

        System.out.println();
        System.out.printf("Média final: %.2f%n", media);
        System.out.printf("Situação: %s%n", situacao);
        System.out.println("=".repeat(50) + "\n");
    }

    private static void salvarDados(String nome, double media, String situacao) {
        try (PrintWriter gravar = new PrintWriter(new FileWriter(ARQUIVO_DADOS, true))) {
            gravar.printf("Aluno: %-20s | Média: %5.2f | Situação: %s%n", nome, media, situacao);
            System.out.println("Dados salvos com sucesso.");
        } catch (IOException e) {
           
            System.err.println("Erro ao salvar os dados: " + e.getMessage());
        }
    }

    private static void exibirAlunosSalvos() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("ALUNOS SALVOS");
        System.out.println("=".repeat(50) + "\n");

        try (Scanner leitorArquivo = new Scanner(new File(ARQUIVO_DADOS))) {
            int contador = 1;
            while (leitorArquivo.hasNextLine()) {
                System.out.printf("%d. %s%n", contador, leitorArquivo.nextLine());
                contador++;
            }
            System.out.println("=".repeat(50));
        } catch (FileNotFoundException e) {
           
            System.out.println("Arquivo não encontrado. Registre um aluno primeiro.");
            System.out.println("=".repeat(50) + "\n");
        }
    }
}