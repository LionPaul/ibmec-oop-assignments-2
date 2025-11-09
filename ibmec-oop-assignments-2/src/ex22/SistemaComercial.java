package ex22;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


class Produto {
    private String nome;
    private double preco;
    private int quantidade;

    public Produto(String nome, double preco, int quantidade) {
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public double getPreco() {
        return preco;
    }

    public String toString() {
        return String.format("Nome: %s | Preço: R$ %.2f | Estoque: %d un.", nome, preco, quantidade);
    }
}

public class SistemaComercial {

    private List<Produto> listaProdutos;
    private Scanner scanner;

    public SistemaComercial() {
        this.listaProdutos = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public void iniciarSistema() {
        int opcao = -1;
        System.out.println("=== BEM-VINDO AO SISTEMA COMERCIAL ===");
        
        while (opcao != 0) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Cadastrar Produtos");
            System.out.println("2. Acessar Produto por Índice");
            System.out.println("3. Calcular Preço Médio");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine(); 

                switch (opcao) {
                    case 1:
                        cadastrarProdutos();
                        break;
                    case 2:
                        acessarProdutoPorIndice();
                        break;
                    case 3:
                        calcularPrecoMedio();
                        break;
                    case 0:
                        System.out.println("\nSistema encerrado. Obrigado!");
                        break;
                    default:
                        System.out.println("[ALERTA] Opção inválida. Tente novamente.");
                }
            } catch (InputMismatchException e) {
          
                System.err.println("\n[ERRO: InputMismatchException] Entrada inválida. Por favor, digite um número correspondente à opção do menu.");
                scanner.nextLine();
                opcao = -1;
            } catch (Exception e) {
                System.err.println("\n[ERRO: Exception Genérica] Ocorreu um erro inesperado: " + e.getMessage());
            }
        }
    }


    private void cadastrarProdutos() {
        System.out.println("\n--- CADASTRO DE PRODUTOS ---");
        boolean continuar = true;

        while (continuar) {
            try {
                System.out.print("Nome do Produto (ou 'sair' para finalizar): ");
                String nome = scanner.nextLine();
                if (nome.equalsIgnoreCase("sair")) {
                    continuar = false;
                    break;
                }

                System.out.print("Preço do Produto: R$ ");
    
                double preco = scanner.nextDouble();

                System.out.print("Quantidade em Estoque: ");
            
                int quantidade = scanner.nextInt();
                scanner.nextLine(); 

                Produto novoProduto = new Produto(nome, preco, quantidade);
                listaProdutos.add(novoProduto);
                System.out.printf("[SUCESSO] Produto '%s' cadastrado. (Total: %d)\n", nome, listaProdutos.size());

            } catch (InputMismatchException e) {
                System.err.println("\n[ERRO: InputMismatchException] Entrada de preço ou quantidade inválida. Use apenas números.");
                scanner.nextLine(); 
            }
        }
    }

    private void acessarProdutoPorIndice() {
        if (listaProdutos.isEmpty()) {
            System.out.println("[ALERTA] Nenhum produto cadastrado para acessar.");
            return;
        }

        System.out.println("\n--- ACESSAR PRODUTO POR ÍNDICE ---");
        System.out.printf("A lista tem %d produtos (índices válidos: 0 a %d).\n", 
                            listaProdutos.size(), listaProdutos.size() - 1);
        System.out.print("Digite o índice do produto que deseja acessar: ");

        try {
            int indice = scanner.nextInt();
            scanner.nextLine(); 

            Produto produtoEncontrado = listaProdutos.get(indice); 
            
            System.out.printf("\n[ENCONTRADO no Índice %d]: %s\n", indice, produtoEncontrado.toString());

        } catch (InputMismatchException e) {
            System.err.println("\n[ERRO: InputMismatchException] Índice inválido. Por favor, digite um número inteiro.");
            scanner.nextLine();
        } catch (IndexOutOfBoundsException e) {
            System.err.printf("\n[ERRO: IndexOutOfBoundsException] Índice fora do limite. Digite um valor entre 0 e %d.\n", listaProdutos.size() - 1);
        }
    }

    private void calcularPrecoMedio() {
        System.out.println("\n--- CÁLCULO DO PREÇO MÉDIO ---");

        try {
            if (listaProdutos.isEmpty()) {
                throw new ArithmeticException("Impossível calcular média de uma lista vazia.");
            }
            
            double somaPrecos = 0;
            for (Produto produto : listaProdutos) {
                somaPrecos += produto.getPreco();
            }

            double precoMedio = somaPrecos / listaProdutos.size(); 
            
            System.out.printf("[RESULTADO] Preço Médio dos %d produtos: R$ %.2f\n", 
                                listaProdutos.size(), precoMedio);

        } catch (ArithmeticException e) {
            System.err.println("\n[ERRO: ArithmeticException] " + e.getMessage() + " Cadastre produtos primeiro.");
        }
    }

    public static void main(String[] args) {
        SistemaComercial sistema = new SistemaComercial();
        sistema.iniciarSistema();
    }
}
