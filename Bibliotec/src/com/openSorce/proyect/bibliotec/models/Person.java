package com.openSorce.proyect.bibliotec.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.openSorce.proyect.bibliotec.connections.AbstractConnection;

public class Person extends AbstractModel {

	 private String name;
	 private String lastName;
	 private Date dob = new Date();
	 private String idCard;
	 private String phoneNumber;
	 private String cellNumber;
	 private String genre;
	 private String city;
	 private String direction;
	 private Date createAt= new Date();
	 private Date updateAt= new Date();
	
	public Person(){
		
	}
	public Person(String name, String lastName, Date dob, String idCard, String phoneNumber, String cellNumber,
			String genre, String city, String direction){
		this.name = name;
		this.lastName = lastName;
		this.dob = dob;
		this.idCard = idCard;
		this.phoneNumber = phoneNumber;
		this.cellNumber = cellNumber;
		this.genre = genre;
		this.city = city;
		this.direction = direction;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getCellNumber() {
		return cellNumber;
	}
	public void setCellNumber(String cellNumber) {
		this.cellNumber = cellNumber;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
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
		if(isNew()) sql = "insert into people(name, last_name, dob, id_card, phone_number, cell_number, genre, city, direction, create_at, update_at)"
				+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		else sql = "update people set name = ?, last_name = ?, dob = ?, id_card = ?, phone_number = ?, cell_number = ?, genre = ?, city = ?, direction = ?, create_at =?, update_at = ?";
		try{
			PreparedStatement prepareStatement = AbstractConnection.getConnection().executePrepareStatement(sql);
			prepareStatement.setString(1, this.name);
			prepareStatement.setString(2, this.lastName);
			prepareStatement.setString(3, new SimpleDateFormat("yyyy-MM-dd").format(this.dob).toString());
			prepareStatement.setString(4, this.idCard);
			prepareStatement.setString(5, this.phoneNumber);
			prepareStatement.setString(6, this.cellNumber);
			prepareStatement.setString(7, this.genre);
			prepareStatement.setString(8, this.city);
			prepareStatement.setString(9, this.direction);			
			if(isNew()){prepareStatement.setString(10, new SimpleDateFormat("yyyy-MM-dd HH:MM:SS").format(new Date()).toString());}
			else prepareStatement.setString(10, new SimpleDateFormat("yyyy-MM-dd HH:MM:SS").format(createAt).toString());
			prepareStatement.setString(11, new SimpleDateFormat("yyyy-MM-dd HH:MM:SS").format(new Date()).toString());
			prepareStatement.addBatch();
			boolean result = prepareStatement.executeBatch().length > 0;
			if(result && isNew()) afterSave(); return true;
		}catch(Exception e){e.printStackTrace(); return false;}
	}
	protected static Person find(String sql, Person person){
		try {
			ResultSet result = AbstractConnection.getConnection().executeQuery(sql);
			while(result.next()){
				person.id = result.getInt("id");
				person.name = result.getString("name");
				person.lastName = result.getString("last_name");			
				person.dob = result.getDate("dob");
				person.idCard = result.getString("id_card");
				person.phoneNumber = result.getString("phone_number");
				person.cellNumber = result.getString("cell_number");
				person.genre = result.getString("genre");
				person.city = result.getString("city");
				person.direction = result.getString("direction");
				person.createAt = new SimpleDateFormat("MM-dd-yyyy").parse(result.getDate("create_at").toString());
				person.updateAt = new SimpleDateFormat("MM-dd-yyyy").parse(result.getDate("update_at").toString());
			}
		} catch (Exception e) {	e.printStackTrace();}
		return person;
	}
	public static Person find(int id){
		return find("select * from people where id=" + id +"", new Person());
	}
	public static Person find(String name){
		return find("select * from people where name="+ name, new Person());
	}
	public static List<Person> getAll(){
		List<Person> people = new ArrayList<Person>();
		ResultSet result = null;
		try{
			result = AbstractConnection.getConnection().executeQuery("select count(id) from books");
			while(result.next()){
				int count = result.getInt(1);
				for(int i=0; i < count; i++){
					result = AbstractConnection.getConnection().executeQuery("select id from people limit 1 offset " + i);				
					people.add(find(result.getInt(1)));
				}							
			}
		}catch(Exception e){e.printStackTrace();}
		return people;
	}
	@Override
	public boolean destroy() {
		try{
			AbstractConnection.getConnection().executeUpdate("delete from people where id=" + id +"");			
		}catch(Exception e){e.printStackTrace(); return false;}
		return true;
	}
	@Override
	public String atributes() {
		return "name: " + this.name + ", last_name: " + this.lastName + ", dob: " +  new SimpleDateFormat("MMM-dd-yyyy").format(this.dob) + ", id_card: " + this.idCard + 
				", phone_number: " + this.phoneNumber + ", cell_number: " + this.cellNumber + ", genre: " + this.genre + ", city: " +this.city + ", direction: " + this.direction + 
				"create_at: " + this.createAt + ", update_at: " + this.updateAt;
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
		return this.name;
	}
	@Override
	public boolean equals(Object obj) {
		return ((Book)obj).getId() == id;
	}
}
