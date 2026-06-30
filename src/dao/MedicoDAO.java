package dao;

import model.Medico;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class MedicoDAO {

    private static final String ARQUIVO = "data/medicos.dat";

    public void salvar(List<Medico> lista) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(ARQUIVO))) {
            oos.writeObject(lista);
        } catch (IOException e) {
            System.err.println("Erro ao salvar médicos: " + e.getMessage());
        }
    }

    
    @SuppressWarnings("unchecked")
    public List<Medico> carregar() {
        File arquivo = new File(ARQUIVO);
        if (!arquivo.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(ARQUIVO))) {
            return (List<Medico>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar médicos: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
