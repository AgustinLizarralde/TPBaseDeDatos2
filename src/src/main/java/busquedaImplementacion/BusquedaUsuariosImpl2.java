package busquedaImplementacion;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import busquedaInterface.BusquedaUsuarios;
import modelo.Usuario;
import repositoriosSpring.UsuarioRepository;

public class BusquedaUsuariosImpl2 implements BusquedaUsuarios{
	private Collection<Usuario> usuarios;
	
	public BusquedaUsuariosImpl2(Collection<Usuario> usuarios){
		this.usuarios = usuarios;
	}
	
	
	public List<Usuario> findAll() {
		return (List<Usuario>) usuarios;
	}

	
	public Optional<Usuario> findById(Long id) {

		for (Usuario usuario : usuarios) {
			if(usuario.getId().equals(id)){
				return Optional.of(usuario);
			}
		}
		return Optional.empty();
	}

	
	public Optional<Usuario> findByNombre(String nombre) {
		for (Usuario usuario : usuarios) {
			if(usuario.getNombre().equals(nombre)){
				return Optional.of(usuario);
			}
		}
		return Optional.empty();
	}

}
