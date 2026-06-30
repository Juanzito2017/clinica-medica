package dao;

import model.Consulta;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class ConsultaDAO {

    private static final String ARQUIVO = "data/consultas.dat";

    
    public void salvar(List<Consulta> lista) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(ARQUIVO))) {
            oos.writeObject(lista);
        } catch (IOException e) {
            System.err.println("Erro ao salvar consultas: " + e.getMessage());
        }
    }

    
    @SuppressWarnings("unchecked")
    public List<Consulta> carregar() {
        File arquivo = new File(ARQUIVO);
        if (!arquivo.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(ARQUIVO))) {
            return (List<Consulta>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar consultas: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
