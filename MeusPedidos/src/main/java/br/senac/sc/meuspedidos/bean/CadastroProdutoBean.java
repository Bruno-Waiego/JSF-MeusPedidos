package br.senac.sc.meuspedidos.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.senac.sc.meuspedidos.daoImpl.CategoriaDao;
import br.senac.sc.meuspedidos.daoImpl.ProdutoDao;
import br.senac.sc.meuspedidos.model.Categoria;
import br.senac.sc.meuspedidos.model.Produto;
import br.senac.sc.meuspedidos.util.FacesUtil;

@ManagedBean
@ViewScoped
public class CadastroProdutoBean {

	private Produto produto;

	private ProdutoDao dao;

	private CategoriaDao cdao;

	private List<Categoria> listaCategoria;

	public void limpar() {
		produto = new Produto();
	}

	public void inicializar() {
		if (produto == null) {
			produto = new Produto();
			listaCategoria = new ArrayList<>();
		}
		dao = new ProdutoDao();
		cdao = new CategoriaDao();
		listarComboBox();
	}

	public void listarComboBox() {
		listaCategoria = cdao.listar();
	}

	public void salvarBD() {
		dao.salvar(produto);
		limpar();
		FacesUtil.addInfoMessages("Produto salvo com sucesso!!");

	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<Categoria> getListaCategoria() {
		return listaCategoria;
	}

	public void setListaCategoria(List<Categoria> listaCategoria) {
		this.listaCategoria = listaCategoria;
	}
	
	
	
}
