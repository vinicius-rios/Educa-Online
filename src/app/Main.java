package app;

import excecoes.*;
import model.*;
import service.EducaOnlineService;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final EducaOnlineService servico = new EducaOnlineService();

    public static void main(String[] args) {
        while (true) {
            exibirMenuPrincipal();
            int opcao = lerInt("Escolha: ");
            switch (opcao) {
                case 1 -> menuAlunos();
                case 2 -> menuCursosETurmas();
                case 3 -> menuVidaAcademica();
                case 4 -> menuSuporte();
                case 5 -> menuFinanceiro();
                case 6 -> menuRelatorios();
                case 0 -> {
                    System.out.println("Encerrando...");
                    return;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private static void exibirMenuPrincipal() {
        System.out.println("\n===== EDUCAONLINE =====");
        System.out.println("1 - Alunos e matrículas");
        System.out.println("2 - Cursos e turmas");
        System.out.println("3 - Vida acadêmica");
        System.out.println("4 - Suporte");
        System.out.println("5 - Financeiro");
        System.out.println("6 - Relatórios");
        System.out.println("0 - Sair");
    }

    private static void menuAlunos() {
        int opcao;
        do {
            System.out.println("\n--- ALUNOS E MATRÍCULAS ---");
            System.out.println("1 - Cadastrar aluno");
            System.out.println("2 - Matricular aluno em uma turma");
            System.out.println("3 - Solicitar mentoria VIP");
            System.out.println("0 - Voltar");
            opcao = lerInt("Escolha: ");
            switch (opcao) {
                case 1 -> executarAcao(Main::cadastrarAluno);
                case 2 -> executarAcao(Main::matricular);
                case 3 -> executarAcao(Main::solicitarMentoria);
                case 0 -> { }
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static void menuCursosETurmas() {
        int opcao;
        do {
            System.out.println("\n--- CURSOS E TURMAS ---");
            System.out.println("1 - Cadastrar curso");
            System.out.println("2 - Criar turma");
            System.out.println("0 - Voltar");
            opcao = lerInt("Escolha: ");
            switch (opcao) {
                case 1 -> executarAcao(Main::cadastrarCurso);
                case 2 -> executarAcao(Main::criarTurma);
                case 0 -> { }
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static void menuVidaAcademica() {
        int opcao;
        do {
            System.out.println("\n--- VIDA ACADÊMICA ---");
            System.out.println("1 - Registrar aula concluída");
            System.out.println("2 - Lançar avaliação");
            System.out.println("3 - Emitir certificado");
            System.out.println("0 - Voltar");
            opcao = lerInt("Escolha: ");
            switch (opcao) {
                case 1 -> executarAcao(Main::concluirAula);
                case 2 -> executarAcao(Main::lancarAvaliacao);
                case 3 -> executarAcao(Main::emitirCertificado);
                case 0 -> { }
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static void menuSuporte() {
        int opcao;
        do {
            System.out.println("\n--- SUPORTE ---");
            System.out.println("1 - Abrir chamado");
            System.out.println("2 - Atender próximo chamado");
            System.out.println("3 - Resolver chamado");
            System.out.println("0 - Voltar");
            opcao = lerInt("Escolha: ");
            switch (opcao) {
                case 1 -> executarAcao(Main::abrirChamado);
                case 2 -> executarAcao(Main::atenderChamado);
                case 3 -> executarAcao(Main::resolverChamado);
                case 0 -> { }
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static void menuFinanceiro() {
        int opcao;
        do {
            System.out.println("\n--- FINANCEIRO ---");
            System.out.println("1 - Registrar pagamento");
            System.out.println("0 - Voltar");
            opcao = lerInt("Escolha: ");
            switch (opcao) {
                case 1 -> executarAcao(Main::registrarPagamento);
                case 0 -> { }
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static void menuRelatorios() {
        int opcao;
        do {
            System.out.println("\n--- RELATÓRIOS ---");
            System.out.println("1 - Relatório de desempenho");
            System.out.println("2 - Relatório financeiro");
            System.out.println("0 - Voltar");
            opcao = lerInt("Escolha: ");
            switch (opcao) {
                case 1 -> executarAcao(() -> System.out.println(servico.relatorioDesempenho()));
                case 2 -> executarAcao(() -> System.out.println(servico.relatorioFinanceiro()));
                case 0 -> { }
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static void executarAcao(AcaoMenu acao) {
        try {
            acao.executar();
            servico.salvar();
        } catch (IllegalArgumentException | IllegalStateException | TurmaCheiaException
                 | MatriculaDuplicadaException | AcessoNegadoException | CertificadoNaoEmitidoException
                 | LimiteMentoriasException e) {
            System.out.println("Erro: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados: " + e.getMessage());
        } finally {
            System.out.print("\nPressione Enter para continuar...");
            sc.nextLine();
            limparTela();
        }
    }

    private static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    @FunctionalInterface
    private interface AcaoMenu {
        void executar() throws IOException, TurmaCheiaException, MatriculaDuplicadaException,
                AcessoNegadoException, CertificadoNaoEmitidoException, LimiteMentoriasException;
    }

    private static void cadastrarAluno() {
        String nome = lerTexto("Nome: ");
        String email = lerEmail();
        boolean vip = lerInt("Tipo (1-Padrão, 2-VIP): ") == 2;
        Aluno aluno = servico.cadastrarAluno(nome, email, vip);
        System.out.println("Aluno cadastrado com ID " + aluno.getId());
    }

    private static void cadastrarCurso() {
        String nome = lerTexto("Nome do curso: ");
        String categoria = lerTexto("Categoria: ");
        String area = lerTexto("Área de conhecimento: ");
        int carga = lerInt("Carga horária (h): ");
        boolean exclusivo = lerInt("Exclusivo VIP? (1-Sim, 2-Não): ") == 1;
        int dias = lerInt("Lançado há quantos dias? (0 = hoje): ");
        int modulos = lerInt("Quantidade de módulos: ");
        int aulas = lerInt("Aulas por módulo: ");
        Curso curso = servico.cadastrarCurso(nome, categoria, area, carga, exclusivo,
                LocalDate.now().minusDays(dias), modulos, aulas);
        System.out.println("Curso cadastrado com ID " + curso.getId());
    }

    private static void criarTurma() {
        listarCursos();
        int cursoId = lerInt("ID do curso: ");
        String nome = lerTexto("Nome da turma: ");
        Turma turma = servico.criarTurma(nome, cursoId);
        System.out.println("Turma criada com ID " + turma.getId());
    }

    private static void matricular() throws TurmaCheiaException, MatriculaDuplicadaException, AcessoNegadoException {
        listarAlunos();
        listarTurmas();
        int alunoId = lerInt("ID do aluno: ");
        int turmaId = lerInt("ID da turma: ");
        servico.matricular(alunoId, turmaId);
        System.out.println("Matrícula realizada com sucesso.");
    }

    private static void concluirAula() {
        int alunoId = lerInt("ID do aluno: ");
        int turmaId = lerInt("ID da turma: ");
        Turma turma = servico.buscarTurma(turmaId);
        for (Modulo modulo : turma.getCurso().getModulos()) {
            for (Aula aula : modulo.getAulas()) {
                System.out.println(aula.getId() + " - " + aula.getTitulo());
            }
        }
        int aulaId = lerInt("ID da aula concluída: ");
        servico.concluirAula(alunoId, turmaId, aulaId);
        System.out.println("Aula registrada como concluída.");
    }

    private static void lancarAvaliacao() {
        int alunoId = lerInt("ID do aluno: ");
        int turmaId = lerInt("ID da turma: ");
        int tipo = lerInt("Tipo (1-Prova, 2-Trabalho): ");
        String modulo = lerTexto("Módulo: ");
        double peso = lerDouble("Peso: ");
        double nota = lerDouble("Nota (0 a 10): ");
        servico.lancarAvaliacao(alunoId, turmaId, tipo, modulo, peso, nota);
        System.out.println("Avaliação lançada.");
    }

    private static void emitirCertificado() throws CertificadoNaoEmitidoException {
        int alunoId = lerInt("ID do aluno: ");
        int turmaId = lerInt("ID da turma: ");
        Certificado c = servico.emitirCertificado(alunoId, turmaId);
        System.out.println("\n=== CERTIFICADO ===");
        System.out.println("Aluno: " + c.getAluno().getNome());
        System.out.println("Curso: " + c.getCurso().getNome());
        System.out.printf("Média final: %.1f%n", c.getMediaFinal());
        System.out.println("Emitido em: " + c.getDataEmissao());
        System.out.println("Código de validação: " + c.getCodigoValidacao());
    }

    private static void abrirChamado() {
        listarAlunos();
        int alunoId = lerInt("ID do aluno: ");
        String descricao = lerTexto("Descrição do problema: ");
        Chamado chamado = servico.abrirChamado(alunoId, descricao);
        System.out.println("Chamado aberto com ID " + chamado.getId());
    }

    private static void atenderChamado() {
        Chamado c = servico.atenderProximoChamado();
        String tipo = c.getAluno() instanceof AlunoVip ? " [VIP]" : "";
        System.out.println("Atendendo chamado " + c.getId() + " de " + c.getAluno().getNome() + tipo
                + ": " + c.getDescricao());
    }

    private static void resolverChamado() {
        int id = lerInt("ID do chamado: ");
        servico.resolverChamado(id);
        System.out.println("Chamado resolvido.");
    }

    private static void registrarPagamento() {
        listarAlunos();
        int alunoId = lerInt("ID do aluno: ");
        int tipo = lerInt("Tipo (1-Assinatura mensal, 2-Curso avulso): ");
        if (tipo == 1) {
            Pagamento p = servico.registrarAssinatura(alunoId);
            System.out.printf("%s registrada: R$ %.2f%n", p.getDescricao(), p.getValor());
        } else {
            listarCursos();
            int cursoId = lerInt("ID do curso: ");
            double valor = lerDouble("Valor (R$): ");
            Pagamento p = servico.registrarPagamentoAvulso(alunoId, cursoId, valor);
            System.out.printf("%s registrado: R$ %.2f%n", p.getDescricao(), p.getValor());
        }
    }

    private static void solicitarMentoria() throws AcessoNegadoException, LimiteMentoriasException {
        listarAlunos();
        int alunoId = lerInt("ID do aluno: ");
        Mentoria mentoria = servico.solicitarMentoria(alunoId);
        int utilizadas = servico.getQuantidadeMentoriasNoMes(alunoId);
        System.out.printf("Mentoria registrada para %s em %s. Uso no mês: %d/2.%n",
                mentoria.getAluno().getNome(), mentoria.getData(), utilizadas);
    }

    private static void listarAlunos() {
        System.out.println("--- Alunos ---");
        for (Aluno a : servico.getAlunos()) {
            System.out.println(a.getId() + " - " + a.getNome() + (a instanceof AlunoVip ? " [VIP]" : ""));
        }
    }

    private static void listarCursos() {
        System.out.println("--- Cursos ---");
        for (Curso c : servico.getCursos()) {
            System.out.println(c.getId() + " - " + c.getNome() + (c.isExclusivo() ? " [exclusivo VIP]" : ""));
        }
    }

    private static void listarTurmas() {
        System.out.println("--- Turmas ---");
        for (Turma t : servico.getTurmas()) {
            System.out.println(t.getId() + " - " + t.getNome() + " (" + t.getCurso().getNome()
                    + ", vagas: " + t.getVagasDisponiveis() + ")");
        }
    }

    private static int lerInt(String msg) {
        while (true) {
            System.out.print(msg);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Digite um número inteiro válido.");
            }
        }
    }

    private static double lerDouble(String msg) {
        while (true) {
            System.out.print(msg);
            try {
                return Double.parseDouble(sc.nextLine().trim().replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println("Digite um número válido.");
            }
        }
    }
    private static String lerTexto(String msg) {
        System.out.print(msg);
        return sc.nextLine().trim();
    }

    private static String lerEmail() {
        while (true) {
            String email = lerTexto("E-mail (usuario@provedor.com): ");
            if (Aluno.emailValido(email)) {
                return email;
            }
            System.out.println("E-mail inválido. Informe um endereço no formato usuario@provedor.com, terminando em .com.");
        }
    }
}
