package ex21;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

class Paciente {
    private String cpf;
    private String nome;

    public Paciente(String cpf, String nome) {
        this.cpf = cpf;
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return String.format("%s (CPF: %s)", nome, cpf);
    }
}


class Consulta {
    private LocalDateTime dataHora;
    private Paciente paciente;
    private static final long DURACAO_MINUTOS = 45;
    
    private static final DateTimeFormatter FORMATADOR = 
        DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm");

    public Consulta(LocalDateTime dataHora, Paciente paciente) {
        this.dataHora = dataHora;
        this.paciente = paciente;
    }

    public long calcularDiasRestantes() {
        LocalDateTime agora = LocalDateTime.now(); 
        
        if (dataHora.isBefore(agora)) {
            return 0; 
        }
        
        return ChronoUnit.DAYS.between(agora.toLocalDate(), dataHora.toLocalDate());
    }


    public String calcularHoraTermino() {
        LocalDateTime termino = this.dataHora.plusMinutes(DURACAO_MINUTOS);

        return termino.format(DateTimeFormatter.ofPattern("HH:mm"));
    }


    public void reagendar(int dias) {
        LocalDateTime novaData = LocalDateTime.now().plusDays(dias).withHour(this.dataHora.getHour()).withMinute(this.dataHora.getMinute());
        
        this.dataHora = novaData;
        System.out.printf("[SUCESSO] Consulta reagendada para daqui a %d dias.\n", dias);
    }

    public void exibirInformacoes() {
        System.out.println("--- DETALHES DA CONSULTA ---");
        System.out.printf("  Paciente: %s\n", this.paciente.toString());
        System.out.printf("  Início Agendado: %s\n", this.dataHora.format(FORMATADOR));
        System.out.printf("  Hora Prevista de Término: %s\n", this.calcularHoraTermino());
        
        long diasFaltantes = this.calcularDiasRestantes();
        if (diasFaltantes > 0) {
            System.out.printf("  Faltam: %d dia(s) para a consulta.\n", diasFaltantes);
        } else if (diasFaltantes == 0 && this.dataHora.isAfter(LocalDateTime.now())) {
             System.out.println("  Falta menos de 1 dia para a consulta (hoje).");
        } else {
            System.out.println("  Status: Consulta já realizada.");
        }
        System.out.println("----------------------------");
    }
}


public class GestaoConsultas {

    public static void main(String[] args) {
        System.out.println("=== SISTEMA DE GESTÃO DE CONSULTAS ===");

        Paciente p1 = new Paciente("123.456.789-00", "Carlos Eduardo");
        Paciente p2 = new Paciente("987.654.321-11", "Ana Júlia");
        System.out.printf("\n[CADASTRO] Paciente 1 registrado: %s\n", p1.getNome());
        System.out.printf("[CADASTRO] Paciente 2 registrado: %s\n", p2.getNome());

        LocalDateTime data1 = LocalDateTime.now()
                                          .plusDays(3)
                                          .withHour(10)
                                          .withMinute(30)
                                          .withSecond(0)
                                          .withNano(0);
        Consulta c1 = new Consulta(data1, p1);

        LocalDateTime data2 = LocalDateTime.now()
                                          .plusDays(7)
                                          .withHour(14)
                                          .withMinute(0)
                                          .withSecond(0)
                                          .withNano(0);
        Consulta c2 = new Consulta(data2, p2);
        
        System.out.println("\n------------------------------------------------");
        System.out.println(">>> PRIMEIRA CONSULTA (P1) - AGENDAMENTO INICIAL");
        c1.exibirInformacoes();
        
        System.out.println("\n------------------------------------------------");
        System.out.println(">>> SEGUNDA CONSULTA (P2) - AGENDAMENTO INICIAL");
        c2.exibirInformacoes();
        
  
        System.out.println("\n=================================================");
        System.out.println(">>> TESTE DE REAGENDAMENTO (P1)");
        System.out.println("  Reagendando consulta de Carlos Eduardo para daqui a 7 dias.");
 
        c1.reagendar(7);
        
        c1.exibirInformacoes();
        System.out.println("=================================================");
    }
}