package ex20;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Objects;


class Aluno {
    private int matricula;
    private String nome;
    private String email;
    private Set<Curso> cursosMatriculados; 

    public Aluno(int matricula, String nome, String email) {
        this.matricula = matricula;
        this.nome = nome;
        this.email = email;
        this.cursosMatriculados = new HashSet<>();
    }

    public void matricularEmCurso(Curso curso) {
        this.cursosMatriculados.add(curso);
        curso.adicionarAluno(this); 
    }

    public int getMatricula() { return matricula; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public Set<Curso> getCursosMatriculados() { return cursosMatriculados; }

 
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aluno aluno = (Aluno) o;
        return matricula == aluno.matricula;
    }

    public int hashCode() {
        return Objects.hash(matricula);
    }

    @Override
    public String toString() {
        return String.format("[Matrícula: %d] %s (%s)", matricula, nome, email);
    }
}

class Instrutor {
    private String nome;
    private String especialidade;
    private List<Curso> cursosMinistrados; 

    public Instrutor(String nome, String especialidade) {
        this.nome = nome;
        this.especialidade = especialidade;
        this.cursosMinistrados = new ArrayList<>();
    }

    public void adicionarCurso(Curso curso) {
        this.cursosMinistrados.add(curso);
    }

    public String getNome() { return nome; }
    public String getEspecialidade() { return especialidade; }
    public List<Curso> getCursosMinistrados() { return cursosMinistrados; }

    public String toString() {
        return String.format("%s (Especialidade: %s)", nome, especialidade);
    }
}

class Curso {
    private String nome;
    private String sigla;
    
    private Instrutor instrutor;
    
    private Set<Aluno> alunosMatriculados;

    public Curso(String nome, String sigla) {
        this.nome = nome;
        this.sigla = sigla;
        this.alunosMatriculados = new HashSet<>();
    }

 
    public void setInstrutor(Instrutor instrutor) {
        this.instrutor = instrutor;
  
        instrutor.adicionarCurso(this); 
    }

  
    public void adicionarAluno(Aluno aluno) {
        this.alunosMatriculados.add(aluno);
    }

    public String getNome() { return nome; }
    public String getSigla() { return sigla; }
    public Instrutor getInstrutor() { return instrutor; }
    public Set<Aluno> getAlunosMatriculados() { return alunosMatriculados; }

    public String toString() {
        return String.format("Curso: %s (%s)", nome, sigla);
    }
}

 class GestaoEducacaoOnline {

    public static void main(String[] args) {
        System.out.println("=== SISTEMA DE GESTÃO DE EDUCAÇÃO ONLINE ===");

        Instrutor instrutor1 = new Instrutor("Dr. Alan Turing", "Inteligência Artificial");
        Instrutor instrutor2 = new Instrutor("Dra. Grace Hopper", "Programação Orientada a Objetos");

        Curso c1 = new Curso("Introdução à IA", "IA-101");
        Curso c2 = new Curso("Java Avançado", "JAV-201");
        Curso c3 = new Curso("Python para Dados", "PY-150");

        Aluno a1 = new Aluno(1001, "Lucas Mendes", "lucas@email.com");
        Aluno a2 = new Aluno(1002, "Fernanda Costa", "fernanda@email.com");
        Aluno a3 = new Aluno(1003, "Guilherme Santos", "guilherme@email.com");
        Aluno a4 = new Aluno(1004, "Patrícia Lima", "patricia@email.com");
        Aluno a5 = new Aluno(1005, "Ricardo Oliveira", "ricardo@email.com");
        
        Aluno a6_duplicado = new Aluno(1001, "Aluno Duplicado", "duplicado@email.com"); 

        c1.setInstrutor(instrutor1); 
        c3.setInstrutor(instrutor1); 
        c2.setInstrutor(instrutor2); 

        a1.matricularEmCurso(c1);
        a1.matricularEmCurso(c2);
        
        a2.matricularEmCurso(c1);
        a2.matricularEmCurso(c2);
        a2.matricularEmCurso(c3);
        
        a3.matricularEmCurso(c2);
        
        a4.matricularEmCurso(c1);
        a4.matricularEmCurso(c3);

        a5.matricularEmCurso(c3);
        
        c1.adicionarAluno(a6_duplicado); 


        System.out.println("\n==================================================================");
        System.out.println(">>> RESUMO DOS CURSOS CADASTRADOS");
        System.out.println("==================================================================");

        List<Curso> todosCursos = List.of(c1, c2, c3);
        
        for (Curso curso : todosCursos) {
            System.out.println("\n------------------------------------------------------------------");
            System.out.println("CURSO: " + curso.toString());
            System.out.println("INSTRUTOR: " + (curso.getInstrutor() != null ? curso.getInstrutor().toString() : "N/A"));
            System.out.printf("ALUNOS MATRICULADOS (%d):\n", curso.getAlunosMatriculados().size());
            
            if (curso.getAlunosMatriculados().isEmpty()) {
                System.out.println("  Nenhum aluno matriculado.");
            } else {
                for (Aluno aluno : curso.getAlunosMatriculados()) {
                    System.out.printf("  - %s\n", aluno.toString());
                }
            }
        }
        System.out.println("\n==================================================================");
    }
}
