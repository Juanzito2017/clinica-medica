package util;


public class Util {

    public static int gerarId(int tamanhoLista) {
        return tamanhoLista + 1;
    }

    public static boolean cpfValido(String cpf) {
        if (cpf == null) return false;
        String apenas = cpf.replaceAll("[^0-9]", "");
        return apenas.length() == 11;
    }

    public static boolean dataValida(String data) {
        if (data == null) return false;
        return data.matches("\\d{2}/\\d{2}/\\d{4}");
    }

    public static boolean horaValida(String hora) {
        if (hora == null) return false;
        return hora.matches("\\d{2}:\\d{2}");
    }

    public static boolean campoVazio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }
}
