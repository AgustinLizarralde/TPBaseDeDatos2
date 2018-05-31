package rest;

import java.util.List;
import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import modelo.Comentario;
import modelo.Foro;
import modelo.Publicacion;
import modelo.Usuario;
import repositoriosSpring.ComentarioRepository;
import repositoriosSpring.ForoRepository;
import repositoriosSpring.PublicacionRepository;
import repositoriosSpring.UsuarioRepository;
import utilidades.BusquedaFactory;
import utilidades.JView;


// crear base de datos con 
//CREATE DATABASE TPBaseDeDatos2  DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

@RestController
@Transactional
public class CargarDatosREST {
	protected static boolean datosCargados = false;
	
	@Autowired
	EntityManager emf;

	@Autowired
	private BusquedaFactory repoFactory;
	
	@Autowired
	private ForoRepository foroRepo;
	@Autowired
	private PublicacionRepository publicacionRepo;
	@Autowired
	private UsuarioRepository usuarioRepo;
	@Autowired
	private ComentarioRepository comentarioRepo;
	
	
	@RequestMapping(value="/cargardatos/comentarios", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(JView.JSONComentario.class)
	@Transactional
    public ResponseEntity<List<Comentario>> cargaComentarios() {
		List<Publicacion> ps = (List<Publicacion>) publicacionRepo.findAll();
		Publicacion p = ps.get(0);
		Usuario u = usuarioRepo.findById(new Long(1)).get();
		for (int i = 1; i < 8; i++) {
			p.addComentario("Mas test" + i , u);
		}
		
		return new ResponseEntity<List<Comentario>>((List<Comentario>)comentarioRepo.findAll(), HttpStatus.OK);
    }
		
	@RequestMapping(value="/cargardatos/datos", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(JView.JSONPublicacion.class)
	@Transactional
	public ResponseEntity<List<Publicacion>> cargaGenericaDeDatos() {
		try{
			if(!CargarDatosREST.datosCargados){
				Foro foro = new Foro();
				Usuario u1 = new Usuario("jose", "jose");
				Usuario u2 = new Usuario("pepe", "pepe");
				foro.addUsuario(u1);
				foro.addUsuario(u2);
				Publicacion p1 = new Publicacion("publicacion T1");
				p1.addComentario("de test1 u1 jose",u1);
				p1.addComentario("de test2 u2 pepe",u2);
				p1.addComentario("de test3 u2 pepe",u2);
				foro.addPublicacion(p1);
				emf.persist(foro);
				CargarDatosREST.datosCargados = true;
			}
			return new ResponseEntity<List<Publicacion>>((List<Publicacion>) publicacionRepo.findAll(), HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
/*
	@RequestMapping(value="/cargardatos/root", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(JView.JSONSoloID.class)
	@Transactional
	public ResponseEntity<Foro> cargaRootObjects() {
		try{
			Foro foro = new Foro();
			emf.persist(foro);
			return new ResponseEntity<Foro>(foro,HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
*/
}