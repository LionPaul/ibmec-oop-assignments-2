package ex23;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;

/**
 * CLASSE BASE ABSTRATA: Representa um Veículo genérico na locadora.
 * Define atributos comuns e o protocolo de comportamento.
 */
abstract class Veiculo {
    // Status do veículo na locadora
    public enum Status { DISPONIVEL, ALUGADO }

    private String modelo;
    private String placa;
    private String fabricante;
    private double valorDiaria;
    private Status status;

    public Veiculo(String modelo, String placa, String fabricante, double valorDiaria) {
        this.modelo = modelo;
        this.placa = placa;
        this.fabricante = fabricante;
        this.valorDiaria = valorDiaria;
        this.status = Status.DISPONIVEL; // Todo veículo inicia disponível
    }

    /**
     * Exibe as informações comuns do veículo.
     */
    public void exibirDetalhes() {
        System.out.printf("  Modelo: %s\n", this.modelo);
        System.out.printf("  Placa: %s\n", this.placa);
        System.out.printf("  Fabricante: %s\n", this.fabricante);
        System.out.printf("  Diária: R$ %.2f\n", this.valorDiaria);
        System.out.printf("  Status: %s\n", this.status);
    }
    
    // Método para ser sobrescrito pelas subclasses (Polimorfismo)
    public abstract void exibirDetalhesEspecificos();

    // Getters e Setters
    public String getPlaca() { return placa; }
    public double getValorDiaria() { return valorDiaria; }
    public Status getStatus() { return status; }
    
    public void setStatus(Status status) {
        this.status = status;
    }
}

/**
 * SUBCLASSE: Carro. Herda de Veiculo.
 */
class Carro extends Veiculo {
    private int quantidadePortas;

    public Carro(String modelo, String placa, String fabricante, double valorDiaria, int quantidadePortas) {
        super(modelo, placa, fabricante, valorDiaria);
        this.quantidadePortas = quantidadePortas;
    }

    @Override
    public void exibirDetalhesEspecificos() {
        System.out.printf("  Tipo: Carro | Portas: %d\n", this.quantidadePortas);
    }
}

/**
 * SUBCLASSE: Moto. Herda de Veiculo.
 */
class Moto extends Veiculo {
    private int cilindradas;

    public Moto(String modelo, String placa, String fabricante, double valorDiaria, int cilindradas) {
        super(modelo, placa, fabricante, valorDiaria);
        this.cilindradas = cilindradas;
    }

    @Override
    public void exibirDetalhesEspecificos() {
        System.out.printf("  Tipo: Moto | Cilindradas: %dcc\n", this.cilindradas);
    }
}

/**
 * Representa um Cliente da locadora. O CPF é o identificador único.
 */
class Cliente {
    private String cpf;
    private String nome;

    public Cliente(String cpf, String nome) {
        this.cpf = cpf;
        this.nome = nome;
    }

    // Getters
    public String getCpf() { return cpf; }
    public String getNome() { return nome; }

    @Override
    public String toString() {
        return String.format("%s (CPF: %s)", nome, cpf);
    }
    
    // Sobrescreve equals e hashCode para garantir que o CPF seja o identificador único
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(cpf, cliente.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }
}

/**
 * Representa uma Locação.
 * Possui referências para o Cliente e o Veículo e calcula o valor final.
 */
class Locacao {
    private static int contadorId = 1;
    private int id;
    private Cliente cliente;
    private Veiculo veiculo;
    private LocalDate dataLocacao;
    private LocalDate dataDevolucao;
    private long dias;
    private double valorFinal;
    
    private static final DateTimeFormatter FORMATADOR_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Construtor da Locação.
     * @param cliente O cliente que está alugando.
     * @param veiculo O veículo alugado.
     * @param dataLocacao A data de início da locação.
     */
    public Locacao(Cliente cliente, Veiculo veiculo, LocalDate dataLocacao) {
        this.id = contadorId++;
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.dataLocacao = dataLocacao;
        this.veiculo.setStatus(Veiculo.Status.ALUGADO); // Altera status do veículo
        
        // Inicializa devolução e valores como zero/null
        this.dataDevolucao = null; 
        this.dias = 0;
        this.valorFinal = 0.0;
        
        System.out.printf("[SUCESSO] Locação #%d registrada para %s.\n", this.id, this.cliente.getNome());
    }

    /**
     * Registra a devolução do veículo, calcula os dias e o valor final.
     * @param dataDevolucao A data real de devolução.
     */
    public void registrarDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
        
        // Calcula a diferença de dias (incluindo o dia de locação)
        this.dias = ChronoUnit.DAYS.between(this.dataLocacao, this.dataDevolucao);
        
