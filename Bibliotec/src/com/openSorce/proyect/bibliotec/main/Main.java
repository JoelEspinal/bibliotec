package com.openSorce.proyect.bibliotec.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.openSorce.proyect.bibliotec.connections.AbstractConnection;
import com.openSorce.proyect.bibliotec.connections.SQLite;
import com.openSorce.proyect.bibliotec.models.Book;

public class Main {
	public static void main(String [] args){
		AbstractConnection.connection = SQLite.getInstance();
		
		Book b = new Book();
		b.setTitle("Cien anios de soledad");
		b.setStock(50);
		b.save();
		try {
			System.out.println(new SimpleDateFormat("MM-dd-yyyy").parse("2013-10-31").getTime());
			b.setPublicationDate(new SimpleDateFormat("MM-dd-yyyy").parse("2013-10-31"));
		} catch (ParseException e) {e.printStackTrace();}
		System.out.println("__----------------------------------------___");
		for(Book book : Book.getAll()){
			System.out.println(book.atributes());
		}
	}
}
