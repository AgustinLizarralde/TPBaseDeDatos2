package hibernateUtil;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.EmptyInterceptor;
import org.hibernate.Transaction;

/**
 * Esta clase es solo para pruebas
 *
 */
public class TestInterceptor extends EmptyInterceptor {
	
	@Override
	public Boolean isTransient(Object entity) {
		if( entity instanceof modelo.Comentario ||
				entity instanceof modelo.Publicacion ||
				entity instanceof modelo.Usuario){
			return false;
		}
		else{
			return super.isTransient(entity);
		}
	}

	@Override
	public void beforeTransactionCompletion(Transaction tx) {
		super.beforeTransactionCompletion(tx);
	}

}
