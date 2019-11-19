package br.senac.sc.meuspedidos.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.senac.sc.meuspedidos.daoImpl.CategoriaDao;
import br.senac.sc.meuspedidos.model.Categoria;

@FacesConverter(forClass = Categoria.class)
public class CategoriaConverter implements Converter {

	private CategoriaDao dao;

	public CategoriaConverter() {
		dao = new CategoriaDao();
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
		Categoria categoria = (Categoria) value;

		if (categoria.getId() == null) {
			return null;
		}
		return categoria.getId().toString();
	}

}
