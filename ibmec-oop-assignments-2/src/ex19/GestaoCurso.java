package ex19;

import java.util.HashMap;
import java.util.Map;
import java.util.Collection;
import java.util.InputMismatchException;

/**
 * Representa um Aluno com nome e matrícula.
 * A matrícula é o identificador único.
 */
class Aluno {
    private String nome;
    private int matricula;

    public Aluno(String nome, int matricula) {
        this.nome = nome;
        this.matricula = matricula;
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public int getMatricula() {
        return matricula;
    }

    /**
     * Retorna a representação textual do Aluno.
     */
    @Override
    public String toString() {
        return String.format("[Matrícula: %d] Nome: %s", matricula, nome);
    }
}

/**
 * Representa um Curso, que armazena um conjunto de Alunos.
 * Usa um HashMap para garantir que a matrícula seja única e permitir busca rápida.
 */
class Curso {
    private int id;
    private String sigla;
    private String nome;
    
    // Map onde a chave é a Matrícula (Integer) e o valor é o objeto Aluno
    private Map<Integer, Aluno> alunos;

    public Curso(int id, String sigla, String nome) {
        this.id = id;
        this.sigla = sigla;
        this.nome = nome;
        this.alunos = new HashMap<>();
    }

    /**
     * Adiciona um Aluno ao curso.
     * Garante que não haja duplicidade de matrícula.
     * @param aluno O objeto Aluno a ser adicionado.
     */
    public void adicionarAluno(Aluno aluno) {
        // Verifica se a matrícula já existe no Map
        if (alunos.containsKey(aluno.getMatricula())) {
            System.out.printf("[ERRO] Matrícula %d já existe. Aluno %s não adicionado.\n", 
                              aluno.getMatricula(), aluno.getNome());
        } else {
            // Adiciona a matrícula como chave e o objeto Aluno como valor
            alunos.put(aluno.getMatricula(), aluno);
            System.out.printf("[SUCESSO] Aluno %s adicionado ao curso %s.\n", 
                              aluno.getNome(), this.sigla);
        }
    }

    /**
     * Remove um Aluno do curso usando sua matrícula.
     * @param matricula O número de matrícula do aluno a ser removido.
     */
    public void removerAluno(int matricula) {
        Aluno alunoRemovido = alunos.remove(matricula);
        if (alunoRemovido != null) {
            System.out.printf("[SUCESSO] Aluno %s (Matrícula %d) removido do curso %s.\n", 
                              alunoRemovido.getNome(), matricula, this.sigla);
        } else {
            System.out.printf("[ALERTA] Matrícula %d não encontrada no curso %s.\n", 
                              matricula, this.sigla);
        }
    }

    /**
     * Busca um Aluno no curso usando sua matrícula.
     * @param matricula O número de matrícula do aluno a ser buscado.
     * @return O objeto Aluno correspondente ou null se não for encontrado.
     */
    public Aluno buscarAluno(int matricula) {
        // Usa o método get() do Map para buscar pela chave (matrícula)
        return alunos.get(matricula);
    }

    /**
     * Lista todos os alunos matriculados no curso.
     */
    public void listarAlunos() {
        System.out.printf("\n--- ALUNOS DO CURSO %s (%s) ---\n", this.nome, this.sigla);
        
        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado neste curso.");
            return;
        }

        // Obtém todos os objetos Aluno da coleção de valores do Map
        Collection<Aluno> listaAlunos = alunos.values();
        
        for (Aluno aluno : listaAlunos) {
            System.out.println(aluno.toString());
        }
        System.out.printf("Total de Alunos: %d\n", alunos.size());
        System.out.println("----------------------------------------------");
    }

    /**
     * Retorna a representação textual do Curso.
     */
    @Override
    public String toString() {
        return String.format("Curso: %s (%s) | ID: %d", nome, sigla, id);
    }
}

/**
 * Classe Main para testar as funcionalidades do sistema de gestão de alunos.
 */
public class GestaoCurso {
    public static void main(String[] args) {
        System.out.println("=== SISTEMA DE GESTÃO DE ALUNOS E CURSOS ===\n");
        
        // 1. Criação do Curso
        Curso engenhariaSoftware = new Curso(101, "ES", "Engenharia de Software");
        System.out.println("Curso Criado: " + engenhariaSoftware);
        System.out.println("\n----------------------------------------------");

        // 2. Criação de Alunos
        Aluno a1 = new Aluno("João Silva", 12345);
        Aluno a2 = new Aluno("Maria Souza", 54321);
        Aluno a3 = new Aluno("Pedro Alves", 98765);
        
        // Aluno com matrícula que será usada para teste de duplicidade
        Aluno a_duplicado = new Aluno("Ana Paula", 12345); 

        // 3. Adicionar Alunos
        engenhariaSoftware.adicionarAluno(a1);
        engenhariaSoftware.adicionarAluno(a2);
        engenhariaSoftware.adicionarAluno(a3);
        
        // Tenta adicionar o aluno duplicado (deve falhar)
        System.out.println("\n--- Teste de Duplicidade ---");
        engenhariaSoftware.adicionarAluno(a_duplicado); 
        System.out.println("----------------------------\n");


        // 4. Listar Alunos
        engenhariaSoftware.listarAlunos();

        // 5. Buscar Aluno
        System.out.println("\n--- Teste de Busca ---");
        int matriculaBuscaSucesso = 54321;
        Aluno encontrado = engenhariaSoftware.buscarAluno(matriculaBuscaSucesso);
        if (encontrado != null) {
            System.out.printf("[BUSCA SUCESSO] Matrícula %d: %s\n", matriculaBuscaSucesso, encontrado.getNome());
        }

        int matriculaBuscaFalha = 11111;
        Aluno naoEncontrado = engenhariaSoftware.buscarAluno(matriculaBuscaFalha);
        if (naoEncontrado == null) {
            System.out.printf("[BUSCA FALHA] Matrícula %d não encontrada.\n", matriculaBuscaFalha);
        }
        System.out.println("----------------------\n");


        // 6. Remover Aluno
        engenhariaSoftware.removerAluno(54321); // Remove Maria Souza
        engenhariaSoftware.removerAluno(99999); // Tenta remover matrícula inexistente


        // 7. Listar Alunos novamente para verificar a remoção
        engenhariaSoftware.listarAlunos();
    }
}
