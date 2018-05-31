package hibernateUtil;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Esta clase es solo para pruebas
 *
 */
@Order(Ordered.LOWEST_PRECEDENCE)
public class TransactionFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		Session session = MySessionFactory.getFactory().openSession();
		Transaction tx = session.getTransaction();
		try {
			tx.begin();
			request.setAttribute("EntityManager", new MyEntityManager(session));
			Buscador buscador = new Buscador(session);
			request.setAttribute("foro", buscador.getForo());
			request.setAttribute("buscador", buscador);
		
			chain.doFilter(request, response);
		
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			session.close();
		}
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
