package br.senac.sc.meuspedidos.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import br.senac.sc.meuspedidos.dao.DaoI;
import br.senac.sc.meuspedidos.model.Endereco;
import br.senac.sc.meuspedidos.util.JpaUtil;

public class EnderecoDao implements DaoI<Endereco> {

	@Override
	public List<Endereco> listar() {
		EntityManager em = JpaUtil.getEntityManager();
		TypedQuery<Endereco> query = em.createQuery("from Endereco", Endereco.class);
		List<Endereco> resultList = query.getResultList();
		em.close();
		return resultList;
	}

	@Override
	public List<Endereco> pesquisar(String txt) {
		EntityManager manager = JpaUtil.getEntityManager();
		TypedQuery<Endereco> queryBusca = manager.createQuery("from Endereco e where e.nome like :valor", Endereco.class);
		queryBusca.setParameter("valor", "%" + txt + "%");

		if (!queryBusca.getResultList().isEmpty()) {
			return queryBusca.getResultList();
		} else {
			return listar();
		}
	}

	@Override
	public Endereco buscarPorId(Long id) {
		EntityManager em = JpaUtil.getEntityManager();
		try {
			return em.find(Endereco.class, id);
		} finally {
			em.close();
		}

	}

	@Override
	public Endereco salvar(Endereco endereco) {
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();

		try {
			trx.begin();
			endereco = manager.merge(endereco);
			trx.commit();
		} finally {
			if (trx.isActive()) {
				trx.rollback();
			}
			manager.close();
		}
		return endereco;
	}

	@Override
	public void alterar(Endereco endereco) {
		// TODO Auto-generated method stub

	}

	@Override
	public void excluir(Endereco endereco) {
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();

		try {
			trx.begin();
			manager.remove(manager.find(Endereco.class, endereco.getId()));
			trx.commit();
		} finally {
			if (trx.isActive()) {
				trx.rollback();
			}
			manager.close();
		}
	}
}
