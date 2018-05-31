package rest;

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
import repositoriosSpring.ComentarioRepository;
import repositoriosSpring.ForoRepository;
import utilidades.JView;

@RestController
public class PublicacionREST extends GenericREST<Publicacion, EntityJsonPublicacion> {
	
	@Autowired
	private ForoRepository foroRepo;
	@Autowired
	private ComentarioRepository comentarioRepo;
	
	@Override
	protected boolean isActualObjectVersion(Publicacion entity, EntityJsonPublicacion jsonEntity) {
		return jsonEntity.getVersion() != null && jsonEntity.getVersion().equals(entity.getVersion());
	}

	@Override
	protected Publicacion createEntity(EntityJsonPublicacion jsonEntity) {
		Publicacion p = new Publicacion(jsonEntity.getTitulo());
		foroRepo.findForo().get().addPublicacion(p);
		return p;
	}

	@Override
	protected boolean isValidJsonEntityToCreate(EntityJsonPublicacion jsonEntity) {
		return(jsonEntity != null &&
			jsonEntity.getTitulo() != null);
	}

	@Override
	protected boolean isValidJsonEntityToUpdate(EntityJsonPublicacion jsonEntity) {
		return(jsonEntity != null);
	}

	@Override
	protected Publicacion updateEntity(Publicacion entity, EntityJsonPublicacion jsonEntity) {
		if(jsonEntity.getTitulo()!=null){
			entity.setTitulo(jsonEntity.getTitulo());
		}
		if(jsonEntity.getComentarios_borrar()!= null && !jsonEntity.getComentarios_borrar().isEmpty()){
			for (Long id : jsonEntity.getComentarios_borrar()) {
				Comentario c = comentarioRepo.findById(id).orElse(null);
				if(c != null){
					entity.removeComentario(c);
				}
			}
		}
		return entity;
	}

	@Override
	protected void deleteEntity(Publicacion entity) {
		foroRepo.findForo().get().removePublicacion(entity);
	}

	@Override
	protected Class<EntityJsonPublicacion> getEntityJsonClass() {
		return EntityJsonPublicacion.class;
	}

	@Override
	@GetMapping(value="/publicacion/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(JView.JSONPublicacion.class)
	@Transactional(readOnly=true)
	public ResponseEntity<Publicacion> entityById(@PathVariable("id") Long id) {
		return super.entityById(id);
	}

	@Override
	@GetMapping(value="/publicacion", produces = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(JView.JSONPublicacion.class)
	@Transactional(readOnly=true)
	public ResponseEntity<List<Publicacion>> entityAll() {
		return super.entityAll();
	}

	@Override
	@PostMapping(value="/publicacion", produces = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(JView.JSONPublicacion.class)
	@Transactional
	public ResponseEntity<Publicacion> entityCreate(@RequestBody String jsonString) {
		return super.entityCreate(jsonString);
	}

	@Override
	@PutMapping(value="/publicacion/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(JView.JSONPublicacion.class)
	@Transactional
	public ResponseEntity<Publicacion> entityUpdate(@PathVariable("id") Long id,@RequestBody String jsonString) {
		return super.entityUpdate(id, jsonString);
	}

	@Override
	@DeleteMapping("/publicacion/{id}")
	@Transactional
	public ResponseEntity<Publicacion> entityRemove(@PathVariable("id") Long id) {
		return super.entityRemove(id);
	}

}

/** JSON (los campos pueden dejarse en vacío):
{
"version":1,
"titulo":"titulo de prueba",
"comentarios_borrar":[1,2],
}
**/
final class EntityJsonPublicacion extends EntityJsonAbstract{
	private String titulo;
	private List<Long> comentarios_borrar;
	
	public EntityJsonPublicacion() {
		super();
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public List<Long> getComentarios_borrar() {
		return comentarios_borrar;
	}

	public void setComentarios_borrar(List<Long> comentarios_borrar) {
		this.comentarios_borrar = comentarios_borrar;
	}

	@Override
	public String toString() {
		return "EntityJsonPublicacion [titulo=" + titulo + "]";
	}
	
}