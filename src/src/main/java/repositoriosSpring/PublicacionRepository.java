package repositoriosSpring;

import org.springframework.data.repository.Repository;

import busquedaInterface.BusquedaPublicaciones;
import modelo.Publicacion;

public interface PublicacionRepository extends Repository<Publicacion, Long>, BusquedaPublicaciones {

}
