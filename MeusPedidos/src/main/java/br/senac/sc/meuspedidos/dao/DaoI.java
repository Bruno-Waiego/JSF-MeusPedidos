package br.senac.sc.meuspedidos.dao;

import java.util.List;

public interface DaoI<T> {

	public T salvar(T obj);
	public void alterar(T obj);
	public void excluir(T obj);
	public List<T> listar();
	public T buscarPorId(Long id);
	public List<T> pesquisar(String txt);
}
