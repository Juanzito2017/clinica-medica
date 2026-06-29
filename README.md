# Sistema de Clinica Medica

Aplicacao desktop desenvolvida em **Java com interface grafica (Java Swing)** para gerenciamento de uma clinica medica. O sistema permite o cadastro completo de pacientes, medicos e consultas, com persistencia automatica de dados.

---

## Funcionalidades

| Modulo | Cadastrar | Alterar | Excluir | Relatorio |
|---|:---:|:---:|:---:|:---:|
| Pacientes | Sim | Sim | Sim | Sim |
| Medicos | Sim | Sim | Sim | Sim |
| Consultas | Sim | Sim | Sim | — |
| Relatorio Geral | — | — | — | Sim |

- Selecione um registro na tabela para **editar ou excluir**
- O botao **Relatorio Geral** (no topo) lista todas as pessoas com polimorfismo
- Os dados sao **salvos automaticamente** e persistem entre execucoes

---

## Guia de Uso

### Por onde comecar?

Ao abrir o sistema pela primeira vez, voce vera uma janela com **tres abas** na parte superior: **Pacientes**, **Medicos** e **Consultas**. Siga a ordem abaixo para usar o sistema corretamente:

---

### 1. Cadastrar Medicos

Antes de agendar qualquer consulta, e necessario ter medicos cadastrados.

1. Clique na aba **Medicos**
2. Preencha os campos do formulario:
   - Nome, CPF, Telefone, E-mail
   - CRM e Especialidade
3. Clique em **Cadastrar**
4. O medico aparecera na tabela abaixo

---

### 2. Cadastrar Pacientes

Com os medicos cadastrados, cadastre os pacientes.

1. Clique na aba **Pacientes**
2. Preencha os campos:
   - Nome, CPF, Telefone, E-mail
   - Data de Nascimento e Plano de Saude
3. Clique em **Cadastrar**
4. O paciente aparecera na tabela

---

### 3. Agendar Consultas

Com pelo menos um paciente e um medico cadastrados, voce pode agendar consultas.

1. Clique na aba **Consultas**
2. Selecione o **Paciente** e o **Medico** nos campos de selecao
3. Informe a **data/hora** e o **status** da consulta
4. Adicione uma **observacao** se necessario
5. Clique em **Cadastrar**

---

### Editar ou Excluir um Registro

O processo e o mesmo nas tres abas:

1. **Clique sobre o registro** desejado na tabela — os dados serao carregados automaticamente no formulario
2. Faca as alteracoes nos campos e clique em **Alterar**, ou clique em **Excluir** para remover o registro
3. Use o botao **Limpar** para desmarcar a selecao e limpar o formulario

---

### Relatorio Geral

No topo da janela ha o botao **Relatorio Geral**. Ao clicar, o sistema exibe uma listagem completa de todos os pacientes e medicos cadastrados com suas informacoes especificas.

>  **Os dados sao salvos automaticamente** apos cada operacao. Ao fechar e reabrir o programa, tudo estara preservado.

---

## Conceitos de POO Aplicados

### Heranca e Polimorfismo

A hierarquia de classes e:

```
Pessoa (abstrata)
 Paciente
 Medico
```

`Paciente` e `Medico` herdam de `Pessoa`, reutilizando os campos `nome`, `cpf`, `telefone` e `email`. Cada subclasse implementa os metodos abstratos de forma diferente:

| Metodo | `Paciente` | `Medico` |
|---|---|---|
| `getTipo()` | Retorna `"Paciente"` | Retorna `"Medico"` |
| `getInfoEspecifica()` | Plano de saude e nascimento | CRM e especialidade |
| `gerarRelatorio()` | Relatorio de paciente | Relatorio de medico |

O polimorfismo e demonstrado na tela de **Relatorio Geral**, onde uma `List<Pessoa>` recebe tanto `Paciente` quanto `Medico` e os metodos abstratos sao chamados sem conhecer o tipo concreto:

