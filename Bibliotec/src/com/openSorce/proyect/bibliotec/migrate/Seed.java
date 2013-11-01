package com.openSorce.proyect.bibliotec.migrate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.openSorce.proyect.bibliotec.models.Book;
import com.openSorce.proyect.bibliotec.models.Category;


public class Seed {

	public Seed(){
		
	}
	public void fillDB(){
		// Book creations
		try {
			// ---Categories creation ---
			// |
			// curiosity
			Category curiosity = new Category("Curiosity", "curiosity");
			curiosity.save();
			// adventure
			Category adventure = new Category("Adventure", "adventure");
			adventure.save();
			// mixed
			Category mixed = new Category("Mixed", "mixed");
			mixed.save();
			// technical
			Category technical = new Category("Technical", "technical");
			technical.save();
			
			// ---Books creation ---
			
			// --------------------------------- // curiosity
			// |
			Book maquinasEnElParaiso = new Book("Maquinas en el paraiso", new SimpleDateFormat("yyyy-MM-dd").parse("2001-01-01"), 200);
			maquinasEnElParaiso.save(); // Author: Jose C. Elias
			// --------------------------------- // adventure
			// |
			Book sherlockHolmes = new Book("The Adventures of Sherlock Holmes", new SimpleDateFormat("yyyy-MM-dd").parse("2001-01-01"), 200);
			sherlockHolmes.save(); // Author: Arthur Conan Doyle
			//---------------------------------
			// |
			Book demonio_Prym = new Book("El demonio y la señorita Prym", new SimpleDateFormat("yyyy-MM-dd").parse("2000-01-01"), 200);
			demonio_Prym.save(); // Author: Paulo Coelho
			//---------------------------------
			// technical
			// |
			Book java60 = new Book("Java in 60 minutes a day", new SimpleDateFormat("yyyy-MM-dd").parse("2003-01-01"), 200);
			java60.save(); // Richard Raposa
			
			
		} catch (Exception e) {e.printStackTrace();}
	}
}
