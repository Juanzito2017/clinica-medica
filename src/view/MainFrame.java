package view;

import model.Medico;
import model.Paciente;
import model.Pessoa;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Janela principal da aplicação Clínica Médica.
 * Organiza os painéis em abas (JTabbedPane).
 * Demonstra polimorfismo ao usar List<Pessoa> com getTipo() e getInfoEspecifica().
 */
public class MainFrame extends JFrame {

    private PacientePanel painelPaciente;
    private MedicoPanel   painelMedico;
    private ConsultaPanel painelConsulta;

    public MainFrame() {
        setTitle("Sistema de Clínica Médica");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 680);
        setLocationRelativeTo(null);

        // Painel de cabeçalho
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(30, 60, 100));
        header.setPreferredSize(new Dimension(1000, 60));

        JLabel titulo = new JLabel("  🏥  Sistema de Clínica Médica", JLabel.LEFT);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setForeground(Color.WHITE);
        header.add(titulo, BorderLayout.WEST);

        JButton btnRelatorioGeral = new JButton("Relatório Geral");
        btnRelatorioGeral.setBackground(new Color(255, 165, 0));
        btnRelatorioGeral.setForeground(Color.WHITE);
        btnRelatorioGeral.setFont(new Font("Arial", Font.BOLD, 12));
        btnRelatorioGeral.setFocusPainted(false);
        btnRelatorioGeral.setOpaque(true);
        btnRelatorioGeral.setBorderPainted(false);
        btnRelatorioGeral.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btnRelatorioGeral.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnRelatorioGeral.addActionListener(e -> exibirRelatorioGeral());

        JPanel painelBtn = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        painelBtn.setBackground(new Color(30, 60, 100));
        painelBtn.add(btnRelatorioGeral);
        header.add(painelBtn, BorderLayout.EAST);

        // Criação dos painéis
        painelPaciente = new PacientePanel();
        painelMedico   = new MedicoPanel();
        painelConsulta = new ConsultaPanel(
            painelPaciente.getLista(),
            painelMedico.getLista()
        );

        // Abas
        JTabbedPane abas = new JTabbedPane();
        abas.setFont(new Font("Arial", Font.BOLD, 13));
        abas.setBackground(new Color(245, 248, 252));

        abas.addTab("👤 Pacientes", painelPaciente);
        abas.addTab("⚕️ Médicos",   painelMedico);
        abas.addTab("📋 Consultas", painelConsulta);

        // Atualiza os combos de consulta ao trocar de aba
        abas.addChangeListener(e -> {
            if (abas.getSelectedComponent() == painelConsulta) {
                painelConsulta.atualizarCombos();
            }
        });

        add(header, BorderLayout.NORTH);
        add(abas, BorderLayout.CENTER);
    }

    /**
     * Demonstra polimorfismo real:
     * Cria uma lista do tipo Pessoa e adiciona Paciente e Medico.
     * Chama getTipo() e getInfoEspecifica() polimorficamente.
     */
    private void exibirRelatorioGeral() {
        List<Pessoa> todasPessoas = new ArrayList<>();
        todasPessoas.addAll(painelPaciente.getLista());
        todasPessoas.addAll(painelMedico.getLista());

        if (todasPessoas.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Nenhum registro cadastrado ainda.",
                "Relatório Geral", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("===== RELATÓRIO GERAL DA CLÍNICA =====\n\n");

        // Polimorfismo: itera sobre List<Pessoa> e chama métodos polimórficos
        for (Pessoa p : todasPessoas) {
            sb.append("[").append(p.getTipo()).append("] ")
              .append(p.getNome())
              .append(" | ").append(p.getInfoEspecifica())
              .append("\n");
        }

        sb.append("\nTotal de Pacientes: ").append(painelPaciente.getLista().size());
        sb.append("\nTotal de Médicos:   ").append(painelMedico.getLista().size());

        JTextArea area = new JTextArea(sb.toString());
        area.setFont(new Font("Monospaced", Font.PLAIN, 12));
        area.setEditable(false);
        area.setBackground(new Color(245, 248, 252));

        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(550, 350));

        JOptionPane.showMessageDialog(this, scroll,
            "Relatório Geral", JOptionPane.INFORMATION_MESSAGE);
    }
}
