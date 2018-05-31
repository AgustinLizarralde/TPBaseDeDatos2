package hibernateUtil;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Esta clase es solo para pruebas
 *
 */
public class MyEntityManager {
	
	private Session session;
	
	public MyEntityManager(Session session) {
		this.session = session;
	}

	public void create(Object o) {
		System.out.println("\n===== GUARDAR -------->." + o.toString());
		session.saveOrUpdate(o);
		System.out.println("\n------ END GUARDAR.");
	}
	
	public void update(Object o) {
		System.out.println("\n===== UPDATE -------->." + o.toString());
		session.saveOrUpdate(o);
		System.out.println("\n------ END UPDATE.");
	}
	
	public void delete(Object o) {
		System.out.println("\n===== BORRAR -------->." + o.toString());
		session.delete(o);
	}

	public List readAll(Class clazz) {
		Query queryResult;
		List list = null;
		System.out.println("\n =========== READ ALL " + clazz.getSimpleName());
		queryResult = session.createQuery("select e from " + clazz.getSimpleName() + " e");
		list = queryResult.list();
		System.out.println("\n------ END READ.");
		System.out.println(list.toString());
		return list;
	}

	public Object readById(Class clazz, Long id) {
		Object o = null;
		System.out.println("\n =========== READ " + clazz.getSimpleName() + " id: "+ id.toString());
		o = session.get(clazz, id);
		System.out.println(o.toString());
		System.out.println("\n------ END READ.");
		return o;
	}
	
	public Object readByParameter(Class clazz, String parameter, Object value) {
		Object o = null;
		System.out.println("\n =========== READ PARAMETER " + clazz.getSimpleName() + "." + parameter + " = "+ value.toString());
		Query queryResult;
		queryResult = session.createQuery("from " + clazz.getSimpleName() + " e where e." + parameter + " = :value").setParameter("value", value);
		o = queryResult.uniqueResult();
		System.out.println(o);
		System.out.println("\n------ END READ.");
		return o;
	}
	
	public List readAllWithParameter(Class clazz, String parameter, Object value) {
		Query queryResult;
		List list = null;
		System.out.println("\n =========== READ PARAMETER ALL " + clazz.getSimpleName() + parameter + ": "+ value.toString());
		queryResult = session.createQuery("from " + clazz.getSimpleName() + " e where e." + parameter + " = :value").setParameter("value", value);
		list = queryResult.list();
		System.out.println(list.toString());
		System.out.println("\n------ END READ.");
		return list;
	}
}