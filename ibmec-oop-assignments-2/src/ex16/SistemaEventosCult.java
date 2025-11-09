package ex16;

import java.util.ArrayList;
import java.util.List;


public class SistemaEventosCult {
    public static void main(String[] args) {
        System.out.println("--- DEMONSTRAÇÃO DO SISTEMA DE EVENTOS CULTURAIS (POO - Relacionamento M:N) ---\n");
        
        Artista mariaBethania = new Artista("Maria Bethânia", 77);
        Artista caetanoVeloso = new Artista("Caetano Veloso", 81);
        Artista gilbertoGil = new Artista("Gilberto Gil", 82);

        Evento rockInRio = new Evento("Rock in Rio", "Rio de Janeiro", "05/09/2025");
        Evento festivalJazz = new Evento("Festival de Jazz", "São Paulo", "20/11/2025");
        Evento noiteDeMPB = new Evento("Noite de MPB Acústico", "Salvador", "15/12/2025");

        rockInRio.adicionarArtista(caetanoVeloso);
        rockInRio.adicionarArtista(gilbertoGil);

        festivalJazz.adicionarArtista(caetanoVeloso);
        
        noiteDeMPB.adicionarArtista(mariaBethania);
        noiteDeMPB.adicionarArtista(gilbertoGil);

  
        List<Evento> eventos = List.of(rockInRio, festivalJazz, noiteDeMPB);
        
        imprimirResumoGeral(eventos);

        System.out.println("\n------------------------------------------------------------");

        System.out.println(">>> Resumo dos Eventos Participados por Artista:");
        mariaBethania.imprimirEventos();
        caetanoVeloso.imprimirEventos();
    }

    private static void imprimirResumoGeral(List<Evento> eventos) {
        System.out.println(">>> RESUMO GERAL DE EVENTOS <<<");
        for (Evento evento : eventos) {
            System.out.println("\n------------------------------------------------------------");
            System.out.printf("EVENTO: %s\n", evento.getNome());
            System.out.printf("  Local: %s | Data: %s\n", evento.getLocal(), evento.getData());
            
            if (evento.getArtistasParticipantes().isEmpty()) {
                System.out.println("  Artistas: Nenhum artista confirmado.");
            } else {
                System.out.println("  Artistas Participantes (" + evento.getArtistasParticipantes().size() + "):");
                for (int i = 0; i < evento.getArtistasParticipantes().size(); i++) {
                    Artista artista = evento.getArtistasParticipantes().get(i);
                    System.out.printf("    - %s (Idade: %d)\n", artista.getNome(), artista.getIdade());
                }
            }
        }
    }
}


class Artista {
    private String nome;
    private int idade;
    private List<Evento> eventosParticipados;

    public Artista(String nome, int idade) {
        this.nome = nome;
        this.idade = idade;
        this.eventosParticipados = new ArrayList<>();
    }

  
    public void adicionarEvento(Evento evento) {
        if (!eventosParticipados.contains(evento)) {
            this.eventosParticipados.add(evento);
        }
    }

  
    public void imprimirEventos() {
        System.out.printf("  %s (%d anos) participará de %d evento(s):\n", 
            nome, idade, eventosParticipados.size());
        for (Evento evento : eventosParticipados) {
            System.out.printf("    -> %s em %s\n", evento.getNome(), evento.getData());
        }
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }
    
    public List<Evento> getEventosParticipados() {
        return eventosParticipados;
    }
}


class Evento {
    private String nome;
    private String local;
    private String data;
  
    private List<Artista> artistasParticipantes;

    public Evento(String nome, String local, String data) {
        this.nome = nome;
        this.local = local;
        this.data = data;
        this.artistasParticipantes = new ArrayList<>();
    }

    public void adicionarArtista(Artista artista) {
        this.artistasParticipantes.add(artista);
        
        artista.adicionarEvento(this);
    }

    public String getNome() {
        return nome;
    }

    public String getLocal() {
        return local;
    }

    public String getData() {
        return data;
    }

    public List<Artista> getArtistasParticipantes() {
        return artistasParticipantes;
    }
}
