package br.senac.sc.meuspedidos.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.senac.sc.meuspedidos.dao.DaoI;
import br.senac.sc.meuspedidos.model.Usuario;
import br.senac.sc.meuspedidos.util.JpaUtil;

public class UsuarioDao implements DaoI<Usuario> {

	@Override
	public List<Usuario> listar() {
		EntityManager em = JpaUtil.getEntityManager();
		TypedQuery<Usuario> query = em.createQuery("from Usuario", Usuario.class);
		List<Usuario> resultList = query.getResultList();
		em.close();
		return resultList;
	}

	@Override
	public List<Usuario> pesquisar(String txt) {
		EntityManager manager = JpaUtil.getEntityManager();
		TypedQuery<Usuario> queryBusca = manager.createQuery("from Usuario u where u.nome like :valor", Usuario.class);
		queryBusca.setParameter("valor", "%" + txt + "%");

		if (!queryBusca.getResultList().isEmpty()) {
			return queryBusca.getResultList();
		} else {
			return listar();
		}
	}

	@Override
	public Usuario buscarPorId(Long id) {
		EntityManager em = JpaUtil.getEntityManager();
		try {
			return em.find(Usuario.class, id);
		} finally {
			em.close();
		}

	}

	@Override
	public Usuario salvar(Usuario usuario) {
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();

		try {
			trx.begin();
			usuario = manager.merge(usuario);
			trx.commit();
		} finally {
			if (trx.isActive()) {
				trx.rollback();
			}
			manager.close();
		}
		return usuario;
	}

	@Override
	public void alterar(Usuario usuario) {
		// TODO Auto-generated method stub

	}

	@Override
	public void excluir(Usuario usuario) {
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();

		try {
			trx.begin();
			manager.remove(manager.find(Usuario.class, usuario.getId()));
			trx.commit();
		} finally {
			if (trx.isActive()) {
				trx.rollback();
			}
			manager.close();
		}
	}

	public Usuario verificarUsuario(String email, String senha) {
		EntityManager manager = JpaUtil.getEntityManager();

		TypedQuery<Usuario> queryBusca = manager
				.createQuery("from Usuario u where u.email = :email" + " and u.senha = :senha and u.ativo = 1", Usuario.class);
		queryBusca.setParameter("email", email);
		queryBusca.setParameter("senha", senha);
		try {
			return queryBusca.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} finally {
			manager.close();
		}
	}

}
