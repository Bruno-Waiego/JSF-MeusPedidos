package br.senac.sc.meuspedidos.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.senac.sc.meuspedidos.daoImpl.ClienteDao;
import br.senac.sc.meuspedidos.model.Cliente;
import br.senac.sc.meuspedidos.model.Endereco;
import br.senac.sc.meuspedidos.model.TipoPessoa;
import br.senac.sc.meuspedidos.util.FacesUtil;

@ManagedBean
@ViewScoped
public class CadastroClienteBean {

	private Cliente cliente;

	private ClienteDao dao;

	private Endereco endereco;

	private List<Endereco> listaEnderecos;

	private TipoPessoa[] tipos;

	public void limpar() {
		cliente = new Cliente();
		endereco = new Endereco();
		listaEnderecos = new ArrayList<>();

	}

	public void inicializar() {
		if (cliente == null) {
			cliente = new Cliente();
			endereco = new Endereco();
			listaEnderecos = new ArrayList<>();
			popularComboBox();
			dao = new ClienteDao();
		} else {
			dao = new ClienteDao();
			editarCliente();
		}
	}

	public void popularComboBox() {
		tipos = TipoPessoa.values();
	}

	public void editarCliente() {
		endereco = new Endereco();
		listaEnderecos = new ArrayList<>();
		popularComboBox();
		try {
			List<Cliente> lista2 = dao.listarEnderecos(cliente.getId());
			listaEnderecos = lista2.get(0).getEnderecos();
		} catch (Exception e) {
			FacesUtil.addInfoMessages("Cliente não tem registro de endereços !! Adicionar caso necessário");
			return;
		}
	}

	public void salvarBD() {
		cliente.setEnderecos(listaEnderecos);
		dao.salvar(cliente);
		limpar();
		FacesUtil.addInfoMessages("Cliente salvo com sucesso!!");

	}

	public void adicionarEndereco() {
		listaEnderecos.add(endereco);
		endereco = new Endereco();
	}

	public void removerEndereco() {
		listaEnderecos.remove(endereco);
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<Endereco> getListaEnderecos() {
		return listaEnderecos;
	}

	public void setListaEnderecos(List<Endereco> listaEnderecos) {
		this.listaEnderecos = listaEnderecos;
	}

	public TipoPessoa[] getTipos() {
		return tipos;
	}

	public void setTipos(TipoPessoa[] tipos) {
		this.tipos = tipos;
	}
}
