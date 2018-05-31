package repositoriosSpring;

import org.springframework.data.repository.Repository;

import busquedaInterface.BusquedaComentarios;
import modelo.Comentario;

public interface ComentarioRepository extends Repository<Comentario, Long>, BusquedaComentarios{

}
