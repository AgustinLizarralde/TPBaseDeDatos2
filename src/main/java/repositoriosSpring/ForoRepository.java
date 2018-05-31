package repositoriosSpring;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import busquedaInterface.busquedaForo;
import modelo.Foro;

public interface ForoRepository extends Repository<Foro, Long>, busquedaForo {
	@Override
	@Query("SELECT f FROM Foro f WHERE f.id = 1")
	public Optional<Foro> findForo();
}
