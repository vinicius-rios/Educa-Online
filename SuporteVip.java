import java.util.ArrayList;

// PESSOA 4 - Suporte, prioridade VIP, mentorias e acesso aos cursos
public class SuporteVip {

    static final int MAX_MENTORIAS_POR_MES = 2;
    static final int DIAS_ESPERA_NAO_VIP = 15;

    // chamados
    ArrayList<String> chamadoAluno = new ArrayList<String>();
    ArrayList<String> chamadoDescricao = new ArrayList<String>();
    ArrayList<String> chamadoStatus = new ArrayList<String>();  // aberto, em atendimento, resolvido
    ArrayList<Boolean> chamadoVip = new ArrayList<Boolean>();

    // cada item guarda "aluno-mes" (uma mentoria usada)
    ArrayList<String> mentorias = new ArrayList<String>();

    public int abrirChamado(String aluno, boolean vip, String descricao) {
        chamadoAluno.add(aluno);
        chamadoDescricao.add(descricao);
        chamadoStatus.add("aberto");
        chamadoVip.add(vip);
        return chamadoAluno.size() - 1;
    }

    // atende primeiro os chamados VIP, depois os outros. Devolve o codigo ou -1
    public int atenderProximo() {
        for (int i = 0; i < chamadoStatus.size(); i++) {
            if (chamadoStatus.get(i).equals("aberto") && chamadoVip.get(i)) {
                chamadoStatus.set(i, "em atendimento");
                return i;
            }
        }
        for (int i = 0; i < chamadoStatus.size(); i++) {
            if (chamadoStatus.get(i).equals("aberto")) {
                chamadoStatus.set(i, "em atendimento");
                return i;
            }
        }
        return -1;
    }

    public void resolverChamado(int codigo) {
        chamadoStatus.set(codigo, "resolvido");
    }

    public void mostrarChamados() {
        System.out.println("--- Chamados ---");
        for (int i = 0; i < chamadoAluno.size(); i++) {
            String tipo = chamadoVip.get(i) ? "VIP" : "normal";
            System.out.println("#" + i + " " + chamadoAluno.get(i) + " (" + tipo + ") - "
                    + chamadoDescricao.get(i) + " [" + chamadoStatus.get(i) + "]");
        }
    }

    // VIP tem direito a 2 mentorias por mes
    public boolean agendarMentoria(String aluno, boolean vip, int mes) {
        if (!vip) {
            System.out.println("Mentoria e so para alunos VIP.");
            return false;
        }

        String chave = aluno + "-" + mes;
        int usadas = 0;
        for (int i = 0; i < mentorias.size(); i++) {
            if (mentorias.get(i).equals(chave)) {
                usadas++;
            }
        }

        if (usadas >= MAX_MENTORIAS_POR_MES) {
            System.out.println(aluno + " ja usou as " + MAX_MENTORIAS_POR_MES + " mentorias do mes.");
            return false;
        }
        mentorias.add(chave);
        System.out.println("Mentoria agendada para " + aluno);
        return true;
    }

    // curso exclusivo: so VIP. Curso novo: VIP no lancamento, outros depois de 15 dias
    public boolean podeAcessarCurso(boolean vip, boolean cursoExclusivo, int diasDesdeLancamento) {
        if (cursoExclusivo && !vip) {
            return false;
        }
        if (!vip && diasDesdeLancamento < DIAS_ESPERA_NAO_VIP) {
            return false;
        }
        return true;
    }
}
