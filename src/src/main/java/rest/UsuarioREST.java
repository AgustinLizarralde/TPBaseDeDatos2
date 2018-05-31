package rest;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;

import modelo.Usuario;
import repositoriosSpring.ForoRepository;
import repositoriosSpring.UsuarioRepository;
import utilidades.JView;

@RestController
public class UsuarioREST extends GenericREST<Usuario, EntityJsonUsuario> {
	
	@Autowired
	private ForoRepository foroRepo;
	
	@Autowired
	private UsuarioRepository usuarioRepo;

	@Override
	protected Class<EntityJsonUsuario> getEntityJsonClass() {
		return EntityJsonUsuario.class;
	}
	
	@Override
	protected boolean isActualObjectVersion(Usuario entity, EntityJsonUsuario jsonEntity) {
		return jsonEntity.getVersion() != null && jsonEntity.getVersion().equals(entity.getVersion());
	}



	@Override
	protected Usuario createEntity(EntityJsonUsuario jsonEntity) {
		Usuario entity = new Usuario(jsonEntity.getUser(), jsonEntity.getPassword());
		foroRepo.findForo().get().addUsuario(entity);
		return entity;
	}

	@Override
	protected Usuario updateEntity(Usuario entity, EntityJsonUsuario jsonEntity) {
		if(jsonEntity.getUser() != null){
			entity.setNombre(jsonEntity.getUser());
		}
		return entity;
	}
	

	@Override
	protected void deleteEntity(Usuario entity) {
		this.foroRepo.findForo().get().removeUsuario(entity);
	}

	@Override
	protected boolean isValidJsonEntityToCreate(EntityJsonUsuario jsonEntity) {
		return (jsonEntity != null && 
				jsonEntity.getUser() != null &&
				jsonEntity.getPassword() != null);
	}

	@Override
	protected boolean isValidJsonEntityToUpdate(EntityJsonUsuario jsonEntity) {
		return (jsonEntity != null);
	}

	@Override
	@GetMapping(value="/usuario/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(JView.JSONUsuario.class)
	@Transactional(readOnly=true)
	public ResponseEntity<Usuario> entityById(@PathVariable("id") Long id) {
		return super.entityById(id);
	}
	
	@GetMapping(value="/usuario/nombre/{nombre}", produces = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(JView.JSONUsuario.class)
	@Transactional(readOnly=true)
	public ResponseEntity<Usuario> entityById(@PathVariable("nombre") String nombre) {
		Usuario u = usuarioRepo.findByNombre(nombre).orElse(null);
		if( u != null){
			return new ResponseEntity<Usuario>(u, HttpStatus.OK);
    	}
		else{
			return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	@GetMapping(value="/usuario", produces = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(JView.JSONUsuario.class)
	@Transactional(readOnly=true)
	public ResponseEntity<List<Usuario>> entityAll() {
		return super.entityAll();
	}

	@Override
	@PostMapping(value="/usuario", consumes = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(JView.JSONUsuario.class)
	@Transactional
	public ResponseEntity<Usuario> entityCreate(@RequestBody String jsonString) {
		return super.entityCreate(jsonString);
	}
	
	@PostMapping(value="/usuario/registrar", consumes = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(JView.JSONUsuario.class)
	@Transactional
	public ResponseEntity<Usuario> registrarUsuario(@RequestBody String jsonString) {
		return super.entityCreate(jsonString);
	}
	
	@PutMapping(value="/usuario/{id}/password", consumes = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(JView.JSONUsuario.class)
	@Transactional
	public ResponseEntity<Usuario> cambiarPasswordUsuario(@PathVariable("id") Long id, @RequestBody String jsonString) {
		Usuario entity = (Usuario) this.getById(id);
    	
		EntityJsonPassword parsedEntity = null;
		try {
			parsedEntity = new ObjectMapper().readValue(jsonString, EntityJsonPassword.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
    	if(entity!= null && parsedEntity!= null && 
    		parsedEntity.getPassword_old() != "" && parsedEntity.getPassword_new() != "" &&
    		entity.getVersion().equals(parsedEntity.getVersion())){
    		if( entity.getPassword().equals(parsedEntity.getPassword_old()) ){
	    		entity.setPassword(parsedEntity.getPassword_new());
	    		return new ResponseEntity<>(HttpStatus.OK);
    		}
    		else{
    			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    		}
    	}
    	else{
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
	}

	@Override
	@PutMapping(value="/usuario/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(JView.JSONUsuario.class)
	@Transactional
	public ResponseEntity<Usuario> entityUpdate(@PathVariable("id") Long id, @RequestBody String jsonString) {
		return super.entityUpdate(id, jsonString);
	}

	@Override
	@DeleteMapping("/usuario/{id}")
	@Transactional
	public ResponseEntity<Usuario> entityRemove(@PathVariable("id") Long id) {
		return super.entityRemove(id);
	}
	
}

/** JSON (los campos pueden dejarse en vacío):
{
"version":1,
"user":"user",
"password":"password",
}
**/
final class EntityJsonUsuario extends EntityJsonAbstract{
	private String user;
	private String password;
	
	public EntityJsonUsuario() {
		super();
	}
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "EntityJsonUsuario [user=" + user + ", password=" + password + "]";
	}

}

/** JSON (los campos pueden dejarse en vacío):
{
"version":1,
"password_old":"password",
"password_new":"password",
}
**/
final class EntityJsonPassword extends EntityJsonAbstract{
	private String password_old;
	private String password_new;
	
	public EntityJsonPassword() {
		super();
	}
	
	public String getPassword_old() {
		return password_old;
	}

	public void setPassword_old(String password_old) {
		this.password_old = password_old;
	}

	public String getPassword_new() {
		return password_new;
	}

	public void setPassword_new(String password_new) {
		this.password_new = password_new;
	}

	@Override
	public String toString() {
		return "EntityJsonPassword [password_old=" + password_old + ", password_new=" + password_new + "]";
	}

}

