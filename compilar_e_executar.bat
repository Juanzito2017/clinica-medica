@echo off

set JAVAC="C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2023.3.8\jbr\bin\javac.exe"
set JAVAW="C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2023.3.8\jbr\bin\javaw.exe"

echo ========================================
echo  Sistema de Clinica Medica
echo ========================================
echo.

if not exist %JAVAC% (
    echo ERRO: Java nao encontrado!
    echo Caminho esperado: %JAVAC%
    pause
    exit /b 1
)

if not exist "out" mkdir out
if not exist "data" mkdir data

echo Compilando...
%JAVAC% -encoding UTF-8 -d out src\Main.java src\model\Pessoa.java src\model\Paciente.java src\model\Medico.java src\model\Consulta.java src\dao\PacienteDAO.java src\dao\MedicoDAO.java src\dao\ConsultaDAO.java src\util\Util.java src\view\MainFrame.java src\view\PacientePanel.java src\view\MedicoPanel.java src\view\ConsultaPanel.java

if %errorlevel% neq 0 (
    echo.
    echo ERRO na compilacao!
    pause
    exit /b 1
)

echo Compilacao concluida!
echo.
echo Iniciando aplicacao...
start "" %JAVAW% -cp out Main
