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

public class CreateTest extends TestCase {
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
	

	public void testCreateComentarioDirecto(){
		beginT();
		List<Usuario> usuarios = buscador.getUsuarios();
		List<Publicacion> publicaciones = buscador.getPublicaciones();
		List<Comentario> comentarios = buscador.getComentarios();
		endT();
		
		beginT();
		Usuario u = buscador.getUsuarioById(new Long(1));
		int comentariosInicialesU = u.getComentarios().size();
		Long idU = u.getId();
		Publicacion p = buscador.getPublicacionById(new Long(1));
		int comentariosInicailesP = p.getComentarios().size();
		Long idP = p.getId();
		Comentario c = new Comentario("test new", u, p);
		
		
		em.create(c);
		
		endT();
		
		beginT();
		List<Usuario> usuarios2 = buscador.getUsuarios();
		List<Publicacion> publicaciones2 = buscador.getPublicaciones();
		List<Comentario> comentarios2 = buscador.getComentarios();
		
		assertTrue((comentarios.size()+1) == comentarios2.size() );
		assertTrue(usuarios.size() == usuarios2.size());
		
		Usuario u2 = buscador.getUsuarioById( idU);
		assertTrue(comentariosInicialesU+1 == u2.getComentarios().size() );
		
		assertTrue(publicaciones.size() == publicaciones2.size());
		Publicacion p2 = buscador.getPublicacionById(idP);
		assertTrue(comentariosInicailesP+1 == p2.getComentarios().size() );
		endT();
	}
	
	public void testCreateComentarioPorAlcanceDesdePublicacion(){
		beginT();
		List<Usuario> usuarios = buscador.getUsuarios();
		List<Publicacion> publicaciones = buscador.getPublicaciones();
		List<Comentario> comentarios = buscador.getComentarios();
		endT();
		
		beginT();
		Usuario u = buscador.getUsuarioById( new Long(1));
		int comentariosInicialesU = u.getComentarios().size();
		Long idU = u.getId();
		Publicacion p = buscador.getPublicacionById(new Long(1));
		int comentariosInicailesP = p.getComentarios().size();
		Long idP = p.getId();
		p.addComentario("test new", u);
		endT();
		
		beginT();
		List<Usuario> usuarios2 = buscador.getUsuarios();
		List<Publicacion> publicaciones2 = buscador.getPublicaciones();
		List<Comentario> comentarios2 = buscador.getComentarios();
		
		
		assertTrue((comentarios.size()+1) == comentarios2.size() );
		assertTrue(usuarios.size() == usuarios2.size());
		
		Usuario u2 = buscador.getUsuarioById( idU);
		assertTrue(comentariosInicialesU+1 == u2.getComentarios().size() );
		
		assertTrue(publicaciones.size() == publicaciones2.size());
		Publicacion p2 = buscador.getPublicacionById(idP);
		assertTrue(comentariosInicailesP+1 == p2.getComentarios().size() );
		endT();
	}
	
	public void testCreateUsuarioDirecto(){
		beginT();
		List<Usuario> usuarios = buscador.getUsuarios();
		List<Publicacion> publicaciones = buscador.getPublicaciones();
		List<Comentario> comentarios = buscador.getComentarios();
		endT();
		
		beginT();
		Usuario u = new Usuario("juan", "juan");
		em.create(u);
		endT();
		
		beginT();
		List<Usuario> usuarios2 = buscador.getUsuarios();
		List<Publicacion> publicaciones2 = buscador.getPublicaciones();
		List<Comentario> comentarios2 = buscador.getComentarios();
		
		assertTrue(comentarios.size() == comentarios2.size() );
		assertTrue(publicaciones.size() == publicaciones2.size());
		assertTrue((usuarios.size()+1)== usuarios2.size());
		endT();
	}
	
	public void testCreateUsuarioPorAlcenceDesdeForo(){
		beginT();
		List<Usuario> usuarios = buscador.getUsuarios();
		List<Publicacion> publicaciones = buscador.getPublicaciones();
		List<Comentario> comentarios = buscador.getComentarios();
		endT();
		
		beginT();
		Usuario u = new Usuario("juan", "juan");
		Foro f = buscador.getForo();
		f.addUsuario(u);
		endT();
		
		beginT();
		List<Usuario> usuarios2 = buscador.getUsuarios();
		List<Publicacion> publicaciones2 = buscador.getPublicaciones();
		List<Comentario> comentarios2 = buscador.getComentarios();
		
		assertTrue(comentarios.size() == comentarios2.size() );
		assertTrue(publicaciones.size() == publicaciones2.size());
		assertTrue((usuarios.size()+1)== usuarios2.size());
		endT();
	}
	