        // Se a diferença for negativa (devolução antes da locação), ajusta para 1 dia mínimo
        if (this.dias <= 0) {
            this.dias = 1; 
        } else {
             // Adiciona 1 dia para contar o dia da locação
             this.dias += 1; 
        }
        
        this.valorFinal = this.dias * this.veiculo.getValorDiaria();
        this.veiculo.setStatus(Veiculo.Status.DISPONIVEL); // Altera status do veículo para disponível
        
        System.out.printf("[SUCESSO] Devolução Locação #%d registrada. Total de dias: %d.\n", this.id, this.dias);
    }

    /**
     * Exibe os detalhes completos da locação.
     */
    public void exibirDetalhes() {
        System.out.printf("\n--- LOCAÇÃO #%d ---\n", this.id);
        System.out.println("CLIENTE:");
        System.out.printf("  Nome: %s\n", this.cliente.getNome());
        System.out.printf("  CPF: %s\n", this.cliente.getCpf());
        
        System.out.println("VEÍCULO ALUGADO:");
        this.veiculo.exibirDetalhes();
        this.veiculo.exibirDetalhesEspecificos();
        
        System.out.println("DETALHES DA TRANSAÇÃO:");
        System.out.printf("  Data Locação: %s\n", this.dataLocacao.format(FORMATADOR_DATA));
        
        if (this.dataDevolucao != null) {
            System.out.printf("  Data Devolução: %s\n", this.dataDevolucao.format(FORMATADOR_DATA));
            System.out.printf("  Total de Dias: %d\n", this.dias);
            System.out.printf("  Valor Diária: R$ %.2f\n", this.veiculo.getValorDiaria());
            System.out.printf("  VALOR FINAL: R$ %.2f\n", this.valorFinal);
        } else {
            System.out.println("  Status: Em andamento. Devolução pendente.");
        }
        System.out.println("--------------------");
    }

    // Getters
    public Cliente getCliente() { return cliente; }
    public Veiculo getVeiculo() { return veiculo; }
    public int getId() { return id; }
}

/**
 * Classe principal para gerenciar o sistema e executar os testes.
 */
public class SistemaLocadora {

    // Coleções para armazenamento dos dados
    private List<Cliente> clientes;
    private List<Veiculo> veiculos;
    private List<Locacao> locacoes;

    public SistemaLocadora() {
        this.clientes = new ArrayList<>();
        this.veiculos = new ArrayList<>();
        this.locacoes = new ArrayList<>();
    }

    /**
     * Menu principal do sistema. (Funcionalidade extra para gerenciar o sistema)
     */
    public void iniciarSistema() {
        // Implementação do menu removida para focar no cenário de teste solicitado.
        // O cenário de teste está implementado no método main.
    }

    /**
     * Cadastra um novo veículo no sistema.
     * @param veiculo O objeto Carro ou Moto.
     */
    public void cadastrarVeiculo(Veiculo veiculo) {
        // Verifica se a placa já existe
        if (veiculos.stream().anyMatch(v -> v.getPlaca().equals(veiculo.getPlaca()))) {
            System.out.printf("[ALERTA] Veículo com placa %s já cadastrado.\n", veiculo.getPlaca());
            return;
        }
        this.veiculos.add(veiculo);
        System.out.printf("[CADASTRO] Veículo %s cadastrado com sucesso.\n", veiculo.getPlaca());
    }

    /**
     * Cadastra um novo cliente no sistema.
     * @param cliente O objeto Cliente.
     */
    public void cadastrarCliente(Cliente cliente) {
        // Verifica se o CPF já existe
        if (clientes.stream().anyMatch(c -> c.getCpf().equals(cliente.getCpf()))) {
            System.out.printf("[ALERTA] Cliente com CPF %s já cadastrado.\n", cliente.getCpf());
            return;
        }
        this.clientes.add(cliente);
        System.out.printf("[CADASTRO] Cliente %s cadastrado com sucesso.\n", cliente.getNome());
    }

    /**
     * Registra uma nova locação no sistema.
     * @param cliente Cliente que irá alugar.
     * @param veiculo Veículo a ser alugado.
     * @param dataLocacao Data de início da locação.
     * @return O objeto Locacao criado ou null.
     */
    public Locacao registrarLocacao(Cliente cliente, Veiculo veiculo, LocalDate dataLocacao) {
        if (veiculo.getStatus() == Veiculo.Status.ALUGADO) {
            System.out.printf("[ERRO] Veículo %s já está alugado.\n", veiculo.getPlaca());
            return null;
        }
        
        Locacao novaLocacao = new Locacao(cliente, veiculo, dataLocacao);
        this.locacoes.add(novaLocacao);
        return novaLocacao;
    }

