package ex18;

import java.util.ArrayList;
import java.util.List;

abstract class Carro {
  
    public static final int LIMITE_MAX_KM = 50000;

    private String descricao;
    private double valor;
    private int quilometragemAtual;


    public Carro(String descricao, double valor, int quilometragemAtual) {
        this.descricao = descricao;
        this.valor = valor;
        this.quilometragemAtual = quilometragemAtual;
    }

  
    public abstract double calcularDiaria();
    public abstract void verificarKM();

    public void exibirInformacoes() {
        System.out.printf("  Descrição: %s\n", this.descricao);
        System.out.printf("  Valor do Carro: R$ %.2f\n", this.valor);
        System.out.printf("  KM Atual: %d / %d (Limite Máximo)\n", this.quilometragemAtual, LIMITE_MAX_KM);
        System.out.printf("  Diária Calculada: R$ %.2f\n", this.calcularDiaria());
        this.verificarKM();
    }


    public double getValor() {
        return valor;
    }

    public int getQuilometragemAtual() {
        return quilometragemAtual;
    }
}

class CarroBasico extends Carro {
    public CarroBasico(String descricao, double valor, int quilometragemAtual) {
        super(descricao, valor, quilometragemAtual);
    }

    public double calcularDiaria() {

        return super.getValor() / 2000.0;
    }

  
    public void verificarKM() {
        if (super.getQuilometragemAtual() > LIMITE_MAX_KM) {
            System.out.println("  ** ALERTA **: Carro Básico deve ser enviado para REVISÃO!");
        } else {
            System.out.println("  Status KM: OK.");
        }
    }
}

class CarroLuxo extends Carro {
    private static final double TETO_DIARIA = 190.00;

    public CarroLuxo(String descricao, double valor, int quilometragemAtual) {
        super(descricao, valor, quilometragemAtual);
    }


    public double calcularDiaria() {
        double diariaCalculada = super.getValor() / 1000.0;
        
        return Math.min(diariaCalculada, TETO_DIARIA);
    }


    public void verificarKM() {
        if (super.getQuilometragemAtual() > LIMITE_MAX_KM) {
            System.out.println("  ** ALERTA **: Carro Luxo deve ser enviado para CONCESSIONÁRIA DO GRUPO!");
        } else {
            System.out.println("  Status KM: OK.");
        }
    }
}

public class GestaoLocadora {
    public static void main(String[] args) {
        System.out.println("--- GESTÃO DE CARROS DA LOCADORA (Herança e Polimorfismo) ---\n");
       
  
        Carro carroBasico1 = new CarroBasico("VW Gol 1.0", 20000.00, 35000); 
        
        Carro carroBasico2 = new CarroBasico("Fiat Uno", 80000.00, 52000); 
        
        Carro carroLuxo1 = new CarroLuxo("Mercedes C180", 250000.00, 40000); 
        
        Carro carroLuxo2 = new CarroLuxo("BMW X1", 100000.00, 60000); 

        List<Carro> frota = new ArrayList<>();
        frota.add(carroBasico1);
        frota.add(carroBasico2);
        frota.add(carroLuxo1);
        frota.add(carroLuxo2);

        System.out.println(">>> RESUMO E VERIFICAÇÃO DA FROTA:");
 
        for (Carro carro : frota) {
            System.out.println("\n============================================================");
            carro.exibirInformacoes();
        }
        System.out.println("============================================================");
    }
}