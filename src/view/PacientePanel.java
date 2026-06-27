package view;

import dao.PacienteDAO;
import model.Paciente;
import util.Util;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Painel de gerenciamento de Pacientes.
 * Permite cadastrar, alterar e excluir pacientes.
 * Usa ArrayList (Collection) para armazenar e manipular pacientes.
 */
public class PacientePanel extends JPanel {

    private List<Paciente> listaPacientes;
    private PacienteDAO dao;

    private DefaultTableModel modeloTabela;
    private JTable tabela;

    private JTextField txtId, txtNome, txtCpf, txtTelefone, txtEmail;
    private JTextField txtDataNascimento, txtPlanoSaude;

    private int idSelecionado = -1;

    public PacientePanel() {
        dao = new PacienteDAO();
        listaPacientes = new ArrayList<>(dao.carregar()); // Collection: ArrayList
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(new Color(245, 248, 252));

        add(criarPainelFormulario(), BorderLayout.NORTH);
        add(criarPainelTabela(), BorderLayout.CENTER);
        add(criarPainelBotoes(), BorderLayout.SOUTH);

        atualizarTabela();
    }

    private JPanel criarPainelFormulario() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(Color.WHITE);
        painel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(100, 149, 237), 1),
            "Dados do Paciente",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 13),
            new Color(70, 130, 180)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 8, 5, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtId = new JTextField(5);
        txtId.setEnabled(false);
        txtId.setBackground(new Color(230, 230, 230));

        txtNome           = new JTextField(20);
        txtCpf            = new JTextField(14);
        txtTelefone       = new JTextField(14);
        txtEmail          = new JTextField(20);
        txtDataNascimento = new JTextField(10);
        txtPlanoSaude     = new JTextField(20);

        // Linha 0
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        painel.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.3;
        painel.add(txtId, gbc);

        gbc.gridx = 2; gbc.weightx = 0;
        painel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 3; gbc.weightx = 1.0;
        painel.add(txtNome, gbc);

        // Linha 1
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        painel.add(new JLabel("CPF:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.3;
        painel.add(txtCpf, gbc);

        gbc.gridx = 2; gbc.weightx = 0;
        painel.add(new JLabel("Telefone:"), gbc);
        gbc.gridx = 3; gbc.weightx = 1.0;
        painel.add(txtTelefone, gbc);

        // Linha 2
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        painel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3; gbc.weightx = 1.0;
        painel.add(txtEmail, gbc);
        gbc.gridwidth = 1;

        // Linha 3
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        painel.add(new JLabel("Nascimento (dd/MM/yyyy):"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.3;
        painel.add(txtDataNascimento, gbc);

        gbc.gridx = 2; gbc.weightx = 0;
        painel.add(new JLabel("Plano de Saúde:"), gbc);
        gbc.gridx = 3; gbc.weightx = 1.0;
        painel.add(txtPlanoSaude, gbc);

        return painel;
    }

    private JScrollPane criarPainelTabela() {
        String[] colunas = {"ID", "Nome", "CPF", "Telefone", "Email", "Nascimento", "Plano de Saúde"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };

        tabela = new JTable(modeloTabela);
        tabela.setFont(new Font("Arial", Font.PLAIN, 13));
        tabela.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tabela.getTableHeader().setBackground(new Color(100, 149, 237));
        tabela.getTableHeader().setForeground(Color.WHITE);
        tabela.setRowHeight(24);
        tabela.setSelectionBackground(new Color(173, 216, 230));

        // Ao clicar numa linha, preenche o formulário
        tabela.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int linha = tabela.getSelectedRow();
                if (linha >= 0) {
                    preencherFormulario(linha);
                }
            }
        });

        return new JScrollPane(tabela);
    }

    private JPanel criarPainelBotoes() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        painel.setBackground(new Color(245, 248, 252));

        JButton btnCadastrar = criarBotao("Cadastrar", new Color(46, 139, 87));
        JButton btnAlterar   = criarBotao("Alterar",   new Color(218, 165, 32));
        JButton btnExcluir   = criarBotao("Excluir",   new Color(178, 34, 34));
        JButton btnLimpar    = criarBotao("Limpar",    new Color(100, 100, 100));
        JButton btnRelatorio = criarBotao("Ver Relatório", new Color(70, 130, 180));

        btnCadastrar.addActionListener(e -> cadastrar());
        btnAlterar.addActionListener(e -> alterar());
        btnExcluir.addActionListener(e -> excluir());
        btnLimpar.addActionListener(e -> limparFormulario());
        btnRelatorio.addActionListener(e -> verRelatorio());

        painel.add(btnCadastrar);
        painel.add(btnAlterar);
        painel.add(btnExcluir);
        painel.add(btnLimpar);
        painel.add(btnRelatorio);

        return painel;
    }

    private JButton criarBotao(String texto, Color cor) {
        JButton btn = new JButton(texto);
        btn.setBackground(cor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setOpaque(true);           // força pintura do fundo
        btn.setBorderPainted(false);   // remove borda nativa do Windows
        btn.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void cadastrar() {
        if (!validarCampos()) return;

        // Usa método polimórfico getTipo() via variável Pessoa
        int novoId = listaPacientes.size() > 0
            ? listaPacientes.get(listaPacientes.size() - 1).getId() + 1
            : 1;

        Paciente p = new Paciente(
            novoId,
            txtNome.getText().trim(),
            txtCpf.getText().trim(),
            txtTelefone.getText().trim(),
            txtEmail.getText().trim(),
            txtDataNascimento.getText().trim(),
            txtPlanoSaude.getText().trim()
        );

        listaPacientes.add(p); // Collection: add no ArrayList
        dao.salvar(listaPacientes);
        atualizarTabela();
        limparFormulario();
        JOptionPane.showMessageDialog(this,
            "Paciente cadastrado com sucesso!\nTipo: " + p.getTipo(),
            "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    private void alterar() {
        if (idSelecionado < 0) {
            JOptionPane.showMessageDialog(this,
                "Selecione um paciente na tabela para alterar.",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!validarCampos()) return;

        // Busca o paciente na Collection (ArrayList) pelo ID
        for (int i = 0; i < listaPacientes.size(); i++) {
            if (listaPacientes.get(i).getId() == idSelecionado) {
                Paciente p = listaPacientes.get(i);
                p.setNome(txtNome.getText().trim());
                p.setCpf(txtCpf.getText().trim());
                p.setTelefone(txtTelefone.getText().trim());
                p.setEmail(txtEmail.getText().trim());
                p.setDataNascimento(txtDataNascimento.getText().trim());
                p.setPlanoSaude(txtPlanoSaude.getText().trim());
                break;
            }
        }

        dao.salvar(listaPacientes);
        atualizarTabela();
        limparFormulario();
        JOptionPane.showMessageDialog(this,
            "Paciente alterado com sucesso!",
            "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    private void excluir() {
        if (idSelecionado < 0) {
            JOptionPane.showMessageDialog(this,
                "Selecione um paciente na tabela para excluir.",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int conf = JOptionPane.showConfirmDialog(this,
            "Deseja realmente excluir este paciente?",
            "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);

        if (conf == JOptionPane.YES_OPTION) {
            // Remove da Collection (ArrayList) usando iterator via removeIf
            listaPacientes.removeIf(p -> p.getId() == idSelecionado);
            dao.salvar(listaPacientes);
            atualizarTabela();
            limparFormulario();
            JOptionPane.showMessageDialog(this,
                "Paciente excluído com sucesso!",
                "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void verRelatorio() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this,
                "Selecione um paciente na tabela.",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Paciente p = listaPacientes.get(linha);
        // Chama método polimórfico gerarRelatorio()
        JOptionPane.showMessageDialog(this,
            p.gerarRelatorio(),
            "Relatório do Paciente", JOptionPane.INFORMATION_MESSAGE);
    }

    private void preencherFormulario(int linha) {
        Paciente p = listaPacientes.get(linha);
        idSelecionado = p.getId();
        txtId.setText(String.valueOf(p.getId()));
        txtNome.setText(p.getNome());
        txtCpf.setText(p.getCpf());
        txtTelefone.setText(p.getTelefone());
        txtEmail.setText(p.getEmail());
        txtDataNascimento.setText(p.getDataNascimento());
        txtPlanoSaude.setText(p.getPlanoSaude());
    }

    private void limparFormulario() {
        idSelecionado = -1;
        txtId.setText("");
        txtNome.setText("");
        txtCpf.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
        txtDataNascimento.setText("");
        txtPlanoSaude.setText("");
        tabela.clearSelection();
    }

    private boolean validarCampos() {
        if (Util.campoVazio(txtNome.getText())) {
            JOptionPane.showMessageDialog(this, "Nome é obrigatório!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!Util.cpfValido(txtCpf.getText())) {
            JOptionPane.showMessageDialog(this, "CPF deve ter 11 dígitos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (Util.campoVazio(txtTelefone.getText())) {
            JOptionPane.showMessageDialog(this, "Telefone é obrigatório!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!Util.dataValida(txtDataNascimento.getText())) {
            JOptionPane.showMessageDialog(this, "Data deve estar no formato dd/MM/yyyy!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        // Itera sobre a Collection (ArrayList)
        for (Paciente p : listaPacientes) {
            modeloTabela.addRow(new Object[]{
                p.getId(), p.getNome(), p.getCpf(),
                p.getTelefone(), p.getEmail(),
                p.getDataNascimento(), p.getPlanoSaude()
            });
        }
    }

    // Expõe lista para uso em outros painéis (ex: ConsultaPanel)
    public List<Paciente> getLista() {
        return listaPacientes;
    }
}
