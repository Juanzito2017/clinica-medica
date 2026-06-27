package dao;

import model.Paciente;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO de Paciente - responsável por salvar e carregar
 * a lista de pacientes em arquivo binário (serialização).
 */
public class PacienteDAO {

    private static final String ARQUIVO = "data/pacientes.dat";

    // Salva a lista de pacientes no arquivo
    public void salvar(List<Paciente> lista) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(ARQUIVO))) {
            oos.writeObject(lista);
        } catch (IOException e) {
            System.err.println("Erro ao salvar pacientes: " + e.getMessage());
        }
    }

    // Carrega a lista de pacientes do arquivo
    @SuppressWarnings("unchecked")
    public List<Paciente> carregar() {
        File arquivo = new File(ARQUIVO);
        if (!arquivo.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(ARQUIVO))) {
            return (List<Paciente>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar pacientes: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