```java
List<Pessoa> todasPessoas = new ArrayList<>();
todasPessoas.addAll(painelPaciente.getLista());
todasPessoas.addAll(painelMedico.getLista());
for (Pessoa p : todasPessoas) {
    sb.append(p.getTipo());            // polimorfismo
    sb.append(p.getInfoEspecifica());  // polimorfismo
}
```

### Collections

Utiliza `ArrayList<T>` (da interface `List<T>`) em todos os modulos:

- `List<Paciente> listaPacientes` — gerencia pacientes no `PacientePanel`
- `List<Medico> listaMedicos` — gerencia medicos no `MedicoPanel`
- `List<Consulta> listaConsultas` — gerencia consultas no `ConsultaPanel`
- `List<Pessoa> todasPessoas` — relatorio geral polimorfico no `MainFrame`

Operacoes utilizadas: `add()`, `removeIf()`, iteracao com `for-each`, `get()`, `size()`.

### Armazenamento Permanente

Usa **serializacao binaria do Java** (`ObjectOutputStream` / `ObjectInputStream`) para persistir os dados em arquivos `.dat` na pasta `data/`:

| Arquivo | Conteudo |
|---|---|
| `data/pacientes.dat` | Lista de pacientes |
| `data/medicos.dat` | Lista de medicos |
| `data/consultas.dat` | Lista de consultas |

Os dados sao salvos automaticamente a cada operacao (cadastro, alteracao, exclusao) e recarregados ao abrir o programa.

---

## Interface Grafica (Java Swing)

Componentes utilizados:

- `JFrame` — janela principal
- `JTabbedPane` — abas para Pacientes, Medicos e Consultas
- `JTable` + `DefaultTableModel` — listagem dos registros
- `JTextField`, `JComboBox` — campos do formulario
- `JButton` — acoes (Cadastrar, Alterar, Excluir, Limpar, Relatorio)
- `JScrollPane` — rolagem da tabela
- `JOptionPane` — mensagens de confirmacao e relatorios
- Layouts: `GridBagLayout`, `BorderLayout`, `FlowLayout`

---

## Estrutura de Arquivos

```
clinica-medica/
 compilar_e_executar.bat   <- Script para compilar e rodar
 data/                     <- Dados persistidos (gerado automaticamente)
    pacientes.dat
    medicos.dat
    consultas.dat
 src/
     Main.java             <- Ponto de entrada
     model/
        Pessoa.java       <- Classe abstrata base (heranca)
        Paciente.java     <- Herda Pessoa (polimorfismo)
        Medico.java       <- Herda Pessoa (polimorfismo)
        Consulta.java     <- Entidade de agendamento
     dao/
        PacienteDAO.java  <- Persistencia de pacientes
        MedicoDAO.java    <- Persistencia de medicos
        ConsultaDAO.java  <- Persistencia de consultas
     util/
        Util.java         <- Validacoes e utilitarios
     view/
         MainFrame.java    <- Janela principal + Relatorio Geral
         PacientePanel.java<- Aba de pacientes (CRUD)
         MedicoPanel.java  <- Aba de medicos (CRUD)
         ConsultaPanel.java<- Aba de consultas (CRUD)
```

---

## Como Executar

### Pre-requisito

**Java JDK 8 ou superior** instalado.
Download: [https://adoptium.net/](https://adoptium.net/)

### Execucao com Script (Recomendado)

1. Abra o **Prompt de Comando (CMD)** dentro da pasta `clinica-medica/`
2. Execute o script:

```bat
compilar_e_executar.bat
```

3. A aplicacao abrira automaticamente.

### Compilacao Manual (Alternativa)

```bat
mkdir out
mkdir data
javac -encoding UTF-8 -d out -sourcepath src src\Main.java src\model\*.java src\dao\*.java src\util\*.java src\view\*.java
java -cp out Main
```

---

## Tecnologias

- **Linguagem:** Java (JDK 8+)
- **Interface:** Java Swing
- **Persistencia:** Serializacao binaria Java (`.dat`)
- **Paradigma:** Orientacao a Objetos (Heranca, Polimorfismo, Encapsulamento)
