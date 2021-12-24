package br.esig.tarefa.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.esig.tarefa.dao.TarefaDAO;
import br.esig.tarefa.model.Prioridade;
import br.esig.tarefa.model.Responsavel;
import br.esig.tarefa.model.Tarefa;

@ManagedBean(name = "tarefaBean")
@ViewScoped
public class MBTarefa {

	private TarefaDAO tDAO;
	private Tarefa t;

	private List<Tarefa> tarefas = new ArrayList<Tarefa>();
	private List<Tarefa> tarefasBusca = new ArrayList<Tarefa>();
	
	private List<Prioridade> tipoPrioridade = new ArrayList<Prioridade>();
	private List<Responsavel> listaResponsavel = new ArrayList<Responsavel>();

	// Referente a entidade Tarefa
	private String titulo;
	private String descricao;
	private Responsavel responsavel;
	private Prioridade prioridade;
	private Date deadline;
	private Boolean concluida;

	// Referente as buscas
	private String buscarTituloDescricao;
	private Responsavel buscarResponsavel;
	private Boolean renderResultados;

	@PostConstruct
	public void init() {
		t = new Tarefa();
		tDAO = new TarefaDAO();
		renderResultados = false;
		tipoPrioridade.add(Prioridade.ALTA);
		tipoPrioridade.add(Prioridade.MEDIA);
		tipoPrioridade.add(Prioridade.BAIXA);
		listaResponsavel.add(Responsavel.PESSOA1);
		listaResponsavel.add(Responsavel.PESSOA2);
		listaResponsavel.add(Responsavel.PESSOA3);
		setTipoPrioridade(tipoPrioridade);
	}

	public void salvar() {
		try {
			Tarefa t = getT();
			t.setTitulo(getTitulo());
			t.setDescricao(getDescricao());
			t.setResponsavel(getResponsavel());
			t.setPrioridade(getPrioridade());
			t.setDeadline(getDeadline());
			t.setConcluida(false);
			tDAO.salvar(t);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void excluir(Long id) {
		try {
			tDAO.excluir(id);
			
			if(renderResultados) {
				limparBuscar();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void limpar() {
		t = new Tarefa();
		setTitulo("");
		setDescricao("");
		setDeadline(null);
		setResponsavel(null);
		setPrioridade(null);
	}
	
	public void concluir(Long id) {
		setT(tDAO.consultarPorId(id));
		t.setConcluida(true);
		try {
			tDAO.salvar(getT());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void editar(long id) {
		setT(tDAO.consultarPorId(id));
		setConcluida(t.getConcluida());
		setDescricao(t.getDescricao());
		setTitulo(t.getTitulo());
		setDeadline(t.getDeadline());
		setResponsavel(t.getResponsavel());
		
		if(renderResultados) {
			limparBuscar();
		}
	}

	public void buscar() {
		setTarefasBusca(tDAO.buscar(getBuscarTituloDescricao(), getConcluida(), getBuscarResponsavel()));
		setRenderResultados(true);
	}
	
	public void limparBuscar() {
		setRenderResultados(false);
		setTarefas(getTarefas());
		setConcluida(false);
		setBuscarResponsavel(null);
		setBuscarTituloDescricao("");
		setTarefasBusca(null);
	}

	// Getters Setters
	public List<Prioridade> getTipoPrioridade() {
		return tipoPrioridade;
	}

	public void setTipoPrioridade(List<Prioridade> tipoPrioridade) {
		this.tipoPrioridade = tipoPrioridade;
	}

	public List<Responsavel> getListaResponsavel() {
		return listaResponsavel;
	}

	public void setListaResponsavel(List<Responsavel> listaResponsavel) {
		this.listaResponsavel = listaResponsavel;
	}

	public List<Tarefa> getTarefas() {
		tarefas = tDAO.listarTodas();
		return tarefas;
	}

	public void setTarefas(List<Tarefa> tarefas) {
		this.tarefas = tarefas;
	}

	public Tarefa getT() {
		return t;
	}

	public void setT(Tarefa t) {
		this.t = t;
	}

	public TarefaDAO gettDAO() {
		return tDAO;
	}

	public void settDAO(TarefaDAO tDAO) {
		this.tDAO = tDAO;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Responsavel getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Responsavel responsavel) {
		this.responsavel = responsavel;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public Prioridade getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(Prioridade prioridade) {
		this.prioridade = prioridade;
	}

	public Boolean getConcluida() {
		return concluida;
	}

	public void setConcluida(Boolean concluida) {
		this.concluida = concluida;
	}


	public String getBuscarTituloDescricao() {
		return buscarTituloDescricao;
	}

	public void setBuscarTituloDescricao(String buscarTituloDescricao) {
		this.buscarTituloDescricao = buscarTituloDescricao;
	}

	public Responsavel getBuscarResponsavel() {
		return buscarResponsavel;
	}

	public void setBuscarResponsavel(Responsavel buscarResponsavel) {
		this.buscarResponsavel = buscarResponsavel;
	}
	
	public Boolean getRenderResultados() {
		return renderResultados;
	}

	public void setRenderResultados(Boolean renderResultados) {
		this.renderResultados = renderResultados;
	}
	
	public List<Tarefa> getTarefasBusca() {
		return tarefasBusca;
	}

	public void setTarefasBusca(List<Tarefa> tarefasBusca) {
		this.tarefasBusca = tarefasBusca;
	}
}
