package busquedaInterface;

import java.util.Optional;

import modelo.Publicacion;

public interface BusquedaPublicaciones extends BusquedaMinima<Publicacion> {
	public Optional<Publicacion> findByTitulo(String titulo);
}
