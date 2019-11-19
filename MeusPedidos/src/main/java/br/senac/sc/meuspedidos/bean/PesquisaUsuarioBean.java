package br.senac.sc.meuspedidos.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.senac.sc.meuspedidos.daoImpl.UsuarioDao;
import br.senac.sc.meuspedidos.model.Usuario;
import br.senac.sc.meuspedidos.util.FacesUtil;

@ViewScoped
@ManagedBean
public class PesquisaUsuarioBean {

	private Usuario usuario;
	private UsuarioDao dao;
	private List<Usuario> usuarios;

	public void inicializar() {
		dao = new UsuarioDao();
		usuario = new Usuario();
		buscarUsuario();

	}

	public void limpar() {
		usuario = new Usuario();
	}

	public void buscarUsuario() {
		usuarios = dao.listar();
	}

	public void filtroUsuario() {
		usuarios = dao.pesquisar(usuario.getNome());
	}

	public void excluirBD() {
		dao.excluir(usuario);
		buscarUsuario();
		FacesUtil.addInfoMessages("Usuário excluido com sucesso!!");
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	

}
