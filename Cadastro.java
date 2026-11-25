import java.util.ArrayList;

// PESSOA 1 - Cadastro de alunos, cursos e turmas
public class Cadastro {

    static final int LIMITE_TURMA = 40;

    ArrayList<String> alunos = new ArrayList<String>();

    // dados dos cursos (a posicao na lista e o "codigo" do curso)
    ArrayList<String> cursos = new ArrayList<String>();
    ArrayList<String> categorias = new ArrayList<String>();
    ArrayList<String> areas = new ArrayList<String>();
    ArrayList<Integer> cargasHorarias = new ArrayList<Integer>();
    ArrayList<Integer> totalAulas = new ArrayList<Integer>();

    // dados das turmas (a posicao na lista e o "codigo" da turma)
    ArrayList<String> turmaCurso = new ArrayList<String>();
    ArrayList<ArrayList<String>> turmaAlunos = new ArrayList<ArrayList<String>>();

    public void cadastrarAluno(String nome) {
        alunos.add(nome);
        System.out.println("Aluno cadastrado: " + nome);
    }

    public void cadastrarCurso(String nome, String categoria, String area, int cargaHoraria, int aulas) {
        cursos.add(nome);
        categorias.add(categoria);
        areas.add(area);
        cargasHorarias.add(cargaHoraria);
        totalAulas.add(aulas);
        System.out.println("Curso cadastrado: " + nome);
    }

    // cria uma turma e devolve o codigo dela
    public int criarTurma(String nomeCurso) {
        turmaCurso.add(nomeCurso);
        turmaAlunos.add(new ArrayList<String>());
        return turmaCurso.size() - 1;
    }

    public boolean matricular(int turma, String aluno) {
        ArrayList<String> lista = turmaAlunos.get(turma);

        if (lista.contains(aluno)) {
            System.out.println("ERRO: " + aluno + " ja esta matriculado nessa turma.");
            return false;
        }
        if (lista.size() >= LIMITE_TURMA) {
            System.out.println("ERRO: turma lotada (limite " + LIMITE_TURMA + ").");
            return false;
        }
        lista.add(aluno);
        System.out.println(aluno + " matriculado na turma " + turma);
        return true;
    }

    // devolve quantas aulas o curso tem (usado pelo relatorio de desempenho)
    public int getTotalAulas(String nomeCurso) {
        int pos = cursos.indexOf(nomeCurso);
        if (pos == -1) {
            return 0;
        }
        return totalAulas.get(pos);
    }
}
