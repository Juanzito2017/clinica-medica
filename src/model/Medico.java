package model;

import java.io.Serializable;


public class Medico extends Pessoa implements Serializable {

    private static final long serialVersionUID = 3L;

    private String crm;
    private String especialidade;

    public Medico(int id, String nome, String cpf, String telefone,
                  String email, String crm, String especialidade) {
        super(id, nome, cpf, telefone, email);
        this.crm = crm;
        this.especialidade = especialidade;
    }

    
    @Override
    public String getTipo() {
        return "Médico";
    }

    
    @Override
    public String getInfoEspecifica() {
        return "CRM: " + crm + " | Especialidade: " + especialidade;
    }

    
    @Override
    public String gerarRelatorio() {
        return "=== MÉDICO ===\n"
             + "ID: " + getId() + "\n"
             + "Nome: " + getNome() + "\n"
             + "CPF: " + getCpf() + "\n"
             + "Telefone: " + getTelefone() + "\n"
             + "Email: " + getEmail() + "\n"
             + "CRM: " + crm + "\n"
             + "Especialidade: " + especialidade;
    }

    public String getCrm() { return crm; }
    public void setCrm(String crm) { this.crm = crm; }

    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }
}
