package es.udc.fi.dc.photoalbum.model.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import es.udc.fi.dc.photoalbum.util.utils.MD5;

/**
 * {@link UserDao} Hibernate implementation.
 */
public class UserDaoImpl extends HibernateDaoSupport implements
        UserDao {

    @Override
    public void create(User user) {
        getHibernateTemplate().save(user);
    }

    @Override
    public void delete(User user) {
        getHibernateTemplate().delete(user);
    }

    @Override
    public void update(User user) {
        getHibernateTemplate().update(user);
    }

    @Override
    public User getUser(String email, String password) {
        @SuppressWarnings("unchecked")
        List<User> list = (ArrayList<User>) getHibernateTemplate()
                .findByCriteria(
                        DetachedCriteria
                                .forClass(User.class)
                                .setResultTransformer(
                                        Criteria.DISTINCT_ROOT_ENTITY)
                                .add(Restrictions.eq("email", email)
                                        .ignoreCase())
                                .add(Restrictions.eq("password",
                                        MD5.getHash(password))));
        if (list.size() == 1) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public User getUser(User userEmail) {
        @SuppressWarnings("unchecked")
        List<User> list = (ArrayList<User>) getHibernateTemplate()
                .findByCriteria(
                        DetachedCriteria
                                .forClass(User.class)
                                .add(Restrictions.eq("email",
                                        userEmail.getEmail()))
                                .setResultTransformer(
                                        Criteria.DISTINCT_ROOT_ENTITY));
        if (list.size() == 1) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public User getById(Integer id) {
        return getHibernateTemplate().get(User.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getUsersSharingWith(int userId) {

        DetachedCriteria dc = DetachedCriteria
                .forClass(User.class, "user")
                .addOrder(Order.asc("user.email"))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        DetachedCriteria fromSharedAlbumsDc = DetachedCriteria
                .forClass(AlbumShareInformation.class, "asi")
                .createAlias("asi.user", "asi_us")
                .createAlias("asi.album", "asi_al")
                .createAlias("asi_al.user", "asi_al_us")
                .add(Restrictions.eq("asi_us.id", userId))
                .setProjection(Projections.property("asi_al_us.id"));
        Criterion fromSharedAlbumsCr = Subqueries.propertyIn(
                "user.id", fromSharedAlbumsDc);

        DetachedCriteria fromSharedFilesDc = DetachedCriteria
                .forClass(FileShareInformation.class, "fsi")
                .createAlias("fsi.user", "fsi_us")
                .createAlias("fsi.file", "fsi_fi")
                .createAlias("fsi_fi.album", "fsi_fi_al")
                .createAlias("fsi_fi_al.user", "fsi_fi_al_us")
                .add(Restrictions.eq("fsi_us.id", userId))
                .setProjection(
                        Projections.property("fsi_fi_al_us.id"));
        Criterion fromSharedFilesCr = Subqueries.propertyIn(
                "user.id", fromSharedFilesDc);

        return (ArrayList<User>) getHibernateTemplate()
                .findByCriteria(
                        dc.add(Restrictions.disjunction()
                                .add(fromSharedAlbumsCr)
                                .add(fromSharedFilesCr)));
    }
}
