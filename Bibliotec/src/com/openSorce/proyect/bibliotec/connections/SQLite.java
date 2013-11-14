package com.openSorce.proyect.bibliotec.connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;

import com.openSorce.proyect.bibliotec.migrate.Schema;

public class SQLite extends AbstractConnection{
	
	private static SQLite instance;
	private static Connection connection;
	
	private SQLite(){
		
	}
	public static SQLite getInstance(){
		if(instance == null){
			instance = new SQLite();
			instance.createTables();
		}
		return instance;
	}
	public Connection startConnection()throws Exception{		
		String dbURL = System.getProperty("user.dir") + "/src/com/openSorce/proyect/bibliotec/db/bibliotec.db";
		close();
		Class.forName("org.sqlite.JDBC");
		connection = DriverManager.getConnection("jdbc:sqlite:" + dbURL);		
		return connection;
	}
	private void createTables(){
		Schema schema = new Schema();
		schema.initialize();
		try{
			SQLite conn = null;
			conn = SQLite.getInstance();
			conn.executeUpdate(new ArrayList<String>(schema.values()));			
		}catch(Exception e){e.printStackTrace();}
	}
	@Override
	public ResultSet executeQuery(String sql)throws Exception{
		System.out.println(sql);
		return startConnection().createStatement().executeQuery(sql);		
	}
	@Override
	public boolean executeUpdate(String sql)throws Exception{
		System.out.println(sql);
		startConnection().createStatement().executeUpdate(sql);
		return true;
	}
	public boolean executeUpdate(List<String> sql)throws Exception{
		System.out.println(sql);
		Statement statement = startConnection().createStatement();
		for(String s : sql){
			statement.addBatch(s);			
		}
		statement.executeBatch();
		return true;
	}
	@Override
	public PreparedStatement executePrepareStatement(String sql) throws Exception {
		return startConnection().prepareStatement(sql);
	}
	public static void close(){
		if(connection  != null){
			try{connection.close();}catch(Exception e){e.printStackTrace();}			
		}
	}
}
