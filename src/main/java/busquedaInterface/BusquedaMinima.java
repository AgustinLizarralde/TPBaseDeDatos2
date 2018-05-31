package busquedaInterface;

import java.util.Optional;

public interface BusquedaMinima<T> {
	public Iterable<T> findAll();
	public Optional<T> findById(Long id);
}
