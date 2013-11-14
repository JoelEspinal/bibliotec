package com.openSorce.proyect.bibliotec.migrate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.openSorce.proyect.bibliotec.models.Book;
import com.openSorce.proyect.bibliotec.models.Category;


public class Seed {
	private List<Category> categories;
	private List<Book> books;
	
	public Seed(){
		
	}
	public void testTransassions(){
		fillDB();
		//drainDB();
	}
	public void fillDB(){
		categories = new ArrayList<Category>();
		books = new ArrayList<Book>();
		try {
			//@#-Categories
			// ^
			// |
			// --------------------------------- //  Categories creation
			// |
			// curiosity:
			// |
				categories.add(new Category("Curiosity", "curiosity"));
			// adventure
			// |
				categories.add(new Category("Adventure", "adventure"));
			// mixed:
			// |
				categories.add(new Category("Mixed", "mixed"));
			// technical:
			// |
				categories.add(new Category("Technical", "technical"));

			// Categories insert into DB:
			// |
				for(Category category : categories){
					category.save();
				}
				
			//@#-Books
			// ^
			// |
			// Books creation
			// curiosity:
			// |
				books.add(new Book("Maquinas en el paraiso", new SimpleDateFormat("yyyy-MM-dd").parse("2001-01-01"), 200)); // Author: Jose C. Elias
			// adventure
			// |
				books.add(new Book("The Adventures of Sherlock Holmes", new SimpleDateFormat("yyyy-MM-dd").parse("2001-01-01"), 200)); // Author: Arthur Conan Doyle
			// |
			// mixed:
			// |
				books.add(new Book("El demonio y la señorita Prym", new SimpleDateFormat("yyyy-MM-dd").parse("2000-01-01"), 200)); // Author: Paulo Coelho
			//---------------------------------
			// technical:
			// |
				books.add(new Book("Java in 60 minutes a day", new SimpleDateFormat("yyyy-MM-dd").parse("2003-01-01"), 200)); // Richard Raposa

			// Book insert into DB:
			// |
				for(Book book : books){
					book.save();
				}
				
			//@#-Match books and categories
			// ^
			// |
			// find books & asing categories:
				//Book.find("Maquinas en el paraiso").addCategory(Category.find("Curiosity"));
				//Book.find("Adventure").addCategory(Category.find("Adventure"));
				//Book.find("Maquinas en el paraiso").addCategory(Category.find("Mixed"));
				//Book.find("Maquinas en el paraiso").addCategory(Category.find("technical"));
								
			// --------------------------------- // delete objects
			// |
		} catch (Exception e) {e.printStackTrace();}
	}
	public void drainDB(){
		//@#-Destroy
		// ^
		// |
		// categories:
		// |
			for(Category category : categories){
				category.destroy();
			}
		// books:
		// |
			for(Book book : books){
				book.destroy();
			}
	}
}
