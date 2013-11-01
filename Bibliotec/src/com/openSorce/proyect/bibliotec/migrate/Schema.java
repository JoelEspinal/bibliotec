package com.openSorce.proyect.bibliotec.migrate;

import java.util.HashMap;

public class Schema extends HashMap<String, String>{

	private static final long serialVersionUID = 7433655820012919870L;

	public Schema() {
		
	}	
	public void initialize(){
		// put("tableName", "creationStatement");
		
		// People
		put("People", "create table people("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "name varchar(50) not null,"
				+ "last_name varchar(50)  not null,"
				+ "dob datetime  not null,"
				+ "id_card varchar(14),"
				+ "phone_number varchar(14),"
				+ "cell_number varchar(14),"
				+ "genre varchar(1),"
				+ "city varchar(100),"
				+ "direction varchar(255),"
				+ "create_at datetime,"
				+ "update_at datetime"
				+ ");");
		// Users
		put("Users", "create table users("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "person_id INTEGER,"
				+ "position varchar(100),"
				+ "create_at datetime,"
				+ "update_at datetime,"
				+ "FOREIGN KEY (person_id) REFERENCES people(id)"
				+ ");");
		// Permissions
		put("Permissions", "create table permissions("
						+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
						+ "name varchar(50),"
						+ "description text,"
						+ "code TEXT,"
						+ "create_at datetime,"
						+ "update_at datetime"
						+ ");");
		// Permissions_Users
		put("Permissions_Users", "create table permisions_users("
				+ "permission_id INTEGER,"
				+ "user_id INTEGER,"
				+ "FOREIGN KEY (permission_id) REFERENCES permissions(id),"
				+ "FOREIGN KEY (user_id) REFERENCES users(id)"
				+ ""
				+ ");");
		// Clients
		put("Clients", "create table clients("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "person_id INTEGER,"
				+ "id_card varchar(14),"
				+ "type varchar(50),"
				+ "reputation INTEGER,"
				+ "create_at datetime,"	
				+ "update_at datetime,"
				+ "FOREIGN KEY (person_id) REFERENCES people(id)"
				+ ");");
		// Authors
		put("Authors", "create table authors("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "person_id INTEGER,"
				+ "alias varchar(255),"
				+ "create_at datetime,"
				+ "update_at datetime,"
				+ "FOREIGN KEY (person_id) REFERENCES people(id)"
				+ ");");		
		// Category +
		put("Categories", "create table categories("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "name varchar(100),"
				+ "alias varchar(100),"
				+ "create_at datetime,"
				+ "update_at datetime"
				+ ");");
		// Book +
		put("Books", "create table books("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "title varchar(100),"
				+ "stock INTEGER,"
				+ "publication_date datetime,"
				+ "create_at timestamp,"
				+ "update_at timestamp"
				+ ");");
		// Authors_Books
		put("Authors_Books", "create table authors_books("
				+ "author_id INTEGER,"	
				+ "book_id INTEGER,"
				+ "FOREIGN KEY (author_id) REFERENCES authors(id),"
				+ "FOREIGN KEY (book_id) REFERENCES books(id)"
				+ ");");
		// Book_Category +
		put("Books_Categories", "create table books_categories("
				+ "book_id INTEGER,"
				+ "category_id INTEGER,"
				+ "FOREIGN KEY (book_id) REFERENCES books(id),"
				+ "FOREIGN KEY (category_id) REFERENCES category(id)"
				+ ");");
		// Loans
		
		put("Loans", "create table loans("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "client_id INTEGER,"
				+ "user_id INTEGER,"
				+ "create_at datetime,"
				+ "update_at datetime,"
				+ "FOREIGN KEY (client_id) REFERENCES clients(id),"
				+ "FOREIGN KEY (user_id) REFERENCES users(id)"
				+ ");");
	
		// Loan_deails
		put("Loan_deails", "create table loan_deails("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "loan_id INTEGER,"
				+ "book_id INTEGER,"
				+ "devolution_date datetime,"
				+ "penalty INTEGER"
				+ ");");
	}
}
