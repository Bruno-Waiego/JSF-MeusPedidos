package br.senac.sc.meuspedidos.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.senac.sc.meuspedidos.daoImpl.ClienteDao;
import br.senac.sc.meuspedidos.model.Cliente;
import br.senac.sc.meuspedidos.model.Endereco;
import br.senac.sc.meuspedidos.util.FacesUtil;

@ViewScoped
@ManagedBean
public class PesquisaClienteBean {

	private Cliente cliente;
	private ClienteDao dao;
	private List<Cliente> clientes;

	private List<Endereco> listaEnderecos;


	public void inicializar() {
		dao = new ClienteDao();
		cliente = new Cliente();
		listaEnderecos = new ArrayList<>();
		buscarCliente();

	}

	public void limpar() {
		cliente = new Cliente();
	}

	public void buscarCliente() {
		clientes = dao.listar();
	}

	public void filtroCliente() {
		clientes = dao.pesquisar(cliente.getNome());
	}

	public void excluirBD() {
		dao.excluir(cliente);
		buscarCliente();
		FacesUtil.addInfoMessages("Cliente excluido com sucesso!!");
	}

	public void pesquisarEndereco() {
		List<Cliente> lista2 = dao.listarEnderecos(cliente.getId());
		listaEnderecos = lista2.get(0).getEnderecos();

	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public List<Endereco> getListaEnderecos() {
		return listaEnderecos;
	}

	public void setListaEnderecos(List<Endereco> listaEnderecos) {
		this.listaEnderecos = listaEnderecos;
	}
}
