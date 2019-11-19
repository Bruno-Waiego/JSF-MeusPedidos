package br.senac.sc.meuspedidos.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.senac.sc.meuspedidos.daoImpl.CategoriaDao;
import br.senac.sc.meuspedidos.model.Categoria;
import br.senac.sc.meuspedidos.util.FacesUtil;

@ManagedBean
@ViewScoped
public class CadastroCategoriaBean {

	private Categoria categoria;

	private CategoriaDao dao;

	public void limpar() {
		categoria = new Categoria();
	}

	public void inicializar() {
		if (categoria == null) {
			categoria = new Categoria();
		}
		dao = new CategoriaDao();
	}

	public void salvarBD() {
		dao.salvar(categoria);
		limpar();
		FacesUtil.addInfoMessages("Categoria salva com sucesso!!");

	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

}
