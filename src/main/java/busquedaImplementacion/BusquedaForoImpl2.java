package busquedaImplementacion;

import java.util.Optional;

import busquedaInterface.busquedaForo;
import modelo.Foro;

public class BusquedaForoImpl2 implements busquedaForo {
	Foro foro;
	
	public BusquedaForoImpl2(Foro foro) {
		this.foro = foro;
	}
	
	@Override
	public Optional<Foro> findForo() {
		if(foro != null){
			return Optional.of(foro);
		}
		return Optional.empty();
	}
}
