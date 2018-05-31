package repositoriosSpring;

import org.springframework.data.repository.Repository;

import busquedaInterface.BusquedaUsuarios;
import modelo.Usuario;

public interface UsuarioRepository extends Repository<Usuario, Long>, BusquedaUsuarios {

}
