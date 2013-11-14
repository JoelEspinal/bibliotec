package com.openSorce.proyect.bibliotec.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.openSorce.proyect.bibliotec.connections.AbstractConnection;

public class Permission extends AbstractModel {

	private String name;
	private String description;
	private String code;
	private Date createAt = new Date();
	private Date updateAt = new Date();
	
	public Permission(){
		
	}
	public Permission(String name, String description, String code){
		this.name = name;
		this.description = description;
		this.code = code;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
		if(isNew()) sql = "insert into permissions(name, description, code, create_at, update_at) values (?, ?, ?, ?, ?)";
		else sql = "update permissions set name = ?, description = ?, code = ?, update_at = ?";
		try{
			PreparedStatement prepareStatement = AbstractConnection.getConnection().executePrepareStatement(sql);
			prepareStatement.setString(1, this.name);
			prepareStatement.setString(2, this.description);
			prepareStatement.setString(3, this.code);
			if(isNew()) prepareStatement.setString(4, new SimpleDateFormat("yyyy-MM-dd HH:MM:SS").format(new Date()).toString());
			else prepareStatement.setString(4, new SimpleDateFormat("yyyy-MM-dd HH:MM:SS").format(createAt).toString());
			prepareStatement.setString(5, new SimpleDateFormat("yyyy-MM-dd HH:MM:SS").format(createAt).toString());
			prepareStatement.addBatch();
			boolean result = prepareStatement.executeBatch().length > 0;
			if(result && isNew()) afterSave(); return true;
		}catch(Exception e){e.printStackTrace(); return false;}
	}	
	private static Permission find(String sql, Permission permission){
		try {
			ResultSet result = AbstractConnection.getConnection().executeQuery(sql);
			while(result.next()){
				permission.id = result.getInt("id");
				permission.name = result.getString("name");
				permission.description = result.getString("description");
				permission.code = result.getString("code");
				permission.createAt = new SimpleDateFormat("MM-dd-yyyy").parse(result.getDate("create_at").toString());
				permission.updateAt = new SimpleDateFormat("MM-dd-yyyy").parse(result.getDate("update_at").toString());				
			}
		} catch (Exception e) {	e.printStackTrace();}
		return permission;
	}
	public static Permission find(int id){
		return find("select * from books where id=" + id +"", new Permission());
	}
	public static Permission find(String code){
		return find("select * from books where code="+ code, new Permission());
	}
	public static List<Permission> getAll(){
		List<Permission> Permissions = new ArrayList<Permission>();
		ResultSet result = null;
		try{
			result = AbstractConnection.getConnection().executeQuery("select count(id) from permissions");
			while(result.next()){
				int count = result.getInt(1);
				for(int i=0; i < count; i++){
					result = AbstractConnection.getConnection().executeQuery("select id from permissions limit 1 offset " + i);				
					Permissions.add(find(result.getInt(1)));				
			}			
		}			
		}catch(Exception e){e.printStackTrace();}
		return Permissions;
	}
	@Override
	public boolean destroy() {
		try{
			AbstractConnection.getConnection().executeUpdate("delete from permissions where id=" + id +"");			
		}catch(Exception e){e.printStackTrace(); return false;}
		return true;
	}
	@Override
	public String atributes() {
		return "[id: " + this.id + ", name: " + this.name + ", code: " + this.code + "]";
	}
	@Override
	public void afterSave() {
		try{
			ResultSet result = AbstractConnection.getConnection().executeQuery("select max(id) from permissions");
			if(result != null) id = result.getInt(1);			
		}catch(Exception e){e.printStackTrace();}
	}
	@Override
	public String toString() {
		return this.code;
	}
	@Override
	public boolean equals(Object obj) {
		return ((Book)obj).getId() == id;
	}
}
