package ex15;

import java.util.ArrayList;
import java.util.List;


public class SistemaAutoria {
    public static void main(String[] args) {
        System.out.println("--- DEMONSTRAÇÃO DO SISTEMA DE AUTORIA (POO - Relacionamento 1-*) ---\n");
        
        Autor machado = new Autor("Machado de Assis", "111.111.111-11");
        Autor clarice = new Autor("Clarice Lispector", "222.222.222-22");

        Livro domCasmurro = new Livro("Dom Casmurro (Romance Clássico)", 380);
        Livro capitu = new Livro("Memórias Póstumas de Brás Cubas (Sátira)", 290);
        Livro horaDaEstrela = new Livro("A Hora da Estrela (Ficção Filosófica)", 120);

        machado.adicionarLivro(domCasmurro);
        machado.adicionarLivro(capitu);
        clarice.adicionarLivro(horaDaEstrela);

        System.out.println(">>> Resumo das Obras de: " + machado.getNome());
        machado.imprimirResumoObras();

        System.out.println("\n------------------------------------------------------------");

        System.out.println(">>> Resumo das Obras de: " + clarice.getNome());
        clarice.imprimirResumoObras();

        System.out.println("\n------------------------------------------------------------");

        System.out.println("Verificando a integridade do relacionamento bidirecional:");
        System.out.println("  Livro: '" + domCasmurro.getDescricao() + "' -> Autor: " + domCasmurro.getAutor().getNome());
        System.out.println("  Livro: '" + horaDaEstrela.getDescricao() + "' -> Autor: " + horaDaEstrela.getAutor().getNome());
    }
}

class Autor {
    private String nome;
    private String cpf;

    private List<Livro> obras;

    public Autor(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
        this.obras = new ArrayList<>();
    }

 
    public void adicionarLivro(Livro livro) {
        this.obras.add(livro);
        livro.setAutor(this);
    }


    public void imprimirResumoObras() {
        if (obras.isEmpty()) {
            System.out.println("  Nenhuma obra registrada para este autor.");
            return;
        }

        System.out.println("  Total de Obras Registradas: " + obras.size());
        for (int i = 0; i < obras.size(); i++) {
            Livro livro = obras.get(i);
            System.out.printf("  [%d] Título: %s | Páginas: %d\n",
                (i + 1), livro.getDescricao(), livro.getNumeroPaginas());
        }
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }
}

class Livro {
    private String descricao;
    private int numeroPaginas;

    private Autor autor;

    public Livro(String descricao, int numeroPaginas) {
        this.descricao = descricao;
        this.numeroPaginas = numeroPaginas;
        this.autor = null; 
    }

    public String getDescricao() {
        return descricao;
    }

    public int getNumeroPaginas() {
        return numeroPaginas;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }
}
