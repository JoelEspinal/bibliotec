package com.openSorce.proyect.bibliotec.models;

public abstract class AbstractModel {
	public int id;
	
	public int getId(){
		return id;
	}
	public void setId(int id){
		this.id = id;
	}
	public boolean isNew(){
		return id == 0;
	}
	@Override
	public int hashCode() {
		return id;
	}
	public abstract boolean save();
	public abstract boolean destroy();
	public abstract String atributes();
	public abstract void afterSave();
}
