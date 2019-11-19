package br.senac.sc.meuspedidos.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.senac.sc.meuspedidos.daoImpl.ClienteDao;
import br.senac.sc.meuspedidos.daoImpl.PedidoDao;
import br.senac.sc.meuspedidos.daoImpl.ProdutoDao;
import br.senac.sc.meuspedidos.model.Cliente;
import br.senac.sc.meuspedidos.model.FormaPagamento;
import br.senac.sc.meuspedidos.model.ItemPedido;
import br.senac.sc.meuspedidos.model.Pedido;
import br.senac.sc.meuspedidos.model.Produto;
import br.senac.sc.meuspedidos.model.StatusPedido;
import br.senac.sc.meuspedidos.util.FacesUtil;

@ManagedBean
@ViewScoped
public class CadastroPedidoBean {

	private Pedido pedido;
	private PedidoDao dao;
	////////////////////////////////////////
	private Produto produto;
	private ProdutoDao pdao;
	private List<Produto> listaProdutos;
	//////////////////////////////////////////
	private ClienteDao cdao;
	private List<Cliente> listaClientes;
	///////////////////////////////////////
	private FormaPagamento[] formas;
	private StatusPedido statusPedido;
	////////////
	private ItemPedido itemPedido;
	private List<ItemPedido> listaAdicionarProdutos;
	///////////////
	private double valorTotalCampo;
	//
	private double somaSub = 0d;
	/////
	private boolean ativo = true;
	////
	private Pedido pedidoComparacao;

	public void limpar() {
		pedido = new Pedido();
		produto = new Produto();
		listaProdutos = new ArrayList<>();
		listaClientes = new ArrayList<>();
		itemPedido = new ItemPedido();
		listaAdicionarProdutos = new ArrayList<>();
		pedidoComparacao = new Pedido();
	}

	public void inicializar() {
		if (pedido == null) {
			pedido = new Pedido();
			pedidoComparacao = new Pedido();
			produto = new Produto();
			itemPedido = new ItemPedido();
			listaClientes = new ArrayList<>();
			listaProdutos = new ArrayList<>();
			listaAdicionarProdutos = new ArrayList<>();
			popularFormasPagamentoComboBox();
			popularStatusComboBox();
			popularDataCriacao();
			cdao = new ClienteDao();
			pdao = new ProdutoDao();
		}
		dao = new PedidoDao();
		popularNumero();
		popularClienteComboBox();
		popularProdutoComboBox();

	}

	public void adicionarValorTotal() {
		valorTotalCampo = itemPedido.getValorTotal();
		msgEstoqueMaximo();

	}

	public void msgEstoqueMaximo() {
		if (itemPedido.getQuantidade() > produto.getEstoque()) {
			FacesUtil.addWarningMessages("Quantidade maior que o estoque atual !! Favor verificar !");
		}
	}

	public void popularProdutoCampos() {
		try {
			Produto pro = pdao.buscarPorId(produto.getId());
			produto.setEstoque(pro.getEstoque());
			itemPedido.setProduto(pro);
			itemPedido.setValorUnitario(pro.getValor());
		} catch (Exception e) {
			return;
		}

	}

	public void popularNumero() {
		if (dao.pegarNumero() == null) {
			pedido.setNumero(1l);
		} else {
			pedido.setNumero(dao.pegarNumero() + 1);
		}
	}

	public void popularDataCriacao() {
		pedido.setDataCriacao(new Date());
	}

	public void popularFormasPagamentoComboBox() {
		formas = FormaPagamento.values();
	}

	public void popularStatusComboBox() {
		statusPedido = StatusPedido.ORCAMENTO;
	}

	public void popularClienteComboBox() {
		listaClientes = cdao.listar();
	}

	public void popularProdutoComboBox() {
		listaProdutos = pdao.listar();
		for (int i = 0; i < listaProdutos.size(); i++) {
			if (listaProdutos.get(i).getEstoque() == 0) {
				listaProdutos.remove(i);
			}
		}

	}

	public void valorTotalPedido() {
		if (pedido.getValorFrete() == null) {
			pedido.setValorFrete(0d);
			pedido.setValorTotal(pedido.getValorFrete() + calculoSubtotal());
		} else {
			pedido.setValorTotal(pedido.getValorFrete() + calculoSubtotal());
		}

	}

	public double calculoSubtotal() {
		if (listaAdicionarProdutos.isEmpty()) {
			somaSub = 0d;
			return 0;
		}
		somaSub = 0d;
		for (ItemPedido item : listaAdicionarProdutos) {
			somaSub += item.getValorTotal();
		}
		return somaSub;
	}

	public void atualizarValoresExcluir() {
		removerItemPedido();
		calculoSubtotal();
		valorTotalPedido();
	}

