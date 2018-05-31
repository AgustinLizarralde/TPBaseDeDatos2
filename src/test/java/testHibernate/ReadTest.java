package testHibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import hibernateUtil.MyEntityManager;
import hibernateUtil.MySessionFactory;
import junit.framework.TestCase;
import modelo.Comentario;
import modelo.Publicacion;
import modelo.Usuario;

public class ReadTest extends TestCase {
private static SessionFactory factory;
	
	private MyEntityManager em;
	
	private Session session;
	private Transaction tx;
	
	@Override
	protected void setUp() throws Exception {
		factory = MySessionFactory.getFactory();
		MySessionFactory.setupTestDatabase();
	}
	
	private void beginT(){
		session = factory.openSession();
		em = new MyEntityManager(session);
		tx = session.beginTransaction();
	}
	
	private void endT(){
		try{
			if(tx!=null){
				tx.commit();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			if(tx!=null){
				tx.rollback();
			}
		}
		finally {
			session.close();
		}
	}
	
	public void testReadComentario(){
		beginT();
		Comentario e = (Comentario) em.readById(Comentario.class, new Long(1));
		
		Comentario e1 = (Comentario) em.readByParameter(Comentario.class, "id", e.getId());
		assertTrue( (e1!= null) && (e.equals(e1)) );
		
		Comentario e2 = (Comentario) em.readByParameter(Comentario.class, "texto", e.getTexto());
		assertTrue( (e2!= null) && (e.equals(e2)) );
		endT();
	}
	
	public void testReadPublicacion(){
		beginT();
		Publicacion e = (Publicacion) em.readById(Publicacion.class, new Long(1));
		Publicacion e1 = (Publicacion) em.readByParameter(Publicacion.class, "id", e.getId());
		assertTrue( (e1!= null) && (e.equals(e1)) );
		
		Publicacion e2 = (Publicacion) em.readByParameter(Publicacion.class, "titulo", e.getTitulo());
		assertTrue( (e2!= null) && (e.equals(e2)) );
		endT();
	}
	
	public void testReadUsuario(){
		beginT();
		Usuario e = (Usuario) em.readById(Usuario.class, new Long(1));
		
		Usuario e1 = (Usuario) em.readByParameter(Usuario.class, "id", e.getId());
		assertTrue( (e1!= null) && (e.equals(e1)) );
		
		Usuario e2 = (Usuario) em.readByParameter(Usuario.class, "nombre", e.getNombre());
		assertTrue( (e2!= null) && (e.equals(e2)) );
		endT();
	}
}
