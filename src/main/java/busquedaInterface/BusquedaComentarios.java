package busquedaInterface;

import modelo.Comentario;
import modelo.Publicacion;
import modelo.Usuario;

public interface BusquedaComentarios extends BusquedaMinima<Comentario> {
	public Iterable<Comentario> findByUsuario(Usuario user);
	public Iterable<Comentario> findByPublicacion(Publicacion publicacion);
	public Iterable<Comentario> findByPublicacionOrderByFechaAsc(Publicacion publicacion);
}
