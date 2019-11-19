package br.senac.sc.meuspedidos.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.senac.sc.meuspedidos.daoImpl.UsuarioDao;
import br.senac.sc.meuspedidos.model.Usuario;

@FacesConverter(forClass = Usuario.class)
public class UsuarioConverter implements Converter {

	private UsuarioDao dao;

	public UsuarioConverter() {
		dao = new UsuarioDao();
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
		Usuario usuario = (Usuario) value;

		if (usuario.getId() == null) {
			return null;
		}
		return usuario.getId().toString();
	}

}
