package util;

/**
 * Classe utilitária para geração de IDs únicos
 * e formatação de campos da aplicação.
 */
public class Util {

    // Gera um ID simples baseado no tamanho atual + 1
    public static int gerarId(int tamanhoLista) {
        return tamanhoLista + 1;
    }

    // Valida se um CPF tem exatamente 11 dígitos numéricos
    public static boolean cpfValido(String cpf) {
        if (cpf == null) return false;
        String apenas = cpf.replaceAll("[^0-9]", "");
        return apenas.length() == 11;
    }

    // Valida se a data está no formato dd/MM/yyyy
    public static boolean dataValida(String data) {
        if (data == null) return false;
        return data.matches("\\d{2}/\\d{2}/\\d{4}");
    }

    // Valida se a hora está no formato HH:mm
    public static boolean horaValida(String hora) {
        if (hora == null) return false;
        return hora.matches("\\d{2}:\\d{2}");
    }

    // Verifica se um campo de texto está preenchido
    public static boolean campoVazio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }
}
