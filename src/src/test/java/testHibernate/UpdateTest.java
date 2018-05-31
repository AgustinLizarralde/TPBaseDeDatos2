package testHibernate;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import hibernateUtil.Buscador;
import hibernateUtil.MyEntityManager;
import hibernateUtil.MySessionFactory;
import junit.framework.TestCase;
import modelo.Comentario;
import modelo.Foro;
import modelo.Publicacion;
import modelo.Usuario;

public class UpdateTest extends TestCase {
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
			if(tx!=null){
				tx.rollback();
			}
		}
		finally {
			session.close();
		}
	}
	

	public void testUpdateComentarioDirecto(){
		beginT();
		List<Usuario> usuarios = buscador.getUsuarios();
		List<Publicacion> publicaciones = buscador.getPublicaciones();
		List<Comentario> comentarios = buscador.getComentarios();
		endT();
		
		beginT();
		Comentario c = buscador.getComentarioById(new Long(1));
		c.setTexto("UPDATE test");
		Long idU = c.getUsuario().getId();
		int comentariosInicialesU = c.getUsuario().getComentarios().size();
		Long idP = c.getPublicacion().getId();
		int comentariosInicialesP = c.getPublicacion().getComentarios().size();
		em.update(c);
		endT();
		
		beginT();
		List<Usuario> usuarios2 = buscador.getUsuarios();
		List<Publicacion> publicaciones2 = buscador.getPublicaciones();
		List<Comentario> comentarios2 = buscador.getComentarios();
		
		assertTrue(comentarios.size() == comentarios2.size() );
		assertTrue(usuarios.size() == usuarios2.size());
		assertTrue(publicaciones.size() == publicaciones2.size());
		
		Comentario c2 = buscador.getComentarioById(new Long(1));
		assertTrue(c2.getTexto().equals("UPDATE test"));
		
		Usuario u2 = buscador.getUsuarioById(idU);
		assertTrue(u2.getComentarios().contains(c) );
		
		Publicacion p2 = buscador.getPublicacionById(idP);
		assertTrue(p2.getComentarios().contains(c));
		endT();
	}
	
	public void testUpdateComentarioPorAlcanceDesdePublicacion(){
		beginT();
		List<Usuario> usuarios = buscador.getUsuarios();
		List<Publicacion> publicaciones = buscador.getPublicaciones();
		List<Comentario> comentarios = buscador.getComentarios();
		endT();
		
		beginT();
		Publicacion p = buscador.getPublicacionById(new Long(1));
		Comentario c = p.getComentarios().iterator().next();
		c.setTexto("UPDATE test");
		Long idU = c.getUsuario().getId();
		int comentariosInicialesU = c.getUsuario().getComentarios().size();
		Long idP = p.getId();
		int comentariosInicialesP = p.getComentarios().size();
		endT();
		
		beginT();
		List<Usuario> usuarios2 = buscador.getUsuarios();
		List<Publicacion> publicaciones2 = buscador.getPublicaciones();
		List<Comentario> comentarios2 = buscador.getComentarios();
		
		assertTrue(comentarios.size() == comentarios2.size() );
		assertTrue(usuarios.size() == usuarios2.size());
		assertTrue(publicaciones.size() == publicaciones2.size());
		
		Comentario c2 = buscador.getComentarioById(new Long(1));
		assertTrue(c2.getTexto().equals("UPDATE test"));
		
		Usuario u2 = buscador.getUsuarioById(idU);
		assertTrue(u2.getComentarios().contains(c) );
		
		Publicacion p2 = buscador.getPublicacionById(idP);
		assertTrue(p2.getComentarios().contains(c));
		endT();
	}
	
	public void testUpdateUsuarioDirecto(){
		beginT();
		List<Usuario> usuarios = buscador.getUsuarios();
		List<Publicacion> publicaciones = buscador.getPublicaciones();
		List<Comentario> comentarios = buscador.getComentarios();
		endT();
		
		beginT();
		Usuario u = buscador.getUsuarioById(new Long(1));
		u.setNombre("UPDATE test");
		endT();
		
		beginT();
		List<Usuario> usuarios2 = buscador.getUsuarios();
		List<Publicacion> publicaciones2 = buscador.getPublicaciones();
		List<Comentario> comentarios2 = buscador.getComentarios();
		
		assertTrue(comentarios.size() == comentarios2.size() );
		assertTrue(publicaciones.size() == publicaciones2.size());
		assertTrue(usuarios.size()== usuarios2.size());
		Usuario u2 = buscador.getUsuarioById(new Long(1));
		assertTrue(u2.getNombre().equals("UPDATE test"));
		endT();
	}
	
	public void testUpdateUsuarioPorAlcanceDesdeComentario(){
		beginT();
		List<Usuario> usuarios = buscador.getUsuarios();
		List<Publicacion> publicaciones = buscador.getPublicaciones();
		List<Comentario> comentarios = buscador.getComentarios();
		endT();
		
		beginT();
		Comentario c = buscador.getComentarioById(new Long(1));
		Usuario u = c.getUsuario();
		Long idU = u.getId();
		u.setNombre("UPDATE test");
		endT();
		
		beginT();
		List<Usuario> usuarios2 = buscador.getUsuarios();
		List<Publicacion> publicaciones2 = buscador.getPublicaciones();
		List<Comentario> comentarios2 = buscador.getComentarios();
		
		assertTrue(comentarios.size() == comentarios2.size() );
		assertTrue(publicaciones.size() == publicaciones2.size());
		assertTrue(usuarios.size()== usuarios2.size());
		Usuario u2 = buscador.getUsuarioById(idU);
		assertTrue(u2.getNombre().equals("UPDATE test"));
		endT();
	}
	
	public void testUpdateUsuarioPorAlcanceDesdeForo(){
		beginT();
		List<Usuario> usuarios = buscador.getUsuarios();
		List<Publicacion> publicaciones = buscador.getPublicaciones();
		List<Comentario> comentarios = buscador.getComentarios();
		endT();
		
		beginT();
		Foro foro = buscador.getForo();
		Usuario u = foro.getUsuarios().iterator().next();
		Long idU = u.getId();
		u.setNombre("UPDATE test");
		endT();
		
		beginT();
		List<Usuario> usuarios2 = buscador.getUsuarios();
		List<Publicacion> publicaciones2 = buscador.getPublicaciones();
		List<Comentario> comentarios2 = buscador.getComentarios();
		
		assertTrue(comentarios.size() == comentarios2.size() );
		assertTrue(publicaciones.size() == publicaciones2.size());
		assertTrue(usuarios.size()== usuarios2.size());
		Usuario u2 = buscador.getUsuarioById(idU);
		assertTrue(u2.getNombre().equals("UPDATE test"));
		endT();
	}
	
}
