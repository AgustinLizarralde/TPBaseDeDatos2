package hibernateUtil;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import busquedaImplementacion.BusquedaComentariosImpl2;
import busquedaImplementacion.BusquedaForoImpl2;
import busquedaImplementacion.BusquedaPublicacionesImpl2;
import busquedaImplementacion.BusquedaUsuariosImpl2;
import modelo.Foro;
import modelo.Publicacion;
import modelo.Usuario;
import utilidades.BusquedaFactory;


/**
 * Esta clase es solo para pruebas
 *
 */
public class MySessionFactory {

	private static SessionFactory instance;
	
	private static String pathConfig = "hibernate/hibernate.cfg.xml";
	
	private static Configuration config = new Configuration();

	public static SessionFactory getFactory() {
		if (instance == null) {
			instance = crearSessionFactory();
		}
		return instance;
	}
	
	public static void dropDatabase(){
		System.out.println("Droping schema.........");
		new SchemaExport(config).drop(true, true);
		System.out.println("DONE.");
	}
	
	public static void createDatabase(){
		System.out.println("Generating schema.........");
		new SchemaExport(config).create(true, true);
		System.out.println("DONE.");
	}
	
	public static void dropAndCeateDatabase(){
		dropDatabase();
		createDatabase();
	}
	
	private static SessionFactory crearSessionFactory() {
//		config.setInterceptor(new TestInterceptor());
	    config.configure(pathConfig);
	    
	    System.out.println("Building sessions.........");    
//	    ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(
//	            configuration.getProperties()).buildServiceRegistry();
	    ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(config.getProperties()).build();
	    
	    return config.buildSessionFactory(serviceRegistry);
	    
	}
	
	public static void setupTestDatabase(){
		dropAndCeateDatabase();
		
		Foro foro = new Foro();
		BusquedaFactory.getInstance().setBusquedaUsuarios(new BusquedaUsuariosImpl2(foro.getUsuarios()));
		BusquedaFactory.getInstance().setBusquedaPublicaciones(new BusquedaPublicacionesImpl2(foro.getPublicaciones()));
		BusquedaFactory.getInstance().setBusquedaForo(new BusquedaForoImpl2(foro));
		foro.setBusquedaUsuarios(BusquedaFactory.getInstance().getBusquedaUsuarios());
		foro.setBusquedaPublicaciones(BusquedaFactory.getInstance().getBusquedaPublicaciones());
		Usuario u1 = new Usuario("jose", "jose");
		Usuario u2 = new Usuario("pepe", "pepe");
		foro.addUsuario(u1);
		foro.addUsuario(u2);
		Publicacion p1 = new Publicacion("publicacion T1");
		p1.addComentario("de test1 u1jose",u1);
		p1.addComentario("de test2 u2pepe",u2);
		p1.addComentario("de test3 u2 pepe",u2);
		foro.addPublicacion(p1);
		Session session = getFactory().openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			session.saveOrUpdate(foro);
			tx.commit();
		}
		catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		}
		finally {
			session.close();
		}
		BusquedaFactory.getInstance().setBusquedaUsuarios(new BusquedaUsuariosImpl2(foro.getUsuarios()));
		BusquedaFactory.getInstance().setBusquedaPublicaciones(new BusquedaPublicacionesImpl2(foro.getPublicaciones()));
		BusquedaFactory.getInstance().setBusquedaForo(new BusquedaForoImpl2(foro));
	}

}