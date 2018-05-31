package utilidades;

public class JView {

	public interface JSONSoloID{}
	public interface JSONSimple extends JSONSoloID{};
	
	public interface JSONComentario extends JSONSimple{};
	public interface JSONPublicacion extends JSONSimple{};
	public interface JSONUsuario extends JSONSimple{};
}
