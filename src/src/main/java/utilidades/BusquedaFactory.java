package utilidades;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import busquedaImplementacion.BusquedaComentariosImpl2;
import busquedaInterface.BusquedaComentarios;
import busquedaInterface.BusquedaPublicaciones;
import busquedaInterface.BusquedaUsuarios;
import busquedaInterface.busquedaForo;
import modelo.Comentario;
import modelo.Usuario;

public class BusquedaFactory {
	
	private static BusquedaFactory instance;
	
	public static BusquedaFactory getInstance(){
		if(instance == null){
			instance = new BusquedaFactory();
		}
		return instance;
	}
	
	private BusquedaComentarios busquedaComentarios;
	
	private BusquedaPublicaciones busquedaPublicaciones;
	
	private BusquedaUsuarios busquedaUsuarios;
	
	private busquedaForo busquedaForo;
	
	private BusquedaFactory(){
		super();
	}
	
	public BusquedaComentarios getBusquedaComentarios() {
		return busquedaComentarios;
	}
	public void setBusquedaComentarios(BusquedaComentarios busquedaComentarios) {
		this.busquedaComentarios = busquedaComentarios;
	}
	public BusquedaPublicaciones getBusquedaPublicaciones() {
		return busquedaPublicaciones;
	}
	public void setBusquedaPublicaciones(BusquedaPublicaciones busquedaPublicaciones) {
		this.busquedaPublicaciones = busquedaPublicaciones;
	}
	public BusquedaUsuarios getBusquedaUsuarios() {
		return busquedaUsuarios;
	}
	public void setBusquedaUsuarios(BusquedaUsuarios busquedaUsuarios) {
		this.busquedaUsuarios = busquedaUsuarios;
	}

	public busquedaForo getBusquedaForo() {
		return busquedaForo;
	}

	public void setBusquedaForo(busquedaForo busquedaForo) {
		this.busquedaForo = busquedaForo;
	}
	
}
