package model;

import java.time.LocalDate;

public class Mentoria {
    private final int id;
    private final Aluno aluno;
    private final LocalDate data;

    public Mentoria(int id, Aluno aluno, LocalDate data) {
        if (aluno == null) {
            throw new IllegalArgumentException("Aluno obrigatório");
        }
        if (data == null) {
            throw new IllegalArgumentException("Data da mentoria obrigatória");
        }
        this.id = id;
        this.aluno = aluno;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public LocalDate getData() {
        return data;
    }
}
