package br.senac.sc.meuspedidos.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import br.senac.sc.meuspedidos.daoImpl.PedidoDao;
import br.senac.sc.meuspedidos.model.Pedido;

@FacesConverter(forClass = Pedido.class)
public class PedidoConverter implements Converter {

	private PedidoDao dao;

	public PedidoConverter() {
		dao = new PedidoDao();
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
		Pedido pedido = (Pedido) value;

		if (pedido.getId() == null) {
			return null;
		}
		return pedido.getId().toString();
	}

}
