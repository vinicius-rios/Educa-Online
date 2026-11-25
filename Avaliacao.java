import java.util.ArrayList;

// PESSOA 2 - Avaliacoes, media ponderada e desempenho
public class Avaliacao {

    ArrayList<String> alunosNotas = new ArrayList<String>();
    ArrayList<String> tipos = new ArrayList<String>();     // prova ou trabalho
    ArrayList<Double> pesos = new ArrayList<Double>();
    ArrayList<Double> notas = new ArrayList<Double>();

    // cada item guarda "aluno-aula", assim nao conta a mesma aula duas vezes
    ArrayList<String> aulasConcluidas = new ArrayList<String>();

    public void lancarNota(String aluno, String tipo, double peso, double nota) {
        alunosNotas.add(aluno);
        tipos.add(tipo);
        pesos.add(peso);
        notas.add(nota);
    }

    // media ponderada = soma(nota * peso) / soma(pesos)
    public double mediaFinal(String aluno) {
        double soma = 0;
        double somaPesos = 0;

        for (int i = 0; i < alunosNotas.size(); i++) {
            if (alunosNotas.get(i).equals(aluno)) {
                soma = soma + notas.get(i) * pesos.get(i);
                somaPesos = somaPesos + pesos.get(i);
            }
        }

        if (somaPesos == 0) {
            return 0;
        }
        return soma / somaPesos;
    }

    public void concluirAula(String aluno, String aula) {
        String registro = aluno + "-" + aula;
        if (!aulasConcluidas.contains(registro)) {
            aulasConcluidas.add(registro);
        }
    }

    public double percentualConcluido(String aluno, int totalAulas) {
        if (totalAulas == 0) {
            return 0;
        }
        int contador = 0;
        for (int i = 0; i < aulasConcluidas.size(); i++) {
            if (aulasConcluidas.get(i).startsWith(aluno + "-")) {
                contador++;
            }
        }
        return contador * 100.0 / totalAulas;
    }

    public void relatorioDesempenho(ArrayList<String> alunos, int totalAulas) {
        System.out.println("--- Relatorio de desempenho ---");
        for (int i = 0; i < alunos.size(); i++) {
            String aluno = alunos.get(i);
            System.out.println(aluno + " | media: " + mediaFinal(aluno)
                    + " | aulas concluidas: " + percentualConcluido(aluno, totalAulas) + "%");
        }
    }
}
