package model;

import java.util.regex.Pattern;

public abstract class Aluno {
    private static final Pattern PADRAO_EMAIL = Pattern.compile(
            "^[A-Za-z0-9]+(?:[._%+-][A-Za-z0-9]+)*@[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?\\.com$",
            Pattern.CASE_INSENSITIVE
    );

    private final int id;
    private String nome;
    private String email;

    protected Aluno(int id, String nome, String email) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome inválido");
        }
        if (!emailValido(email)) {
            throw new IllegalArgumentException("E-mail inválido. Use o formato usuario@provedor.com.");
        }
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    public int getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public String getEmail() {
        return email;
    }

    public static boolean emailValido(String email) {
        return email != null && PADRAO_EMAIL.matcher(email).matches();
    }

    public abstract boolean podeAcessar(Curso curso);
    public abstract int prioridadeSuporte();
    public abstract double valorMensalidade();
}
