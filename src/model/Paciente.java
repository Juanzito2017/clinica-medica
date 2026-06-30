package model;

import java.io.Serializable;


public class Paciente extends Pessoa implements Serializable {

    private static final long serialVersionUID = 2L;

    private String dataNascimento;
    private String planoSaude;

    public Paciente(int id, String nome, String cpf, String telefone,
                    String email, String dataNascimento, String planoSaude) {
        super(id, nome, cpf, telefone, email);
        this.dataNascimento = dataNascimento;
        this.planoSaude = planoSaude;
    }

    
    @Override
    public String getTipo() {
        return "Paciente";
    }

    
    @Override
    public String getInfoEspecifica() {
        return "Plano: " + planoSaude + " | Nascimento: " + dataNascimento;
    }

    
    @Override
    public String gerarRelatorio() {
        return "=== PACIENTE ===\n"
             + "ID: " + getId() + "\n"
             + "Nome: " + getNome() + "\n"
             + "CPF: " + getCpf() + "\n"
             + "Telefone: " + getTelefone() + "\n"
             + "Email: " + getEmail() + "\n"
             + "Data de Nascimento: " + dataNascimento + "\n"
             + "Plano de Saúde: " + planoSaude;
    }

    public String getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(String dataNascimento) { this.dataNascimento = dataNascimento; }

    public String getPlanoSaude() { return planoSaude; }
    public void setPlanoSaude(String planoSaude) { this.planoSaude = planoSaude; }
}
