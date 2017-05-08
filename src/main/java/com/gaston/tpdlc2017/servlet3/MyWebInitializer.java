package com.gaston.tpdlc2017.servlet3;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.gaston.tpdlc2017.config.SpringRootConfig;
import com.gaston.tpdlc2017.config.SpringWebConfig;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;
import java.io.File;

public class MyWebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	private String TMP_FOLDER = "/tmp";
	private int MAX_UPLOAD_SIZE = 20 * 1024 * 1024;

	@Override
	protected void customizeRegistration(ServletRegistration.Dynamic registration) {

		// upload temp file will put here
		File uploadDirectory = new File(TMP_FOLDER);

		// register a MultipartConfigElement
		MultipartConfigElement multipartConfigElement =
				new MultipartConfigElement(uploadDirectory.getAbsolutePath(),
						MAX_UPLOAD_SIZE, MAX_UPLOAD_SIZE * 2, MAX_UPLOAD_SIZE / 2);

		registration.setMultipartConfig(multipartConfigElement);

	}

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { SpringRootConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { SpringWebConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

}