	public void testCreateUsuarioPorAlcanceDesdeComentario(){
		beginT();
		List<Usuario> usuarios = buscador.getUsuarios();
		List<Publicacion> publicaciones = buscador.getPublicaciones();
		List<Comentario> comentarios = buscador.getComentarios();
		endT();
		
		beginT();
		Usuario u = new Usuario("juan", "juan");
		Publicacion p = buscador.getPublicacionById(new Long(1));
		int comentariosInicailesP = p.getComentarios().size();
		Long idP = p.getId();
		Comentario c = new Comentario("test new", u, p);
		endT();
		
		beginT();
		List<Usuario> usuarios2 = buscador.getUsuarios();
		List<Publicacion> publicaciones2 = buscador.getPublicaciones();
		List<Comentario> comentarios2 = buscador.getComentarios();
		
		assertTrue((comentarios.size()+1) == comentarios2.size() );
		assertTrue((usuarios.size()+1) == usuarios2.size());
		
		assertTrue(publicaciones.size() == publicaciones2.size());
		Publicacion p2 = buscador.getPublicacionById(idP);
		assertTrue(comentariosInicailesP+1 == p2.getComentarios().size() );
		endT();
	}
	
	public void testCreateUsuarioPorAlcanceDesdeComentarioPublicacion(){
		beginT();
		List<Usuario> usuarios = buscador.getUsuarios();
		List<Publicacion> publicaciones = buscador.getPublicaciones();
		List<Comentario> comentarios = buscador.getComentarios();
		endT();
		
		beginT();
		Usuario u = new Usuario("juan", "juan");
		Publicacion p = buscador.getPublicacionById(new Long(1));
		int comentariosInicailesP = p.getComentarios().size();
		Long idP = p.getId();
		p.addComentario("test new", u);
		endT();
		
		beginT();
		List<Usuario> usuarios2 = buscador.getUsuarios();
		List<Publicacion> publicaciones2 = buscador.getPublicaciones();
		List<Comentario> comentarios2 = buscador.getComentarios();
		
		assertTrue((comentarios.size()+1) == comentarios2.size() );
		assertTrue((usuarios.size()+1) == usuarios2.size());
		
		assertTrue(publicaciones.size() == publicaciones2.size());
		Publicacion p2 = buscador.getPublicacionById(idP);
		assertTrue(comentariosInicailesP+1 == p2.getComentarios().size() );
		endT();
	}
	
	public void testCreatePublicacionDirecto(){
		beginT();
		List<Usuario> usuarios = buscador.getUsuarios();
		List<Publicacion> publicaciones = buscador.getPublicaciones();
		List<Comentario> comentarios = buscador.getComentarios();
		endT();
		
		beginT();
		Publicacion p = new Publicacion("Test 2");
		em.create(p);
		endT();
		
		beginT();
		List<Usuario> usuarios2 = buscador.getUsuarios();
		List<Publicacion> publicaciones2 = buscador.getPublicaciones();
		List<Comentario> comentarios2 = buscador.getComentarios();
		
		assertTrue(comentarios.size() == comentarios2.size() );
		assertTrue((publicaciones.size()+1) == publicaciones2.size());
		assertTrue(usuarios.size() == usuarios2.size());
		endT();
	}
	
	public void testCreatePublicacionPorAlcenceDesdeForo(){
		beginT();
		List<Usuario> usuarios = buscador.getUsuarios();
		List<Publicacion> publicaciones = buscador.getPublicaciones();
		List<Comentario> comentarios = buscador.getComentarios();
		endT();
		
		beginT();
		Publicacion p = new Publicacion("Test 2");
		Foro f = buscador.getForo();
		f.addPublicacion(p);
		endT();
		
		beginT();
		List<Usuario> usuarios2 = buscador.getUsuarios();
		List<Publicacion> publicaciones2 = buscador.getPublicaciones();
		List<Comentario> comentarios2 = buscador.getComentarios();
		
		assertTrue(comentarios.size() == comentarios2.size() );
		assertTrue((publicaciones.size()+1) == publicaciones2.size());
		assertTrue(usuarios.size() == usuarios2.size());
		endT();
	}
}
