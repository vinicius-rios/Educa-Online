# EducaOnline

Sistema de cursos EAD feito em Java para o trabalho de Programação Orientada a Objetos.

## O que precisa

- JDK 21 ou superior instalado,com `java` e `javac` disponíveis no PowerShell.
- O projeto não usa bibliotecas externas.

## Como executar no Windows

1. Extraia o arquivo ZIP.
2. Abra o PowerShell dentro da pasta `educaonline`,onde ficam `src` e este README.
3. Confira se o Java está instalado:

   ```powershell
   java -version
   javac -version
   ```

4. Compile o projeto:

   ```powershell
   $fontes = Get-ChildItem .\src -Recurse -Filter *.java | ForEach-Object { $_.FullName }
   javac --release 21 -encoding UTF-8 -d out $fontes
   ```

5. Inicie o programa:

   ```powershell
   java -cp out app.Main
   ```

## Uso

Escolha uma opção no menu. Digite `0` para voltar ao menu anterior ou sair. Depois de cada operação,pressione Enter para limpar a tela.

## Dados

Os registros ficam em arquivos CSV na pasta `dados`. Execute o programa a partir da pasta `educaonline` para manter os arquivos nesse local.

Se o PowerShell não reconhecer `java` ou `javac`,instale o JDK 21 ou superior e abra uma nova janela do PowerShell.

## Arquivos do trabalho

- [Relatório em PDF](docs/Relatorio_EducaOnline.pdf)
- [Projeto completo em ZIP](downloads/EducaOnline-Trabalho-Final.zip)
