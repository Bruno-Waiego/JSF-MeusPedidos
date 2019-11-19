package br.senac.sc.meuspedidos.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import br.senac.sc.meuspedidos.dao.DaoI;
import br.senac.sc.meuspedidos.model.Categoria;
import br.senac.sc.meuspedidos.util.JpaUtil;

public class CategoriaDao implements DaoI<Categoria> {

	@Override
	public List<Categoria> listar() {
		EntityManager em = JpaUtil.getEntityManager();
		TypedQuery<Categoria> query = em.createQuery("from Categoria", Categoria.class);
		List<Categoria> resultList = query.getResultList();
		em.close();
		return resultList;
	}

	@Override
	public Categoria salvar(Categoria categoria) {

		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();

		try {
			trx.begin();
			categoria = manager.merge(categoria);
			trx.commit();
		} finally {
			if (trx.isActive()) {
				trx.rollback();
			}
			manager.close();
		}
		return categoria;
	}

	@Override
	public void alterar(Categoria categoria) {
		// TODO Auto-generated method stub
	}

	@Override
	public void excluir(Categoria categoria) {
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();

		try {
			trx.begin();
			manager.remove(manager.find(Categoria.class, categoria.getId()));
			trx.commit();
		} finally {
			if (trx.isActive()) {
				trx.rollback();
			}
			manager.close();
		}
	}

	@Override
	public List<Categoria> pesquisar(String txt) {
		EntityManager manager = JpaUtil.getEntityManager();
		TypedQuery<Categoria> queryBusca = manager.createQuery("from Categoria c where c.descricao like :valor",
				Categoria.class);
		queryBusca.setParameter("valor", "%" + txt + "%");

		if (!queryBusca.getResultList().isEmpty()) {
			return queryBusca.getResultList();
		} else {
			return listar();
		}
	}

	@Override
	public Categoria buscarPorId(Long id) {
		EntityManager em = JpaUtil.getEntityManager();
		try {
			return em.find(Categoria.class, id);
		} finally {
			em.close();
		}

	}
}
