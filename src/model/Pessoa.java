package model;

import java.io.Serializable;

/**
 * Classe abstrata Pessoa - base da hierarquia de herança.
 * Paciente e Medico herdam desta classe.
 */
public abstract class Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;

    public Pessoa(int id, String nome, String cpf, String telefone, String email) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
    }

    // Método abstrato - polimorfismo: cada subclasse implementa de forma diferente
    public abstract String getTipo();

    // Método abstrato - polimorfismo: retorna informações específicas de cada tipo
    public abstract String getInfoEspecifica();

    // Método abstrato - polimorfismo: exibe informações em formato de relatório
    public abstract String gerarRelatorio();

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
