package rest;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import busquedaInterface.BusquedaMinima;


/**
 * Esta clase se puede extender para generar las CRUD de un controllador y mas
 * 
 * Si se usa anotaciones deben extenderse los métodos
 * entityById(id), entityAll(), entityCreate(), entityUpdate(id,json), entityRemove(id)
 * solo usando super(id), y agregandole las anotaciones.
 * 
 * @author Agustin
 * @param <T> entidad principal de las CRUD (una del modelo)
 * @param <E> clase a la que parcear el json recibido 
 */
@Transactional
public abstract class GenericREST<T, E extends EntityJsonAbstract> {
	
	@Autowired
	protected BusquedaMinima<T> repo;
	
	//mejorar con repositorios
	protected T getById(Long id){
		return repo.findById(id).orElse(null);
	}
	
	protected List<T> getAll(){
		return (List<T>) repo.findAll();
	}
	
	/**
	 * Verifica las versiones del objeto a modificar y el objeto que lo pide
	 * @return si la version del objeto a modificar es la misma que el de la base de datos
	 */
	protected abstract boolean isActualObjectVersion(T entity, E jsonEntity);
	
	
	/**
	 * Crea la entidad a partir de un json parseado
	 * @param jsonEntity in
	 * @return entidad creada
	 */
	protected abstract T createEntity(E jsonEntity);
	
	/**
	 * Actualiza la entidad con la informacion del json parseado.
	 * Ya se verificó que la entidad no es nula y el json es valido
	 * @param entity entidad a ser modificada
	 * @param jsonEntity json parseado
	 * @return entidad modificada
	 */
	protected abstract T updateEntity(T entity, E jsonEntity);
	
	/**
	 * Borra la entidad desasociandola de las demas
	 * @param entity entidad a ser borrada
	 * @param jsonEntity json parseado
	 */
	protected abstract void deleteEntity(T entity);
	
	/**
	 * @return clase con la que será parseado el json de los metodos create y update
	 */
	protected abstract Class<E> getEntityJsonClass();
	
	/**
	 * Verifica si los datos de la entidad json son validos y suficientes para
	 * crear la entidad principal. Por ejemplo, si no es null.
	 * @param jsonEntity json parseado 
	 * @return boolean
	 */
	protected abstract boolean isValidJsonEntityToCreate(E jsonEntity);
	
	/**
	 * Verifica si los datos de la entidad json son validos y suficientes para
	 * modificar la entidad principal. Por ejemplo, si no es null.
	 * @param jsonEntity json parseado 
	 * @return boolean
	 */
	protected abstract boolean isValidJsonEntityToUpdate(E jsonEntity);
	
	/**
	 * Parsea el json a la clase especificada por @method getEntityJsonClass()
	 * @param jsonString String json a parsear
	 * @return json parseado
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	protected E mapFromJson(String jsonString) throws JsonParseException, JsonMappingException, IOException {
		E parsedEntity = null;
			parsedEntity = new ObjectMapper().readValue(jsonString, getEntityJsonClass());
		return parsedEntity;
	}

	
	//codigos basicos de REST (no son abstractos pero deben extenderse para el mapeo)
	
	/**
	 * Read de una instancia específica de la entidad.
	 * @param id identificacion de la entidad
	 * @return json de la entidad si se encontro, más el codigo de estado http
	 */
	@Transactional(readOnly=true)
    public ResponseEntity<T> entityById(@PathVariable("id") Long id) {
    	try{
			T entity = this.getById(id);
	    	if( entity == null){
	    		return new ResponseEntity<T>(HttpStatus.NOT_FOUND);
	    	}
	    	return new ResponseEntity<T>(entity, HttpStatus.OK);
    	}
    	catch (Exception e) {
    		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    /**
	 * Read de todas las instancias de la entidad.
	 * @return json de la entidad si se encontro, más el codigo de estado http
     */
	@Transactional(readOnly=true)
    public ResponseEntity<List<T>> entityAll() {
    	try{
	    	List<T>  entity = this.getAll();
	    	if( entity != null){
	    		return new ResponseEntity<List<T>>(entity, HttpStatus.OK);
	    	}
	    	else{
	    		return new ResponseEntity<List<T>>(HttpStatus.NO_CONTENT);
	    	}
		}
		catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    /**
     * Create de la entidad en base a un json
     * @param jsonString String json
     * @return json de la entidad si se pudo crear, más el codigo de estado http
     */
    @Transactional
    public ResponseEntity<T> entityCreate(@RequestBody String jsonString) {
    	try{
    		E parsedEntity;
			
    		try {
				parsedEntity = mapFromJson(jsonString);
			} catch (IOException e) {
				e.printStackTrace();
				return new ResponseEntity<T>(HttpStatus.BAD_REQUEST);
			}
			
	    	if(isValidJsonEntityToCreate(parsedEntity)){
	    		T entity = createEntity(parsedEntity);
	    		return new ResponseEntity<T>(entity, HttpStatus.CREATED);
	    	}
	    	else{
	    		return new ResponseEntity<T>(HttpStatus.BAD_REQUEST);
	    	}
    	}
    	catch (Exception e) {
    		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
	/**
	 * Update de la entidad en base a un json
	 * @param id identificacion de la entidad
	 * @param jsonString String json
	 * @return json de la entidad si se pudo modificar, más el codigo de estado http
	 */
    @Transactional
    public ResponseEntity<T> entityUpdate(@PathVariable("id") Long id, @RequestBody String jsonString) {
    	try{
	    	T entity = this.getById(id);
	    	if(entity== null){
	    		return new ResponseEntity<T>(HttpStatus.NOT_FOUND);
	    	}
	    	
	    	E parsedEntity;
			try {
				parsedEntity = mapFromJson(jsonString);
			}catch (IOException e) {
	    		e.printStackTrace();
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
	    	
	    	
	    	if(isActualObjectVersion(entity, parsedEntity) && isValidJsonEntityToUpdate(parsedEntity)){
	    		entity = updateEntity(entity, parsedEntity);
		    	return new ResponseEntity<T>(entity,HttpStatus.OK);
	    	}
	    	else{
	    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    	}
    	}
    	catch (Exception e) {
    		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    

	/**
	 * Remove de una instancia específica de la entidad.
	 * @param id identificacion de la entidad
	 * @return codigo de estado http
	 */
    @Transactional
    public ResponseEntity<T> entityRemove(@PathVariable("id") Long id) {
    	try{
	    	T entity = this.getById(id);
	    	if( entity != null ){
	    		this.deleteEntity(entity);
	    		return new ResponseEntity<T>(HttpStatus.NO_CONTENT);
	    	}
	    	else{
	    		return new ResponseEntity<T>(HttpStatus.NOT_FOUND);
	    	}
    	}
    	catch (Exception e) {
    		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
}
