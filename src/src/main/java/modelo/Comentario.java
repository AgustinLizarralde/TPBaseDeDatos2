package modelo;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonView;

import utilidades.JView;

public class Comentario {
	@JsonView(JView.JSONSoloID.class)
	private Long id;
	@JsonView(JView.JSONSoloID.class)
	private Long version;
	@JsonView(JView.JSONSimple.class)
	private String texto;
	@JsonView(JView.JSONComentario.class)
	private Usuario usuario;
	@JsonView(JView.JSONSimple.class)
	private Date fecha;
	@JsonView(JView.JSONComentario.class)
	private Publicacion publicacion;
	
	public Comentario() {
		super();
	}
	
	protected Comentario(String texto, Usuario usuario){
		super();
		this.texto = texto;
		this.usuario = usuario;
		this.fecha = new Date();
		usuario.getComentarios().add(this);
	}
	
	public Comentario(String texto, Usuario usuario, Publicacion publicacion) {
		super();
		this.texto = texto;
		this.usuario = usuario;
		this.fecha = new Date();
		this.publicacion = publicacion;
		usuario.getComentarios().add(this);
		publicacion.addComentario(this);
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Publicacion getPublicacion() {
		return publicacion;
	}
	public void setPublicacion(Publicacion publicacion) {
		this.publicacion = publicacion;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "Comentario [id=" + id + ", texto=" + texto + ", usuario=" + usuario.getNombre() + ", fecha=" + fecha
				+ ", publicacion=" + publicacion.getTitulo() + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!this.getClass().isInstance(obj)){
			return false;
		}
		else{
			Comentario c = (Comentario) obj;
			return (this.getFecha().equals(c.getFecha())) && (this.getTexto().equals(c.getTexto())) && (this.getUsuario().getId().equals(c.getUsuario().getId()));
		}
		
	}

	@Override
	public int hashCode() {
		return Objects.hash(id,fecha);
	}
	
}
