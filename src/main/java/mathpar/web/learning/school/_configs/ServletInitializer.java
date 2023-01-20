package mathpar.web.learning.school._configs;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(LearningApplication.class);
	}

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		try {
			super.onStartup(servletContext);
		}catch (Exception e){
			System.exit(1);
		}
	}
}
