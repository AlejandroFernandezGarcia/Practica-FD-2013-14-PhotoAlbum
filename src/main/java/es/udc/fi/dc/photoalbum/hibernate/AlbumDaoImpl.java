package es.udc.fi.dc.photoalbum.hibernate;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import es.udc.fi.dc.photoalbum.utils.PrivacyLevel;

import java.util.ArrayList;

public class AlbumDaoImpl extends HibernateDaoSupport implements AlbumDao {

	public void create(Album album) {
		getHibernateTemplate().save(album);
	}

	public void delete(Album album) {
		getHibernateTemplate().delete(album);
	}

	public Album getAlbum(String name, int userId) {
		@SuppressWarnings("unchecked")
		ArrayList<Album> list = (ArrayList<Album>) getHibernateTemplate()
				.findByCriteria(
						DetachedCriteria
								.forClass(Album.class)
								.add(Restrictions.eq("name", name))
								.createCriteria("user")
								.add(Restrictions.eq("id", userId))
								.setResultTransformer(
										Criteria.DISTINCT_ROOT_ENTITY));
		if (list.size() == 1) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public void rename(Album album, String newName) {
		album.setName(newName);
		getHibernateTemplate().update(album);
	}

	public Album getById(Integer id) {
		return getHibernateTemplate().get(Album.class, id);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Album> getAlbums(Integer id) {
		return (ArrayList<Album>) getHibernateTemplate().findByCriteria(
				DetachedCriteria.forClass(Album.class).createCriteria("user")
						.add(Restrictions.eq("id", id))
						.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY));
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Album> getPublicAlbums() {
		return (ArrayList<Album>) getHibernateTemplate().findByCriteria(
				DetachedCriteria.forClass(Album.class).createCriteria("user")
						.add(Restrictions.like("privacyLevel", PrivacyLevel.PRIVATE, MatchMode.EXACT))
						.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY));
	}
	
	public void changePrivacyLevel(Album album, String privacyLevel) {
		album.setPrivacyLevel(privacyLevel);
		getHibernateTemplate().update(album);
	}

//	@SuppressWarnings("unchecked")
//	public ArrayList<Album> getByIdAndMinPrivacyLevel(Integer id,
//			String privacyLevel) {
//
//		Criterion privacyLevelCriterion = Restrictions.like("privacity",
//				PrivacyLevel.PUBLIC, MatchMode.EXACT);
//		boolean getPrivates = PrivacyLevel.PRIVATE.equals(privacyLevel);
//		boolean getShareables = PrivacyLevel.SHAREABLE.equals(privacyLevel)
//				|| getPrivates;
//		if (getShareables)
//			privacyLevelCriterion = Restrictions.or(privacyLevelCriterion,
//					Restrictions.like("privacity", PrivacyLevel.SHAREABLE,
//							MatchMode.EXACT));
//		else if (getPrivates)
//			privacyLevelCriterion = Restrictions.or(privacyLevelCriterion,
//					Restrictions.like("privacity", PrivacyLevel.PRIVATE,
//							MatchMode.EXACT));
//
//		return (ArrayList<Album>) getHibernateTemplate().findByCriteria(
//				DetachedCriteria.forClass(Album.class).createCriteria("user")
//						.add(Restrictions.eq("id", id))
//						.add(privacyLevelCriterion)
//						.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY));
//	}
}
