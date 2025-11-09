package ex17;

import java.util.ArrayList;
import java.util.List;

class Produto {
 
    private String nome;
    private double precoBase;

    public Produto(String nome, double precoBase) {
        this.nome = nome;
        this.precoBase = precoBase;
    }

    public double calcularPrecoFinal() {
        return this.precoBase;
    }

    public void exibirInformacoes() {
        System.out.printf("  Nome: %s\n", this.nome);
        System.out.printf("  Preço Base: R$ %.2f\n", this.precoBase);
        System.out.printf("  Preço Final: R$ %.2f (Regra Padrão)\n", this.calcularPrecoFinal());
    }

    public String getNome() {
        return nome;
    }

    public double getPrecoBase() {
        return precoBase;
    }
}

class ProdutoImportado extends Produto {
    private double taxaImportacao; // Taxa adicional, em valor monetário

    public ProdutoImportado(String nome, double precoBase, double taxaImportacao) {
        super(nome, precoBase);
        this.taxaImportacao = taxaImportacao;
    }

    public double calcularPrecoFinal() {
        return super.getPrecoBase() + this.taxaImportacao;
    }

    public void exibirInformacoes() {
        System.out.printf("  Nome: %s (IMPORTADO)\n", super.getNome());
        System.out.printf("  Preço Base: R$ %.2f\n", super.getPrecoBase());
        System.out.printf("  Taxa de Importação: R$ %.2f\n", this.taxaImportacao);
        System.out.printf("  Preço Final: R$ %.2f (Base + Taxa Fixa)\n", this.calcularPrecoFinal());
    }
}


class ProdutoComServico extends Produto {
    private double taxaServico; 

 
    public ProdutoComServico(String nome, double precoBase, double taxaServico) {
        super(nome, precoBase);
        this.taxaServico = taxaServico;
    }


    public double calcularPrecoFinal() {
        return super.getPrecoBase() + (super.getPrecoBase() * this.taxaServico);
    }


    public void exibirInformacoes() {
        System.out.printf("  Nome: %s (COM SERVIÇO)\n", super.getNome());
        System.out.printf("  Preço Base: R$ %.2f\n", super.getPrecoBase());
        System.out.printf("  Taxa de Serviço: %.1f%%\n", this.taxaServico * 100);
        System.out.printf("  Preço Final: R$ %.2f (Base + %% de Serviço)\n", this.calcularPrecoFinal());
    }
}


public class GestaoProdutos {
    public static void main(String[] args) {
        System.out.println("--- DEMONSTRAÇÃO DE GESTÃO DE PRODUTOS (Herança e Polimorfismo) ---\n");
        
        Produto produtoNormal = new Produto("Caderno Universitário", 25.00);
        
        Produto produtoImportado = new ProdutoImportado("Livro Raro", 80.00, 15.00); 
        
        Produto produtoComServico = new ProdutoComServico("Software Pro", 500.00, 0.10); 

        List<Produto> listaDeProdutos = new ArrayList<>();
        listaDeProdutos.add(produtoNormal);
        listaDeProdutos.add(produtoImportado);
        listaDeProdutos.add(produtoComServico);
        
        System.out.println(">>> RESUMO E CÁLCULO DE PREÇOS FINAIS:");
        double totalGeral = 0.0;
        
        for (Produto produto : listaDeProdutos) {
            System.out.println("\n------------------------------------------------------------");
            produto.exibirInformacoes();
            totalGeral += produto.calcularPrecoFinal();
        }

        System.out.println("\n============================================================");
        System.out.printf("VALOR TOTAL GERAL DE TODOS OS PRODUTOS: R$ %.2f\n", totalGeral);
        System.out.println("============================================================");
    }
}