package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonView;

import busquedaInterface.BusquedaComentarios;
import utilidades.BusquedaFactory;
import utilidades.JView;

public class Publicacion {
	@JsonView(JView.JSONSoloID.class)
	private Long id;
	@JsonView(JView.JSONSoloID.class)
	private Long version;
	@JsonView(JView.JSONSimple.class)
	private String titulo;
	@JsonView(JView.JSONPublicacion.class)
	private List<Comentario> comentarios = new ArrayList<Comentario>();
	
	private BusquedaComentarios busquedaComentarios;
	
	public Publicacion() {
		super();
		this.busquedaComentarios = BusquedaFactory.getInstance().getBusquedaComentarios();
	}
	
	public Publicacion(String titulo) {
		super();
		this.titulo = titulo;
		this.busquedaComentarios = BusquedaFactory.getInstance().getBusquedaComentarios();
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public List<Comentario> getComentariosOrdenados() {
		return (List<Comentario>) this.busquedaComentarios.findByPublicacionOrderByFechaAsc(this);
	}

	public List<Comentario> getComentarios() {
		return comentarios;
	}
	
	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	public void addComentario(String texto, Usuario usuario){
		Comentario comentario = new Comentario(texto, usuario);
		comentario.setPublicacion(this);
		this.comentarios.add(comentario);
	}
	
	public void addComentario(Comentario comentario){
		comentario.setPublicacion(this);
		this.comentarios.add(comentario);
	}
	
	public void removeComentario(Comentario comentario){
		comentario.getUsuario().getComentarios().remove(comentario);
		this.comentarios.remove(comentario);
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "Publicacion [id=" + id + ", titulo=" + titulo + " COM=" + comentarios + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (!this.getClass().isInstance(obj)){
			return false;
		}
		else{
			Publicacion p = (Publicacion) obj;
			return this.getId().equals(p.getId()) && this.getTitulo().equals(p.getTitulo());
		}
		
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id,titulo);
	}
}
