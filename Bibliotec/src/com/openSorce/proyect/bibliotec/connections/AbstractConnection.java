package com.openSorce.proyect.bibliotec.connections;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public abstract class AbstractConnection {
	
	public static AbstractConnection connection;
     
     public abstract ResultSet executeQuery(String sql)throws Exception;
     public abstract boolean executeUpdate(String sql)throws Exception;
     public abstract PreparedStatement executePrepareStatement(String sql)throws Exception;
     public static AbstractConnection getConnection(){
             return connection;
     }
}
