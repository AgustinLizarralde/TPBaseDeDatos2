package busquedaImplementacion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import busquedaInterface.BusquedaComentarios;
import modelo.Comentario;
import modelo.Publicacion;
import modelo.Usuario;

public class BusquedaComentariosImpl2 implements BusquedaComentarios {

	private Collection<Comentario> collection;
	
	public BusquedaComentariosImpl2(Collection<Comentario> collection){
		this.collection = new ArrayList<Comentario>(collection);
	}
	
	public Iterable<Comentario> findAll() {
		return (List<Comentario>) collection;
	}

	public Optional<Comentario> findById(Long id) {
		for (Comentario comentario : collection) {
			if(comentario.getId().equals(id)){
				return Optional.of(comentario);
			}
		}
		return Optional.empty();
	}

	public Iterable<Comentario> findByPublicacionOrderByFechaAsc(Publicacion publicacion) {
		
		final Comparator<Comentario> comparator = new Comparator<Comentario>() {

			public int compare(Comentario o1, Comentario o2) {
				if( o1.getFecha().equals(o2.getFecha()) ){
					return 0;
				}
				else{
					if( o1.getFecha().after(o2.getFecha()) ){
						return 1;
					}
					else{
						return -1;
					}
				}
			}
		};
		List<Comentario> res =publicacion.getComentarios();
		Collections.sort(res, comparator);
		
		return res;
	}

	public Iterable<Comentario> findByUsuario(Usuario user) {
		ArrayList<Comentario> list = new ArrayList<Comentario>();
		for (Comentario comentario : collection) {
			if(comentario.getUsuario().equals(user)){
				list.add(comentario);
			}
		}
		return list;
	}

	@Override
	public Iterable<Comentario> findByPublicacion(Publicacion publicacion) {
		ArrayList<Comentario> list = new ArrayList<Comentario>();
		for (Comentario comentario : collection) {
			if(comentario.getPublicacion().equals(publicacion)){
				list.add(comentario);
			}
		}
		return list;
	}

}
