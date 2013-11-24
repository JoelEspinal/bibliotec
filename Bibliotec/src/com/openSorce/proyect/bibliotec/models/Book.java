package com.openSorce.proyect.bibliotec.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.openSorce.proyect.bibliotec.connections.AbstractConnection;
import com.openSorce.proyect.bibliotec.utils.DateUtil;

public class Book extends AbstractModel {

	private String title;
	private int stock;
	private Date publicationDate;
	private Date createAt;
	private Date updateAt;
	
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
		if(isNew()) sql = "insert into books(title, publication_date, stock, create_at, update_at) values (?, ?, ?, ?, ?);";
		else sql = "update books set title = ?, publication_date = ?, stock = ?, create_at = ?, update_at = ?;";
		try{
			PreparedStatement prepareStatement = AbstractConnection.getConnection().executePrepareStatement(sql);
			prepareStatement.setString(1, this.title);
			prepareStatement.setString(2, DateUtil.stringify(publicationDate));
			prepareStatement.setInt(3, this.stock);
			if(isNew()){prepareStatement.setString(4, DateUtil.stringify(new Date()));}
			else prepareStatement.setString(4, DateUtil.stringify(createAt));
			prepareStatement.setString(5, DateUtil.stringify(new Date()));
			prepareStatement.addBatch();
			boolean result = prepareStatement.executeBatch().length > 0;
			if(result && isNew()) afterSave(); return true;
		}catch(Exception e){e.printStackTrace(); return false;}
	}
	protected void makeTransaction(String sql, int categoryId){
		try{			
			PreparedStatement prepareStatement = AbstractConnection.getConnection().executePrepareStatement(sql);
			prepareStatement.setInt(1, this.getId());
			prepareStatement.setInt(2, categoryId);
			prepareStatement.addBatch();
			prepareStatement.executeBatch();
		}catch(Exception e){e.printStackTrace();}
	}
	public void addCategory(Category category){
		makeTransaction("insert into books_categories(book_id, category_id) values(?, ?);", category.getId());
	}
	public void removeCategory(Category category){
		makeTransaction("delete from books_categories where book_id= ? AND category_id= ?;", category.getId());
	}
	@Override
	public boolean destroy() {
		try{
			AbstractConnection.getConnection().executeUpdate("delete from books where id=" + id +";");			
		}catch(Exception e){e.printStackTrace(); return false;}
		return true;
	}
	protected static Book find(String sql, Book book){
		try {
			ResultSet result = AbstractConnection.getConnection().executeQuery(sql);
			while(result.next()){
				book.id = result.getInt("id");
				book.title = result.getString("title");
				book.publicationDate = result.getDate("publication_date");
				book.stock = result.getInt("stock");
				
				book.publicationDate = result.getDate("publication_date");
				book.createAt = result.getDate("create_at");
				book.updateAt = result.getDate("update_at");
				book.publicationDate = DateUtil.toFormat(result.getTimestamp("publication_date"));
				book.createAt = DateUtil.toFormat(result.getTimestamp("create_at"));
				book.updateAt = DateUtil.toFormat(result.getTimestamp("update_at"));				
			}
		} catch (Exception e) {	e.printStackTrace();}
		return book;
	}
	public static Book find(int id){
		return find("select * from books where id=" + id +";", new Book());
	}
	public static Book find(String title){
		return find("select * from books where title=" +"'" + title  +"';", new Book());
	}
	public static List<com.openSorce.proyect.bibliotec.models.Book> getAll(){
		List<com.openSorce.proyect.bibliotec.models.Book> books = new ArrayList<com.openSorce.proyect.bibliotec.models.Book>();
		ResultSet result = null;
		try{
			result = AbstractConnection.getConnection().executeQuery("select count(id) from books;");			
			while(result.next()){
				int count = result.getInt(1);
				for(int i=0; i < count; i++){
				result = AbstractConnection.getConnection().executeQuery("select id from books limit 1 offset " + i + ";");				
				books.add(find(result.getInt(1)));
				result.next();				
			}
		}			
		}catch(Exception e){e.printStackTrace();}
		return books;
	}
	@Override
	public String atributes() {
		try {
			return "[id: " + this.id + " title: " + title + ", publication_date: " + DateUtil.toFormat(getCreateAt()) + " update_at: " + DateUtil.toFormat(getUpdateAt()) + "]";
		} catch (Exception e) {e.printStackTrace(); return "None";}
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
		return this.title;
	}
	@Override
	public boolean equals(Object obj) {
		return ((Book)obj).getId() == id;
	}
};
