import view.MainFrame;

import javax.swing.*;

/**
 * Ponto de entrada da aplicação Clínica Médica.
 * Inicia a interface gráfica na Event Dispatch Thread do Swing.
 */
public class Main {

    public static void main(String[] args) {
        // Executa na thread do Swing (boa prática)
        // Não usamos SystemLookAndFeel pois o Windows ignora
        // as cores customizadas dos botões com o L&F nativo.
        // O L&F padrão do Swing (Metal) respeita todas as cores definidas.
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
