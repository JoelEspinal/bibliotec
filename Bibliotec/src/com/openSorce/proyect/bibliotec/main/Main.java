package com.openSorce.proyect.bibliotec.main;

import com.openSorce.proyect.bibliotec.connections.AbstractConnection;
import com.openSorce.proyect.bibliotec.connections.SQLite;
import com.openSorce.proyect.bibliotec.migrate.Seed;
import com.openSorce.proyect.bibliotec.models.Book;
import com.openSorce.proyect.bibliotec.models.Category;


public class Main {
	public static void main(String [] args){
		AbstractConnection.connection = SQLite.getInstance();
		
		Seed seed = new Seed();
		seed.fillDB();
		
		
		System.out.println(Book.find("Maquinas en el paraiso").atributes());
		Category.find("Curiosity");
	
		seed.drainDB();
		
	//	System.out.println(new java.sql.Date(new java.util.Date().getTime()));
		
	}
};
