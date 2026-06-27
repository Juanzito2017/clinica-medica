package view;

import dao.ConsultaDAO;
import model.Consulta;
import model.Medico;
import model.Paciente;
import util.Util;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Painel de gerenciamento de Consultas.
 * Permite cadastrar, alterar e excluir consultas.
 * Demonstra polimorfismo ao tratar Paciente e Medico como Pessoa.
 */
public class ConsultaPanel extends JPanel {

    private List<Consulta> listaConsultas;
    private ConsultaDAO dao;

    private DefaultTableModel modeloTabela;
    private JTable tabela;

    private JTextField txtId, txtData, txtHora, txtObservacao;
    private JComboBox<String> cbPaciente, cbMedico, cbStatus;

    private List<Paciente> listaPacientes;
    private List<Medico> listaMedicos;

    private int idSelecionado = -1;

    public ConsultaPanel(List<Paciente> listaPacientes, List<Medico> listaMedicos) {
        this.listaPacientes = listaPacientes;
        this.listaMedicos   = listaMedicos;
        dao = new ConsultaDAO();
        listaConsultas = new ArrayList<>(dao.carregar()); // Collection: ArrayList

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
            BorderFactory.createLineBorder(new Color(186, 85, 211), 1),
            "Dados da Consulta",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 13),
            new Color(148, 0, 211)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 8, 5, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtId = new JTextField(5);
        txtId.setEnabled(false);
        txtId.setBackground(new Color(230, 230, 230));

        txtData       = new JTextField(10);
        txtHora       = new JTextField(6);
        txtObservacao = new JTextField(30);

        // ComboBoxes de seleção
        cbPaciente = new JComboBox<>();
        cbMedico   = new JComboBox<>();
        cbStatus   = new JComboBox<>(new String[]{"AGENDADA", "REALIZADA", "CANCELADA"});

        atualizarCombos();

        // Linha 0
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        painel.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.2;
        painel.add(txtId, gbc);

