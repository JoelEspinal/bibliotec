package com.openSorce.proyect.bibliotec.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.openSorce.proyect.bibliotec.connections.AbstractConnection;

public class Category extends AbstractModel {

	private String name;
	private String alias;
	private Date createAt = new Date();
	private Date updateAt = new Date();
	
	public Category(){
		
	}
	public Category(String name, String alias){
		this();
		this.name = name;
		this.alias = alias;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public Date getUpdateAt() {
		return updateAt;
	}
	@Override
	public boolean save() {
		String sql = "";
		if(isNew()) sql = "insert into categories(name, alias, create_at, update_at) values (?, ?, ?, ?)";
		else sql = "update books set name = ?, alias = ?, create_at = ?, update_at = ?";
		try{
			PreparedStatement prepareStatement = AbstractConnection.getConnection().executePrepareStatement(sql);
			prepareStatement.setString(1, this.name);
			prepareStatement.setString(2, this.alias);
			if(isNew()){prepareStatement.setString(3, new SimpleDateFormat("yyyy-MM-dd HH:MM:SS").format(createAt).toString());}
			prepareStatement.setString(4, new SimpleDateFormat("yyyy-MM-dd HH:MM:SS").format(createAt).toString());
			prepareStatement.addBatch();
			boolean result = prepareStatement.executeBatch().length > 0;
			if(result && isNew()) afterSave(); return true;
		}catch(Exception e){e.printStackTrace(); return false;}
	}

	@Override
	public boolean destroy() {
		try{
			AbstractConnection.getConnection().executeUpdate("delete from categories where id=" + id +"");			
		}catch(Exception e){e.printStackTrace();}
		return false;
	}
	private static Category find(String sql, Category category){
		try {
			ResultSet result = AbstractConnection.getConnection().executeQuery(sql);
			category.id = result.getInt("id");
			category.name = result.getString("name");
			category.alias = result.getString("alias");
			category.createAt = new SimpleDateFormat("MM-dd-yyyy").parse(result.getDate("create_at").toString());
			category.updateAt = new SimpleDateFormat("MM-dd-yyyy").parse(result.getDate("update_at").toString());
		} catch (Exception e) {	e.printStackTrace();}
		return category;
	}
	public static  Category find(int id){
		return find("select * from categories where id=" + id +"", new Category());
	}
	public static Category find(String name){
		return find("select * from categories where name=" + name, new Category());
	}
	public static List<com.openSorce.proyect.bibliotec.models.Category> getAll(){
		List<com.openSorce.proyect.bibliotec.models.Category> categories = new ArrayList<com.openSorce.proyect.bibliotec.models.Category>();
		ResultSet result = null;
		try{
			result = AbstractConnection.getConnection().executeQuery("select count(id) from categories");
			result.next();
			int count = result.getInt(1);
			System.out.println("Count: " + count);
			for(int i=0; i < count; i++){
				System.out.println("Iterator: " + i);
				result = AbstractConnection.getConnection().executeQuery("select id from categories limit 1 offset " + i);				
				categories.add(find(result.getInt(1)));
				result.next();
			}			
		}catch(Exception e){e.printStackTrace();}
		return categories;
	}
	@Override
	public String atributes() {
		return "id: " + id + "Name: " + name + "Alias: " + alias + " Created_at: " + new SimpleDateFormat("MMM-dd-yyyy HH:mm:ss").format(getCreateAt()) + " update_at: " + new SimpleDateFormat("MMM-dd-yyyy HH:mm:ss").format(getUpdateAt());
	}
	@Override
	public void afterSave() {
		try{
			ResultSet result = AbstractConnection.getConnection().executeQuery("select max(id) from categories");
			if(result != null) id = result.getInt(1);			
		}catch(Exception e){e.printStackTrace();}
	}
	@Override
	public String toString() {
		return "Alias: " + alias;
	}
	@Override
	public boolean equals(Object obj) {
		return ((Category)obj).getId() == id;
	}
}
