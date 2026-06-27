package model;

import java.io.Serializable;

/**
 * Classe Consulta - representa um agendamento médico.
 * Associa um Paciente a um Medico em uma data/hora específica.
 */
public class Consulta implements Serializable {

    private static final long serialVersionUID = 4L;

    // Possíveis status de uma consulta
    public enum Status {
        AGENDADA, REALIZADA, CANCELADA
    }

    private int id;
    private Paciente paciente;
    private Medico medico;
    private String data;
    private String hora;
    private Status status;
    private String observacao;

    public Consulta(int id, Paciente paciente, Medico medico,
                    String data, String hora, Status status, String observacao) {
        this.id = id;
        this.paciente = paciente;
        this.medico = medico;
        this.data = data;
        this.hora = hora;
        this.status = status;
        this.observacao = observacao;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }

    public Medico getMedico() { return medico; }
    public void setMedico(Medico medico) { this.medico = medico; }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }

    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
}