        gbc.gridx = 2; gbc.weightx = 0;
        painel.add(new JLabel("Data (dd/MM/yyyy):"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.4;
        painel.add(txtData, gbc);

        gbc.gridx = 4; gbc.weightx = 0;
        painel.add(new JLabel("Hora (HH:mm):"), gbc);
        gbc.gridx = 5; gbc.weightx = 0.3;
        painel.add(txtHora, gbc);

        // Linha 1
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        painel.add(new JLabel("Paciente:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; gbc.weightx = 0.5;
        painel.add(cbPaciente, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 3; gbc.weightx = 0;
        painel.add(new JLabel("Médico:"), gbc);
        gbc.gridx = 4; gbc.gridwidth = 2; gbc.weightx = 0.5;
        painel.add(cbMedico, gbc);
        gbc.gridwidth = 1;

        // Linha 2
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        painel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.3;
        painel.add(cbStatus, gbc);

        gbc.gridx = 2; gbc.weightx = 0;
        painel.add(new JLabel("Observação:"), gbc);
        gbc.gridx = 3; gbc.gridwidth = 3; gbc.weightx = 1.0;
        painel.add(txtObservacao, gbc);
        gbc.gridwidth = 1;

        return painel;
    }

    private JScrollPane criarPainelTabela() {
        String[] colunas = {"ID", "Paciente", "Médico", "Data", "Hora", "Status", "Observação"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };

        tabela = new JTable(modeloTabela);
        tabela.setFont(new Font("Arial", Font.PLAIN, 13));
        tabela.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tabela.getTableHeader().setBackground(new Color(148, 0, 211));
        tabela.getTableHeader().setForeground(Color.WHITE);
        tabela.setRowHeight(24);
        tabela.setSelectionBackground(new Color(221, 160, 221));

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

        JButton btnCadastrar     = criarBotao("Agendar",         new Color(46, 139, 87));
        JButton btnAlterar       = criarBotao("Alterar",          new Color(218, 165, 32));
        JButton btnExcluir       = criarBotao("Excluir",          new Color(178, 34, 34));
        JButton btnLimpar        = criarBotao("Limpar",           new Color(100, 100, 100));
        JButton btnAtualizarComb = criarBotao("Atualizar Listas", new Color(70, 130, 180));

        btnCadastrar.addActionListener(e -> cadastrar());
        btnAlterar.addActionListener(e -> alterar());
        btnExcluir.addActionListener(e -> excluir());
        btnLimpar.addActionListener(e -> limparFormulario());
        btnAtualizarComb.addActionListener(e -> atualizarCombos());

        painel.add(btnCadastrar);
        painel.add(btnAlterar);
        painel.add(btnExcluir);
        painel.add(btnLimpar);
        painel.add(btnAtualizarComb);

        return painel;
    }

    private JButton criarBotao(String texto, Color cor) {
        JButton btn = new JButton(texto);
        btn.setBackground(cor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // Atualiza os comboboxes com dados das listas de Paciente e Medico
    public void atualizarCombos() {
        cbPaciente.removeAllItems();
        cbMedico.removeAllItems();

        // Polimorfismo: usa getInfoEspecifica() definida em Pessoa
        // mas com comportamento de Paciente e Medico respectivamente
        for (Paciente p : listaPacientes) {
            cbPaciente.addItem(p.getId() + " - " + p.getNome());
        }
        for (Medico m : listaMedicos) {
            cbMedico.addItem(m.getId() + " - " + m.getNome() + " (" + m.getEspecialidade() + ")");
        }
    }

    private void cadastrar() {
        if (!validarCampos()) return;

        Paciente pacienteSel = getPacienteSelecionado();
        Medico medicoSel     = getMedicoSelecionado();

        if (pacienteSel == null || medicoSel == null) {
            JOptionPane.showMessageDialog(this,
                "Selecione um paciente e um médico.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int novoId = listaConsultas.size() > 0
            ? listaConsultas.get(listaConsultas.size() - 1).getId() + 1
            : 1;

        Consulta c = new Consulta(
            novoId,
            pacienteSel,
            medicoSel,
            txtData.getText().trim(),
            txtHora.getText().trim(),
            Consulta.Status.valueOf((String) cbStatus.getSelectedItem()),
            txtObservacao.getText().trim()
        );

        listaConsultas.add(c); // Collection: add no ArrayList
        dao.salvar(listaConsultas);
        atualizarTabela();
        limparFormulario();
        JOptionPane.showMessageDialog(this,
            "Consulta agendada com sucesso!\n"
            + "Paciente: " + pacienteSel.getTipo() + " - " + pacienteSel.getNome() + "\n"
            + "Médico: " + medicoSel.getTipo() + " - " + medicoSel.getNome(),
            "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    private void alterar() {
        if (idSelecionado < 0) {
            JOptionPane.showMessageDialog(this,
                "Selecione uma consulta na tabela para alterar.",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!validarCampos()) return;

        Paciente pacienteSel = getPacienteSelecionado();
        Medico medicoSel     = getMedicoSelecionado();

        for (Consulta c : listaConsultas) {
            if (c.getId() == idSelecionado) {
                c.setPaciente(pacienteSel);
                c.setMedico(medicoSel);
                c.setData(txtData.getText().trim());
                c.setHora(txtHora.getText().trim());
                c.setStatus(Consulta.Status.valueOf((String) cbStatus.getSelectedItem()));
                c.setObservacao(txtObservacao.getText().trim());
                break;
            }
        }

        dao.salvar(listaConsultas);
        atualizarTabela();
        limparFormulario();
        JOptionPane.showMessageDialog(this,
            "Consulta alterada com sucesso!",
            "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    private void excluir() {
        if (idSelecionado < 0) {
            JOptionPane.showMessageDialog(this,
                "Selecione uma consulta na tabela para excluir.",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int conf = JOptionPane.showConfirmDialog(this,
            "Deseja realmente excluir esta consulta?",
            "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);

        if (conf == JOptionPane.YES_OPTION) {
            listaConsultas.removeIf(c -> c.getId() == idSelecionado); // Collection: removeIf
            dao.salvar(listaConsultas);
            atualizarTabela();
            limparFormulario();
            JOptionPane.showMessageDialog(this,
                "Consulta excluída com sucesso!",
                "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void preencherFormulario(int linha) {
        Consulta c = listaConsultas.get(linha);
        idSelecionado = c.getId();
        txtId.setText(String.valueOf(c.getId()));
        txtData.setText(c.getData());
        txtHora.setText(c.getHora());
        txtObservacao.setText(c.getObservacao());
        cbStatus.setSelectedItem(c.getStatus().name());

        // Seleciona paciente no combobox
        for (int i = 0; i < listaPacientes.size(); i++) {
            if (listaPacientes.get(i).getId() == c.getPaciente().getId()) {
                cbPaciente.setSelectedIndex(i);
                break;
            }
        }
        // Seleciona médico no combobox
        for (int i = 0; i < listaMedicos.size(); i++) {
            if (listaMedicos.get(i).getId() == c.getMedico().getId()) {
                cbMedico.setSelectedIndex(i);
                break;
            }
        }
    }

    private void limparFormulario() {
        idSelecionado = -1;
        txtId.setText("");
        txtData.setText("");
        txtHora.setText("");
        txtObservacao.setText("");
        cbStatus.setSelectedIndex(0);
        if (cbPaciente.getItemCount() > 0) cbPaciente.setSelectedIndex(0);
        if (cbMedico.getItemCount() > 0) cbMedico.setSelectedIndex(0);
        tabela.clearSelection();
    }

    private boolean validarCampos() {
        if (!Util.dataValida(txtData.getText())) {
            JOptionPane.showMessageDialog(this,
                "Data deve estar no formato dd/MM/yyyy!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!Util.horaValida(txtHora.getText())) {
            JOptionPane.showMessageDialog(this,
                "Hora deve estar no formato HH:mm!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (listaPacientes.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Cadastre pelo menos um paciente antes de agendar!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (listaMedicos.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Cadastre pelo menos um médico antes de agendar!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private Paciente getPacienteSelecionado() {
        int idx = cbPaciente.getSelectedIndex();
        if (idx < 0 || idx >= listaPacientes.size()) return null;
        return listaPacientes.get(idx);
    }

    private Medico getMedicoSelecionado() {
        int idx = cbMedico.getSelectedIndex();
        if (idx < 0 || idx >= listaMedicos.size()) return null;
        return listaMedicos.get(idx);
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        for (Consulta c : listaConsultas) { // Iterando sobre a Collection
            modeloTabela.addRow(new Object[]{
                c.getId(),
                c.getPaciente().getNome(),
                c.getMedico().getNome(),
                c.getData(),
                c.getHora(),
                c.getStatus().name(),
                c.getObservacao()
            });
        }
    }
}
