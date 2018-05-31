package busquedaInterface;

import java.util.Optional;
import modelo.Usuario;

public interface BusquedaUsuarios extends BusquedaMinima<Usuario> {
	public Optional<Usuario> findByNombre(String nombre);
}