    /**
     * Registra a devolução de uma locação.
     * @param locacaoId ID da locação a ser devolvida.
     * @param dataDevolucao Data de devolução.
     */
    public void registrarDevolucao(int locacaoId, LocalDate dataDevolucao) {
        Locacao locacao = locacoes.stream()
            .filter(l -> l.getId() == locacaoId)
            .findFirst()
            .orElse(null);
            
        if (locacao == null) {
            System.out.printf("[ERRO] Locação #%d não encontrada.\n", locacaoId);
            return;
        }
        
        // Verifica se a devolução já foi registrada
        if (locacao.getVeiculo().getStatus() == Veiculo.Status.DISPONIVEL && locacao.dataDevolucao != null) {
             System.out.printf("[ALERTA] Locação #%d já foi devolvida.\n", locacaoId);
             return;
        }

        locacao.registrarDevolucao(dataDevolucao);
    }

    /**
     * Lista todas as locações de um cliente específico.
     * @param cpf CPF do cliente.
     */
    public void listarLocacoesCliente(String cpf) {
        List<Locacao> locacoesCliente = locacoes.stream()
            .filter(l -> l.getCliente().getCpf().equals(cpf))
            .toList();

        System.out.printf("\n--- LISTA DE LOCAÇÕES DO CLIENTE CPF %s (%d Locação(ões)) ---\n", cpf, locacoesCliente.size());
        
        if (locacoesCliente.isEmpty()) {
            System.out.println("Nenhuma locação encontrada para este CPF.");
            return;
        }
        
        for (Locacao locacao : locacoesCliente) {
            locacao.exibirDetalhes();
        }
        System.out.println("----------------------------------------------------------");
    }

    /**
     * Método principal para demonstrar o cenário de teste solicitado.
     */
    public static void main(String[] args) {
        SistemaLocadora sistema = new SistemaLocadora();
        
        System.out.println("=== SISTEMA DE LOCADORA - DEMONSTRAÇÃO ===");
        
        // 1. Configuração Inicial
        
        // Cliente único para o teste
        Cliente clienteTeste = new Cliente("333.444.555-66", "João da Silva");
        sistema.cadastrarCliente(clienteTeste);
        
        // Veículos de teste
        Carro carroTeste = new Carro("Sedan X", "ABC-1234", "Fabricante A", 95.00, 4);
        Moto motoTeste = new Moto("Esportiva Y", "MNO-5678", "Fabricante B", 70.00, 600);
        sistema.cadastrarVeiculo(carroTeste);
        sistema.cadastrarVeiculo(motoTeste);

        // Datas de Locação/Devolução para o teste
        LocalDate hoje = LocalDate.now();
        LocalDate locacaoCarro = hoje.plusDays(1); // Amanhã
        LocalDate devolucaoCarro = hoje.plusDays(5); // Daqui a 5 dias
        LocalDate locacaoMoto = hoje.plusDays(10);
        LocalDate devolucaoMoto = hoje.plusDays(12);
        
        System.out.println("\n----------------------------------------------------------");
        System.out.println(">>> CENÁRIO 1: LOCAÇÃO E DEVOLUÇÃO DO CARRO");
        System.out.println("----------------------------------------------------------");
        
        // 2. Locação do Carro (João da Silva)
        Locacao locacao1 = sistema.registrarLocacao(clienteTeste, carroTeste, locacaoCarro);
        if (locacao1 != null) {
            System.out.println("\n--- DETALHES ANTES DA DEVOLUÇÃO ---");
            locacao1.exibirDetalhes();
            
            // 3. Devolução do Carro
            sistema.registrarDevolucao(locacao1.getId(), devolucaoCarro);
            
            System.out.println("\n--- DETALHES APÓS A DEVOLUÇÃO ---");
            locacao1.exibirDetalhes();
        }

        System.out.println("\n----------------------------------------------------------");
        System.out.println(">>> CENÁRIO 2: LOCAÇÃO E DEVOLUÇÃO DA MOTO");
        System.out.println("----------------------------------------------------------");

        // 4. Locação da Moto (João da Silva)
        Locacao locacao2 = sistema.registrarLocacao(clienteTeste, motoTeste, locacaoMoto);
        if (locacao2 != null) {
            System.out.println("\n--- DETALHES ANTES DA DEVOLUÇÃO ---");
            locacao2.exibirDetalhes();
            
            // 5. Devolução da Moto
            sistema.registrarDevolucao(locacao2.getId(), devolucaoMoto);
            
            System.out.println("\n--- DETALHES APÓS A DEVOLUÇÃO ---");
            locacao2.exibirDetalhes();
        }

        // 6. Listar as locações do cliente
        sistema.listarLocacoesCliente(clienteTeste.getCpf());
    }
}
