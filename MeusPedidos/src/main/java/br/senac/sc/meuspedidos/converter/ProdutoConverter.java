package br.senac.sc.meuspedidos.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.senac.sc.meuspedidos.daoImpl.ProdutoDao;
import br.senac.sc.meuspedidos.model.Produto;

@FacesConverter(forClass = Produto.class)
public class ProdutoConverter implements Converter {

	private ProdutoDao dao;

	public ProdutoConverter() {
		dao = new ProdutoDao();
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		if (value.isEmpty() || value == null) {
			return null;
		}
		return dao.buscarPorId(new Long(value));
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value == null) {
			return null;
		}
		Produto produto = (Produto) value;

		if (produto.getId() == null) {
			return null;
		}
		return produto.getId().toString();
	}

}
