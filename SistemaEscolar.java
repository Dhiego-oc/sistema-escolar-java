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
    public static void main(String[]args) {
      Scanner leitor = new Scanner (System.in);

       // Sistema simples para cadastro e cálculo de notas de alunos
       System.out.println("--- SISTEMA ESCOLAR---");
        System.out.print("Digite o nome do aluno: ");
        String nome = leitor.nextLine();

        // Acumulador utilizado para cálculo da média final
        double somarNotas = 0;
        int totalNotas = 4;

        // Loop responsável pela entrada e validação das notas do aluno
        for (int i = 1; i <= totalNotas; i++) {
            System.out.print("Digite a nota: " + i + ": ");
            double nota = leitor.nextDouble();
        
        // Garante que a nota esteja entre 0 e 10; caso contrário, solicita nova entrada  
        while (nota < 0 ||  nota > 10) {
            System.out.print("Nota inválida. Digite um valor entre 0 e 10: ");
            nota = leitor.nextDouble();
        }
        somarNotas += nota;
    }
        // Calcula a média final do aluno
        double media  = somarNotas / totalNotas;
        
        System.out.println("\nOlá, " + nome + "!");
        System.out.printf("A média final é: %.1f%n", media);

        // Define a situação com base na média final 
        if (media >= 7.0) {
            System.out.println("O aluno está: APROVADO");
        } 
        else if (media >= 5.0) {
            System.out.println("O aluno está de: RECUPERAÇÃO");
        }
        else {
            System.out.println("O aluno está: REPROVADO");
        } 

        // Salva os dados do aluno em arquivo texto (modo append)
        try { 
            FileWriter arquivo = new FileWriter("arquivo_de_notas.txt", true);
            PrintWriter gravar = new PrintWriter(arquivo);

            gravar.printf("Aluno: %s | Média: %.1f%n", nome, media);
            gravar.close();

           System.out.println("\nDados salvos com sucesso no arquivo!"); 
        } catch (IOException e) {
            System.err.println("Erro ao salvar os seus dados: " + e.getMessage());
        }
        leitor.close();
        
        System.out.println("---LISTA DE ALUNOS NO ARQUIVO---");
        exibirAlunosSalvos();
    }
        // Exibe todos os registros armazenados no arquivo de notas
      public static void exibirAlunosSalvos () {
        try { 
            File arquivo = new File ("arquivo_de_notas.txt");
            Scanner leitorArquivo = new Scanner (arquivo);
        
        
        while (leitorArquivo.hasNextLine()) {
             String linha = leitorArquivo.nextLine();
             System.out.println(linha);
        }

        leitorArquivo.close();
      } catch (FileNotFoundException e) {
        System.out.println("Arquivo não existe. Registre um aluno primeiro.");
           
        } 
    }
}
