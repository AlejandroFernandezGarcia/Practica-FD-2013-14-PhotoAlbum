package es.udc.fi.dc.photoalbum.hibernate;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class LikeAndDislikeDaoImpl extends HibernateDaoSupport implements LikeAndDislikeDao {
	
	public void create(LikeAndDislike likeAndDislike) {
		getHibernateTemplate().save(likeAndDislike);
		
	}

	public void delete(LikeAndDislike likeAndDislike) {
		getHibernateTemplate().delete(likeAndDislike);
		
	}
	
	public void update(LikeAndDislike likeandDislike) {
		getHibernateTemplate().update(likeandDislike);
	}
	
}
