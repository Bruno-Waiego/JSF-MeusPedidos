package br.senac.sc.meuspedidos.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.senac.sc.meuspedidos.daoImpl.ProdutoDao;
import br.senac.sc.meuspedidos.model.Produto;
import br.senac.sc.meuspedidos.util.FacesUtil;

@ViewScoped
@ManagedBean
public class PesquisaProdutoBean {

	private Produto produto;
	private ProdutoDao dao;
	private List<Produto> produtos;

	public void inicializar() {
		this.dao = new ProdutoDao();
		produto = new Produto();
		buscarProduto();

	}

	public void limpar() {
		produto = new Produto();
	}

	public void buscarProduto() {
		produtos = dao.listar();
	}

	public void filtroProduto() {

		produtos = dao.pesquisar(produto.getNome());

	}

	public void excluirBD() {
		System.out.println(produto.getId());
		dao.excluir(produto);
		buscarProduto();
		FacesUtil.addInfoMessages("Produto excluido com sucesso!!");
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}


}
