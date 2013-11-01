package com.openSorce.proyect.bibliotec.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.openSorce.proyect.bibliotec.connections.AbstractConnection;

public class Book extends AbstractModel {

	private String title;
	private Date publicationDate = new Date();
	private Date createAt = new Date();
	private Date updateAt = new Date();
	private int stock;
	
	public Book(){
		
	}
	
	public Book(String title, Date publicationDate, int stock){
		this();
		this.title = title;
		this.publicationDate = publicationDate;
		this.stock = stock;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getPublicationDate(){
		return publicationDate;
	}
	public void setPublicationDate(Date publicationDate){
		this.publicationDate = publicationDate;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public Date getUpdateAt() {
		return updateAt;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}	
	@Override
	public boolean save() {
		String sql = "";
		if(isNew()) sql = "insert into books(title, publication_date, stock, create_at, update_at) values (?, ?, ?, ?, ?)";
		else sql = "update books set title = ?, publication_date = ?, stock = ?, create_at = ?, update_at = ?";
		try{
			PreparedStatement prepareStatement = AbstractConnection.getConnection().executePrepareStatement(sql);
			prepareStatement.setString(1, this.title);
			prepareStatement.setString(2, new SimpleDateFormat("yyyy-MM-dd").format(publicationDate).toString());
			prepareStatement.setInt(3, this.stock);
			if(isNew()){prepareStatement.setString(4, new SimpleDateFormat("yyyy-MM-dd HH:MM:SS").format(createAt).toString());}
			prepareStatement.setString(5, new SimpleDateFormat("yyyy-MM-dd HH:MM:SS").format(createAt).toString());
			prepareStatement.addBatch();
			boolean result = prepareStatement.executeBatch().length > 0;
			if(result && isNew()) afterSave(); return true;
		}catch(Exception e){e.printStackTrace(); return false;}
	}

	@Override
	public boolean destroy() {
		try{
			AbstractConnection.getConnection().executeUpdate("delete from books where id=" + id +"");			
		}catch(Exception e){e.printStackTrace();}
		return false;
	}
	private static Book find(String sql, Book book){
		try {
			ResultSet result = AbstractConnection.getConnection().executeQuery(sql);
			book.id = result.getInt("id");
			book.title = result.getString("title");
			book.publicationDate = result.getDate("publication_date");
			book.stock = result.getInt("stock");
			book.publicationDate = new SimpleDateFormat("MM-dd-yyyy").parse(result.getDate("publication_date").toString());
			book.createAt = new SimpleDateFormat("MM-dd-yyyy").parse(result.getDate("create_at").toString());
			book.updateAt = new SimpleDateFormat("MM-dd-yyyy").parse(result.getDate("update_at").toString());
		} catch (Exception e) {	e.printStackTrace();}
		return book;
	}
	public static Book find(int id){
		return find("select * from books where id=" + id +"", new Book());
	}
	public static Book find(String title){
		return find("select * from books where title="+ title, new Book());
	}
	public static List<com.openSorce.proyect.bibliotec.models.Book> getAll(){
		List<com.openSorce.proyect.bibliotec.models.Book> books = new ArrayList<com.openSorce.proyect.bibliotec.models.Book>();
		ResultSet result = null;
		try{
			result = AbstractConnection.getConnection().executeQuery("select count(id) from books");
			result.next();
			int count = result.getInt(1);
			System.out.println("Count: " + count);
			for(int i=0; i < count; i++){
				System.out.println("Iterator: " + i);
				result = AbstractConnection.getConnection().executeQuery("select id from books limit 1 offset " + i);				
				books.add(find(result.getInt(1)));
				result.next();
			}			
		}catch(Exception e){e.printStackTrace();}
		return books;
	}
	@Override
	public String atributes() {
		return "id: " + this.getId() + " title: " + title + ", publication_date" + new SimpleDateFormat("MMM-dd-yyyy HH:mm:ss").format(getPublicationDate()) + " stock: " + stock + " Created_at: " + new SimpleDateFormat("MMM-dd-yyyy HH:mm:ss").format(getCreateAt()) + " update_at: " + new SimpleDateFormat("MMM-dd-yyyy HH:mm:ss").format(getUpdateAt());
	}
	@Override
	public void afterSave() {				
		try{
			ResultSet result = AbstractConnection.getConnection().executeQuery("select max(id) from books");
			if(result != null) id = result.getInt(1);			
		}catch(Exception e){e.printStackTrace();}
	}
	@Override
	public String toString() {
		return getTitle();
	}
	@Override
	public boolean equals(Object obj) {
		return ((Book)obj).getId() == id;
	}
};
