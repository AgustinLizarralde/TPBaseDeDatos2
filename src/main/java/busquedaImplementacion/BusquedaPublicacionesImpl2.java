package busquedaImplementacion;

import java.util.Collection;
import java.util.Optional;

import busquedaInterface.BusquedaPublicaciones;
import modelo.Publicacion;

public class BusquedaPublicacionesImpl2 implements BusquedaPublicaciones {
	
	private Collection<Publicacion> collection;
	
	public BusquedaPublicacionesImpl2(Collection<Publicacion> collection){
		this.collection= collection;
	}
	
	public Iterable<Publicacion> findAll() {
		return collection;
	}

	public Optional<Publicacion> findById(Long id) {
		for (Publicacion publicacion : collection) {
			if(publicacion.getId().equals(id)){
				return Optional.of(publicacion);
			}
		}
		return Optional.empty();
	}

	public Optional<Publicacion> findByTitulo(String titulo) {
		for (Publicacion publicacion : collection) {
			if(publicacion.getTitulo().equals(titulo)){
				return Optional.of(publicacion);
			}
		}
		return Optional.empty();
	}

}