	public void adicionarProduto() {
		itemPedido.setValorUnitario(itemPedido.getProduto().getValor());

		if (listaAdicionarProdutos.size() < 1) {
			listaAdicionarProdutos.add(itemPedido);
		} else {
			for (ItemPedido i : listaAdicionarProdutos) {
				if (i.getProduto().getNome().equalsIgnoreCase(itemPedido.getProduto().getNome())) {

					FacesUtil.addWarningMessages("Já existe esse produto na lista!! Favor verificar dados !");
					itemPedido = new ItemPedido();
					return;
				}
			}
			listaAdicionarProdutos.add(itemPedido);
			itemPedido = new ItemPedido();
			calculoSubtotal();
			valorTotalPedido();
			return;
		}
		itemPedido = new ItemPedido();
		calculoSubtotal();
		valorTotalPedido();
	}

	public void deshabilitado() {
		ativo = false;
	}

	public void statusEmitir() {
		statusPedido = StatusPedido.EMITIDO;
		FacesUtil.addInfoMessages("Emitido com sucesso !!");
		pedido.setStatus(statusPedido);
		if (pedido.getNumero() == pedidoComparacao.getNumero()) {
			dao.excluir(pedidoComparacao);
			pedidoComparacao = dao.salvar(pedido);
		}
	}

	public void statusCancelado() {
		statusPedido = StatusPedido.CANCELADO;
		FacesUtil.addInfoMessages("Quantidades dos estoques retornadas com sucesso !!");
		pedido.setStatus(statusPedido);
		if (pedido.getNumero() == pedidoComparacao.getNumero()) {
			dao.excluir(pedidoComparacao);
			retornarEstoqueProdutos();
			dao.salvar(pedido);
		}
	}

	public void retirarEstoqueProdutos() {
		for (ItemPedido item : listaAdicionarProdutos) {
			item.getProduto().setEstoque(item.getProduto().getEstoque() - item.getQuantidade());
			pdao.salvar(item.getProduto());
		}
	}

	public void retornarEstoqueProdutos() {
		for (ItemPedido item : listaAdicionarProdutos) {
			item.getProduto().setEstoque(item.getProduto().getEstoque() + item.getQuantidade());
			pdao.salvar(item.getProduto());
		}
	}

	public void salvarBD() {
		deshabilitado();
		for (ItemPedido item : listaAdicionarProdutos) {
			item.setPedido(pedido);
		}
		retirarEstoqueProdutos();
		pedido.setItens(listaAdicionarProdutos);
		FacesUtil.addInfoMessages("Pedido salvo com sucesso!!");
		pedidoComparacao = dao.salvar(pedido);
	}

	public void removerItemPedido() {
		listaAdicionarProdutos.remove(itemPedido);
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<Produto> getListaProdutos() {
		return listaProdutos;
	}

	public void setListaProdutos(List<Produto> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}

	public List<Cliente> getListaClientes() {
		return listaClientes;
	}

	public void setListaClientes(List<Cliente> listaClientes) {
		this.listaClientes = listaClientes;
	}

	public FormaPagamento[] getFormas() {
		return formas;
	}

	public void setFormas(FormaPagamento[] formas) {
		this.formas = formas;
	}

	public StatusPedido getStatusPedido() {
		return statusPedido;
	}

	public void setStatusPedido(StatusPedido statusPedido) {
		this.statusPedido = statusPedido;
	}

	public ItemPedido getItemPedido() {
		return itemPedido;
	}

	public void setItemPedido(ItemPedido itemPedido) {
		this.itemPedido = itemPedido;
	}

	public double getValorTotalProdutos() {
		return valorTotalCampo;
	}

	public void setValorTotalProdutos(double valorTotalCampo) {
		this.valorTotalCampo = valorTotalCampo;
	}

	public List<ItemPedido> getListaAdicionarProdutos() {
		return listaAdicionarProdutos;
	}

	public void setListaAdicionarProdutos(List<ItemPedido> listaAdicionarProdutos) {
		this.listaAdicionarProdutos = listaAdicionarProdutos;
	}

	public double getValorTotalCampo() {
		return valorTotalCampo;
	}

	public void setValorTotalCampo(double valorTotalCampo) {
		this.valorTotalCampo = valorTotalCampo;
	}

	public double getSomaSub() {
		return somaSub;
	}

	public void setSomaSub(double somaSub) {
		this.somaSub = somaSub;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public Pedido getPedidoComparacao() {
		return pedidoComparacao;
	}

	public void setPedidoComparacao(Pedido pedidoComparacao) {
		this.pedidoComparacao = pedidoComparacao;
	}

}
