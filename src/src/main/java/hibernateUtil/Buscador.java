package hibernateUtil;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import busquedaImplementacion.BusquedaComentariosImpl2;
import busquedaImplementacion.BusquedaForoImpl2;
import busquedaImplementacion.BusquedaPublicacionesImpl2;
import busquedaImplementacion.BusquedaUsuariosImpl2;
import modelo.Comentario;
import modelo.Foro;
import modelo.Publicacion;
import modelo.Usuario;
import utilidades.BusquedaFactory;

/**
 * Esta clase es solo para pruebas
 *
 */
public class Buscador {
private Session session;
	
	public Buscador(Session session) {
		this.session = session;
	}
	
	public Foro getForo(){
		Foro foro = (Foro) session.get(Foro.class, new Long(1));
		return foro;
	}
	
	public List<Usuario> getUsuarios() {
		Query query = session.createQuery("from "+ Usuario.class.getSimpleName());
		return (List<Usuario>) query.list();
	}
	public Usuario getUsuarioById(Long id){
		return (Usuario) session.get(Usuario.class, id);
	}
	public Usuario getUsuarioByNombre(String nombre){
		Query queryResult = session.createQuery("from " + Usuario.class.getSimpleName() + 
					" e where e." + "nombre" + " = :value").setParameter("value", nombre);
		return (Usuario) queryResult.uniqueResult();
	}
	
	public List<Publicacion> getPublicaciones() {
		Query query = session.createQuery("from "+ Publicacion.class.getSimpleName());
		return (List<Publicacion>) query.list();
	}
	public Publicacion getPublicacionById(Long id){
		return (Publicacion) session.get(Publicacion.class, id);
	}
	public Publicacion getPublicacionByTitulo(String titulo){
		Query queryResult = session.createQuery("from " + Publicacion.class.getSimpleName() + 
					" e where e." + "titulo" + " = :value").setParameter("value", titulo);
		return (Publicacion) queryResult.uniqueResult();
	}
	
	public List<Comentario> getComentarios(){
		Query query = session.createQuery("from "+ Comentario.class.getSimpleName());
		return (List<Comentario>) query.list();
	}
	public Comentario getComentarioById(Long id){
		return (Comentario) session.get(Comentario.class, id);
	}
	
}
