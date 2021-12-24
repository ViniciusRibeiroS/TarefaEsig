package br.esig.tarefa.model;

public enum Prioridade {
	
	ALTA("Alta"),
	MEDIA("Média"),
	BAIXA("Baixa");
	
	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	Prioridade(String descricao) {
		this.descricao = descricao.toUpperCase();
	}

}
