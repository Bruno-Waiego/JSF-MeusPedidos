package br.senac.sc.meuspedidos.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import br.senac.sc.meuspedidos.dao.DaoI;
import br.senac.sc.meuspedidos.model.Produto;
import br.senac.sc.meuspedidos.util.JpaUtil;

public class ProdutoDao implements DaoI<Produto> {

	@Override
	public List<Produto> listar() {
		EntityManager em = JpaUtil.getEntityManager();
		TypedQuery<Produto> query = em.createQuery("from Produto", Produto.class);
		List<Produto> resultList = query.getResultList();
		em.close();
		return resultList;
	}

	@Override
	public List<Produto> pesquisar(String txt) {
		EntityManager manager = JpaUtil.getEntityManager();
		TypedQuery<Produto> queryBusca = manager.createQuery("from Produto p where p.nome like :valor or p.id like :valor", Produto.class);
		queryBusca.setParameter("valor", "%" + txt + "%");

		if (!queryBusca.getResultList().isEmpty()) {
			return queryBusca.getResultList();
		} else {
			return listar();
		}
	}

	@Override
	public Produto buscarPorId(Long id) {
		EntityManager em = JpaUtil.getEntityManager();
		try {
			return em.find(Produto.class, id);
		} finally {
			em.close();
		}

	}

	@Override
	public Produto salvar(Produto produto) {
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();

		try {
			trx.begin();
			produto = manager.merge(produto);
			trx.commit();
		} finally {
			if (trx.isActive()) {
				trx.rollback();
			}
			manager.close();
		}
		return produto;
	}

	@Override
	public void alterar(Produto obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void excluir(Produto produto) {
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();

		try {
			trx.begin();
			manager.remove(manager.find(Produto.class, produto.getId()));
			trx.commit();
		} finally {
			if (trx.isActive()) {
				trx.rollback();
			}
			manager.close();
		}
	}
}
