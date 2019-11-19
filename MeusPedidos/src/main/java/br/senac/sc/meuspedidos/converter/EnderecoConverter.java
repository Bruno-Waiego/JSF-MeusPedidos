package br.senac.sc.meuspedidos.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.senac.sc.meuspedidos.daoImpl.EnderecoDao;
import br.senac.sc.meuspedidos.model.Endereco;

@FacesConverter(forClass = Endereco.class)
public class EnderecoConverter implements Converter {

	private EnderecoDao dao;

	public EnderecoConverter() {
		dao = new EnderecoDao();
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
		Endereco endereco = (Endereco) value;

		if (endereco.getId() == null) {
			return null;
		}
		return endereco.getId().toString();
	}

}
