package br.senac.sc.meuspedidos.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import br.senac.sc.meuspedidos.dao.DaoI;
import br.senac.sc.meuspedidos.model.Pedido;
import br.senac.sc.meuspedidos.util.JpaUtil;

public class PedidoDao implements DaoI<Pedido> {

	@Override
	public List<Pedido> listar() {
		EntityManager em = JpaUtil.getEntityManager();
		TypedQuery<Pedido> query = em.createQuery("from Pedido", Pedido.class);
		List<Pedido> resultList = query.getResultList();
		em.close();
		return resultList;
	}

	@Override
	public List<Pedido> pesquisar(String txt) {
		EntityManager manager = JpaUtil.getEntityManager();
		TypedQuery<Pedido> queryBusca = manager.createQuery("from Pedido p where p.nome like :valor", Pedido.class);
		queryBusca.setParameter("valor", "%" + txt + "%");

		if (!queryBusca.getResultList().isEmpty()) {
			return queryBusca.getResultList();
		} else {
			return listar();
		}
	}

	@Override
	public Pedido buscarPorId(Long id) {
		EntityManager em = JpaUtil.getEntityManager();
		try {
			return em.find(Pedido.class, id);
		} finally {
			em.close();
		}

	}

	@Override
	public Pedido salvar(Pedido pedido) {
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();

		try {
			trx.begin();
			pedido = manager.merge(pedido);
			trx.commit();
		} finally {
			if (trx.isActive()) {
				trx.rollback();
			}
			manager.close();
		}
		return pedido;
	}

	@Override
	public void alterar(Pedido pedido) {
		// TODO Auto-generated method stub

	}

	@Override
	public void excluir(Pedido pedido) {
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();

		try {
			trx.begin();
			manager.remove(manager.find(Pedido.class, pedido.getId()));
			trx.commit();
		} finally {
			if (trx.isActive()) {
				trx.rollback();
			}
			manager.close();
		}
	}

	public List<Pedido> listarEnderecos(Long id) {
		EntityManager manager = JpaUtil.getEntityManager();
		TypedQuery<Pedido> query = manager.createQuery("from Pedido p JOIN fetch p.lista where p.id = " + id,
				Pedido.class);
		try {
			return query.getResultList();
		} finally {
			manager.close();
		}
	}

	public Long pegarNumero() {
		EntityManager manager = JpaUtil.getEntityManager();
		TypedQuery<Long> query = manager.createQuery("select max(numero) from Pedido ", Long.class);
		try {
			return query.getSingleResult();
		} finally {
			manager.close();
		}
	}
}
