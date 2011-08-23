package org.pasut.quecomemoshoy.services.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.opensource.pasut.persister.mongodb.PersisterService;
import org.pasut.quecomemoshoy.repository.RecipeProvider;
import org.pasut.quecomemoshoy.repository.RecipeProviderImpl;
import org.pasut.quecomemoshoy.repository.config.PersisterServiceProvider;
import org.pasut.quecomemoshoy.services.RecipesService;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

public class Builder extends AbstractModule {

	@Override
	protected void configure() {
		Properties properties = loadProperties("/config.properties");
		Names.bindProperties(binder(), properties);
		System.out.println(properties);
		this.bind(PersisterService.class).toProvider(PersisterServiceProvider.class);
		this.bind(RecipeProvider.class).to(RecipeProviderImpl.class);
		this.bind(RecipesService.class);
	}
	
	private static Properties loadProperties(String name){
		Properties props = new Properties();
		InputStream is = new Object(){}
							.getClass()
							.getEnclosingClass()
							.getResourceAsStream(name);
		try{
			props.load(is);
		}catch(IOException e){
			throw new RuntimeException(e);
		} finally {
			if(is != null){
				try{
					is.close();
				}catch(IOException e){}
			}
		}
		return props;
	}

}
