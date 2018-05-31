package testHibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import busquedaImplementacion.BusquedaComentariosImpl2;
import busquedaImplementacion.BusquedaForoImpl2;
import busquedaImplementacion.BusquedaPublicacionesImpl2;
import busquedaImplementacion.BusquedaUsuariosImpl2;
import hibernateUtil.Buscador;
import hibernateUtil.MyEntityManager;
import hibernateUtil.MySessionFactory;
import junit.framework.TestCase;
import modelo.Comentario;
import modelo.Foro;
import modelo.Publicacion;
import modelo.Usuario;
import utilidades.BusquedaFactory;

public class DeleteTest extends TestCase {
	private static SessionFactory factory;
	
	private MyEntityManager em;
	private Buscador buscador;
	
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
		buscador = new Buscador(session);
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
		}
		finally {
			session.close();
		}
	}
	
	public void testDeleteComentarioDirecto(){
		beginT();
		List<Usuario> usuarios = buscador.getUsuarios();
		List<Publicacion> publicaciones = buscador.getPublicaciones();
		List<Comentario> comentarios = buscador.getComentarios();
		endT();
		
		beginT();
		Usuario u = usuarios.get(0);
		Comentario c = u.getComentarios().iterator().next();
		Long id = u.getId();
		
		em.delete(c);
		
		endT();
		
		beginT();
		List<Usuario> usuarios2 = buscador.getUsuarios();
		List<Publicacion> publicaciones2 = buscador.getPublicaciones();
		List<Comentario> comentarios2 = buscador.getComentarios();
		
		assertTrue((comentarios.size()-1) == comentarios2.size() );
		assertTrue(usuarios.size() == usuarios2.size());
		
		Usuario u2 = (Usuario) em.readById(Usuario.class, id);
		assertTrue((u.getComentarios().size()-1) == u2.getComentarios().size() );
		assertTrue(publicaciones.size() == publicaciones2.size());
		
		endT();
	}
	
	public void testDeleteComentarioPorAlcanceDesdePublicacion(){
		beginT();
		List<Usuario> usuarios = buscador.getUsuarios();
		List<Publicacion> publicaciones = buscador.getPublicaciones();
		List<Comentario> comentarios = buscador.getComentarios();
		endT();
		
		beginT();
		Usuario u = null;
		Comentario c;
		Integer comentariosIniciales = null;

		Publicacion p = (Publicacion) session.get(Publicacion.class, new Long(1));

		u = (Usuario) session.get(Usuario.class,new Long(1));
		comentariosIniciales = u.getComentarios().size();
		c = u.getComentarios().iterator().next();
		
		System.out.println("COMENTARIO A BORRAR: " + c);
		p.removeComentario(c);
		endT();
		
		beginT();
		List<Usuario> usuarios2 = buscador.getUsuarios();
		List<Publicacion> publicaciones2 = buscador.getPublicaciones();
		List<Comentario> comentarios2 = buscador.getComentarios();
		
		assertTrue((comentarios.size()-1) == comentarios2.size() );
		assertTrue(usuarios.size() == usuarios2.size());
		
		Usuario u2 = (Usuario) em.readById(Usuario.class, u.getId());
		assertTrue((comentariosIniciales - 1) == u2.getComentarios().size() );
		assertTrue(publicaciones.size() == publicaciones2.size());
		endT();
	}
	
	public void testDeleteComentarioPorAlcanceDesdeUsuario(){
		beginT();
		List<Usuario> usuarios = buscador.getUsuarios();
		List<Publicacion> publicaciones = buscador.getPublicaciones();
		List<Comentario> comentarios = buscador.getComentarios();
		endT();
		
		beginT();
		Usuario u = null;
		Comentario c;
		Integer comentariosIniciales = null;

		Publicacion p = (Publicacion) session.get(Publicacion.class, new Long(1));

		u = (Usuario) session.get(Usuario.class,new Long(1));
		comentariosIniciales = u.getComentarios().size();
		c = u.getComentarios().iterator().next();
		
		u.getComentarios().remove(c);
		endT();
		
		beginT();
		List<Usuario> usuarios2 = buscador.getUsuarios();
		List<Publicacion> publicaciones2 = buscador.getPublicaciones();
		List<Comentario> comentarios2 = buscador.getComentarios();
		
		assertTrue((comentarios.size()-1) == comentarios2.size() );
		assertTrue(usuarios.size() == usuarios2.size());
		
		Usuario u2 = (Usuario) em.readById(Usuario.class, u.getId());
		assertTrue((comentariosIniciales - 1) == u2.getComentarios().size() );
		assertTrue(publicaciones.size() == publicaciones2.size());
		endT();
	}
	
	public void testDeleteUsuarioDirecto(){
		beginT();
		List<Usuario> usuarios = buscador.getUsuarios();
		List<Publicacion> publicaciones = buscador.getPublicaciones();
		List<Comentario> comentarios = buscador.getComentarios();
		endT();
		
		
		assertFalse(usuarios.isEmpty());
		assertFalse(publicaciones.isEmpty());
		assertFalse(comentarios.isEmpty());
		beginT();
		em.delete(usuarios.get(0));
		endT();
		
		beginT();
		List<Usuario> usuarios2 = buscador.getUsuarios();
		List<Publicacion> publicaciones2 = buscador.getPublicaciones();
		List<Comentario> comentarios2 = buscador.getComentarios();
		
		assertTrue(usuarios.size() != usuarios2.size());
		Usuario user = (Usuario)usuarios2.get(0);
		assertTrue(comentarios.size() != comentarios2.size());
		assertTrue(publicaciones.size() == publicaciones2.size());
		assertTrue(user.getComentarios().size() == comentarios2.size());
		endT();
	}
	
		
	public void testDeletePublicacionDirecto(){
		beginT();
		List<Usuario> usuarios = buscador.getUsuarios();
		List<Publicacion> publicaciones = buscador.getPublicaciones();
		List<Comentario> comentarios = buscador.getComentarios();
		endT();
		
		
		assertFalse(usuarios.isEmpty());
		assertFalse(publicaciones.isEmpty());
		assertFalse(comentarios.isEmpty());
		
		beginT();
		em.delete(publicaciones.get(0));
		endT();
		
		beginT();
		List<Usuario> usuarios2 = buscador.getUsuarios();
		List<Publicacion> publicaciones2 = buscador.getPublicaciones();
		List<Comentario> comentarios2 = buscador.getComentarios();
		
		
		assertTrue(publicaciones.size() != publicaciones2.size());
		assertTrue(publicaciones2.size() == 0);
		assertTrue(comentarios.size() != comentarios2.size());
		assertTrue(comentarios2.size() == 0);
		assertTrue(usuarios.size() == usuarios2.size());
		endT();
	}
}
