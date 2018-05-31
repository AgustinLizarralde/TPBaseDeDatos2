package modelo;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import busquedaInterface.BusquedaPublicaciones;
import busquedaInterface.BusquedaUsuarios;
import utilidades.BusquedaFactory;
import utilidades.JView;

public class Foro {
	
	@JsonView(JView.JSONSoloID.class)
	private Long id;
	@JsonView(JView.JSONSoloID.class)
	private Long version;
	private List<Usuario> usuarios = new ArrayList<Usuario>();
	private List<Publicacion> publicaciones = new ArrayList<Publicacion>();
	
	private BusquedaUsuarios busquedaUsuarios;
	private BusquedaPublicaciones busquedaPublicaciones;

	public Foro(){
		super();
		busquedaUsuarios = BusquedaFactory.getInstance().getBusquedaUsuarios();
		busquedaPublicaciones = BusquedaFactory.getInstance().getBusquedaPublicaciones();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	public boolean addUsuario(Usuario usuario) {
		if(this.usuarioPuedeRegistrarse(usuario)){
			this.usuarios.add(usuario);
			return true;
		}
		else{
			return false;
		}
	}
	public void removeUsuario(Usuario usuario){
		this.usuarios.remove(usuario);
	}
	public Usuario findUsuarioByNombre(String nombre){
		return busquedaUsuarios.findByNombre(nombre).orElse(null);
	}
	public boolean usuarioPuedeRegistrarse(Usuario usuario){
		return (!busquedaUsuarios.findByNombre(usuario.getNombre()).isPresent());
	}
	public boolean usuarioLogueo(String nombre, String password){
		try{
			return this.findUsuarioByNombre(nombre).getPassword().equals(password);
		}
		catch (Exception e) {
			return false;
		}
	}
	public boolean usuarioCambiarContraseña(Usuario usuario, String oldPass, String newPass){
		if(usuario.getPassword().equals(oldPass)){
			usuario.setPassword(newPass);
			return true;
		}
		else{
			return false;
		}
	}
	
	public List<Publicacion> getPublicaciones() {
		return publicaciones;
	}
	public void setPublicaciones(List<Publicacion> publicaciones) {
		this.publicaciones = publicaciones;
	}
	public boolean addPublicacion(Publicacion publicacion) {
		if(this.puedePublicarse(publicacion)){
			return this.publicaciones.add(publicacion);
		}
		else{
			return false;
		}
		
	}
	public boolean puedePublicarse(Publicacion publicacion){
		return (!busquedaPublicaciones.findByTitulo(publicacion.getTitulo()).isPresent());
	}
	public void removePublicacion(Publicacion publicacion){
		this.publicaciones.remove(publicacion);
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public BusquedaUsuarios getBusquedaUsuarios() {
		return busquedaUsuarios;
	}

	public void setBusquedaUsuarios(BusquedaUsuarios busquedaUsuarios) {
		this.busquedaUsuarios = busquedaUsuarios;
	}

	public BusquedaPublicaciones getBusquedaPublicaciones() {
		return busquedaPublicaciones;
	}

	public void setBusquedaPublicaciones(BusquedaPublicaciones busquedaPublicaciones) {
		this.busquedaPublicaciones = busquedaPublicaciones;
	}

	@Override
	public String toString() {
		return "Foro [id=" + id + ", usuarios[" + usuarios + "], publicaciones[" + publicaciones + "] ]";
	}
}
