package br.senac.sc.meuspedidos.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.senac.sc.meuspedidos.daoImpl.CategoriaDao;
import br.senac.sc.meuspedidos.model.Categoria;
import br.senac.sc.meuspedidos.util.FacesUtil;

@ViewScoped
@ManagedBean
public class PesquisaCategoriaBean {

	private Categoria categoria;
	private CategoriaDao dao;
	private List<Categoria> categorias;

	public void inicializar() {
		this.dao = new CategoriaDao();
		categoria = new Categoria();
		buscarCategoria();

	}

	public void limpar() {
		categoria = new Categoria();
	}

	public void buscarCategoria() {
		categorias = dao.listar();
	}
	
	public void filtroCategoria() {
		
		categorias = dao.pesquisar(categoria.getDescricao());
	}

	public void excluirBD() {
		dao.excluir(categoria);
		buscarCategoria();
		FacesUtil.addInfoMessages("Categoria excluida com sucesso!!");
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

}
