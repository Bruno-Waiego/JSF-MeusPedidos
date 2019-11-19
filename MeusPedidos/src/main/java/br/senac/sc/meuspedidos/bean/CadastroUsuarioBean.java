package br.senac.sc.meuspedidos.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.senac.sc.meuspedidos.daoImpl.UsuarioDao;
import br.senac.sc.meuspedidos.model.Usuario;
import br.senac.sc.meuspedidos.util.FacesUtil;

@ManagedBean
@ViewScoped
public class CadastroUsuarioBean {

	private Usuario usuario;

	private UsuarioDao dao;

	public void limpar() {
		usuario = new Usuario();
	}

	public void inicializar() {
		if (usuario == null) {
			usuario = new Usuario();
		}
		dao = new UsuarioDao();
	}

	public void salvarBD() {
		dao.salvar(usuario);
		limpar();
		FacesUtil.addInfoMessages("Usuário salvo com sucesso!!");

	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
