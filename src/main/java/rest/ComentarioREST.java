package rest;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import modelo.Comentario;
import modelo.Publicacion;
import modelo.Usuario;
import repositoriosSpring.PublicacionRepository;
import repositoriosSpring.UsuarioRepository;
import utilidades.JView;

@RestController
public class ComentarioREST extends GenericREST<Comentario, EntityJsonComentario> {
	
	@Autowired
	private UsuarioRepository usuarioRepo;
	
	@Autowired
	private PublicacionRepository publicacionRepo;
	
	@Override
	protected Comentario createEntity(EntityJsonComentario jsonEntity) {
		Usuario usuario = usuarioRepo.findById(jsonEntity.getUsuarioId()).orElse(null);
		Publicacion publicacion=publicacionRepo.findById(jsonEntity.getPublicacionId()).orElse(null);
		Comentario entity = new Comentario(jsonEntity.getTexto(), usuario, publicacion);
		return entity;
	}

	@Override
	protected Comentario updateEntity(Comentario entity, EntityJsonComentario jsonEntity) {
		if(jsonEntity.getTexto() != null){
			entity.setTexto(jsonEntity.getTexto());
		}
		if(jsonEntity.getFecha()!=null){
			entity.setFecha(jsonEntity.getFecha());
		}
		return entity;
	}
	
	@Override
	protected void deleteEntity(Comentario entity) {
		entity.getPublicacion().removeComentario(entity);
	}

	@Override
	protected Class<EntityJsonComentario> getEntityJsonClass() {
		return EntityJsonComentario.class;
	}

	@Override
	protected boolean isValidJsonEntityToCreate(EntityJsonComentario jsonEntity) {
		return (jsonEntity != null &&
				jsonEntity.getTexto() != null);
	}

	@Override
	protected boolean isValidJsonEntityToUpdate(EntityJsonComentario jsonEntity) {
		return (jsonEntity != null);
	}

	@Override
	protected boolean isActualObjectVersion(Comentario entity, EntityJsonComentario jsonEntity) {
		return jsonEntity.getVersion() != null && jsonEntity.getVersion().equals(entity.getVersion());
	}

	@Override
	@GetMapping(value="/comentario/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(JView.JSONComentario.class)
	@Transactional(readOnly=true)
	public ResponseEntity<Comentario> entityById(@PathVariable("id") Long id) {
		return super.entityById(id);
	}

	@Override
	@GetMapping(value="/comentario", produces = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(JView.JSONComentario.class)
	@Transactional(readOnly=true)
	public ResponseEntity<List<Comentario>> entityAll() {
		return super.entityAll();
	}

	@Override
	@PostMapping(value="/comentario", consumes = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(JView.JSONComentario.class)
	@Transactional
	public ResponseEntity<Comentario> entityCreate(@RequestBody String jsonString) {
		return super.entityCreate(jsonString);
	}
		
	@Override
	@PutMapping(value="/comentario/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(JView.JSONComentario.class)
	@Transactional
	public ResponseEntity<Comentario> entityUpdate(@PathVariable("id") Long id, @RequestBody String jsonString) {
		return super.entityUpdate(id, jsonString);
	}

	@Override
	@DeleteMapping("/comentario/{id}")
	@Transactional
	public ResponseEntity<Comentario> entityRemove(@PathVariable("id") Long id) {
		return super.entityRemove(id);
	}
	
}

/** JSON (los campos pueden dejarse en vacío):
{
"version":1,
"usuarioId":1,
"publicacionId":1,
"texto":"texto de prueba",
"fecha":1527380997000,
}
**/
final class EntityJsonComentario extends EntityJsonAbstract{
	private String texto;
	private Date fecha;
	private Long usuarioId;
	private Long publicacionId;
	
	public EntityJsonComentario() {
		super();
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Long getPublicacionId() {
		return publicacionId;
	}

	public void setPublicacionId(Long publicacionId) {
		this.publicacionId = publicacionId;
	}

	@Override
	public String toString() {
		return "EntityJsonComentario [texto=" + texto + ", fecha=" + fecha + "]";
	}

}

