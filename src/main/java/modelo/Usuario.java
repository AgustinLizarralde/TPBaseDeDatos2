package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonView;

import utilidades.JView;

public class Usuario {
	@JsonView(JView.JSONSoloID.class)
	private Long id;
	@JsonView(JView.JSONSoloID.class)
	private Long version;
	@JsonView(JView.JSONSimple.class)
	private String nombre;
	@JsonView(JView.JSONUsuario.class)
	private String password;
	@JsonView(JView.JSONUsuario.class)
	private List<Comentario> comentarios = new ArrayList<Comentario>();
	
	public Usuario() {
		super();
	}
	
	public Usuario(String nombre, String password) {
		super();
		this.nombre = nombre;
		this.password = password;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<Comentario> getComentarios() {
		return comentarios;
	}
	
	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}
	
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + " COM: "+comentarios+"]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!this.getClass().isInstance(obj)){
			return false;
		}
		else{
			Usuario p = (Usuario) obj;
			return this.getId().equals(p.getId()) && this.getNombre().equals(p.getNombre());
		}
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id,nombre);
	}
	
}
