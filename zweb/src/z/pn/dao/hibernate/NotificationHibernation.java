package z.pn.dao.hibernate;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import z.pn.dao.NotificationDao;
import z.pn.model.Notification;
/**
 * 推送消息dao实现类
 * @author Administrator
 *
 */
@Repository
public class NotificationHibernation extends HibernateDaoSupport implements
		NotificationDao {
    @Resource
    public void setSessionFactory0(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }
	
	public void save(Notification notification) {
		getHibernateTemplate().save(notification);
	}

	public void remove(Long id) {
		getHibernateTemplate().delete(id);
	}

	public void update(Notification notification) {
		getHibernateTemplate().saveOrUpdate(notification);
	}

	public Notification get(Long id) {
		return (Notification) getHibernateTemplate().get(Notification.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Notification> getAll() {
		return (List<Notification>) getHibernateTemplate().find("from Notification where 1=1");
	}
	
	@SuppressWarnings("unchecked")
	public List<Notification> getByName(String name) throws Exception {
		List<Notification> notifications=(List<Notification>) getHibernateTemplate().find("from Notification where 1=1");
		if(notifications==null||notifications.size()==0){
			throw new Exception("未找到该用户名("+name+")对应的Notifications");
		}else{
			return notifications;
		}
	}

}
