package br.senac.sc.meuspedidos.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import br.senac.sc.meuspedidos.dao.DaoI;
import br.senac.sc.meuspedidos.model.Cliente;
import br.senac.sc.meuspedidos.util.JpaUtil;

public class ClienteDao implements DaoI<Cliente> {

	@Override
	public List<Cliente> listar() {
		EntityManager em = JpaUtil.getEntityManager();
		TypedQuery<Cliente> query = em.createQuery("from Cliente", Cliente.class);
		List<Cliente> resultList = query.getResultList();
		em.close();
		return resultList;
	}

	@Override
	public List<Cliente> pesquisar(String txt) {
		EntityManager manager = JpaUtil.getEntityManager();
		TypedQuery<Cliente> queryBusca = manager.createQuery("from Cliente c where c.nome like :valor", Cliente.class);
		queryBusca.setParameter("valor", "%" + txt + "%");

		if (!queryBusca.getResultList().isEmpty()) {
			return queryBusca.getResultList();
		} else {
			return listar();
		}
	}

	@Override
	public Cliente buscarPorId(Long id) {
		EntityManager em = JpaUtil.getEntityManager();
		try {
			return em.find(Cliente.class, id);
		} finally {
			em.close();
		}

	}

	@Override
	public Cliente salvar(Cliente cli) {
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();

		try {
			trx.begin();
			cli = manager.merge(cli);
			trx.commit();
		} finally {
			if (trx.isActive()) {
				trx.rollback();
			}
			manager.close();
		}
		return cli;
	}

	@Override
	public void alterar(Cliente cli) {
		// TODO Auto-generated method stub

	}

	@Override
	public void excluir(Cliente cliente) {
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();

		try {
			trx.begin();
			manager.remove(manager.find(Cliente.class, cliente.getId()));
			trx.commit();
		} finally {
			if (trx.isActive()) {
				trx.rollback();
			}
			manager.close();
		}
	}

	public List<Cliente> listarEnderecos(Long id) {
		EntityManager manager = JpaUtil.getEntityManager();
		TypedQuery<Cliente> query = manager.createQuery("from Cliente c JOIN fetch c.enderecos where c.id = "+id, Cliente.class);
		try {
			return query.getResultList();
		} finally {
			manager.close();
		}
	}
	
	}
