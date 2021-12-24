package br.esig.tarefa.model;

public enum Responsavel {
	
	PESSOA1("Pessoa 1"),
	PESSOA2("Pessoa 2"),
	PESSOA3("Pessoa 3");
	
	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	Responsavel(String descricao) {
		this.descricao = descricao.toUpperCase();
	}

}
