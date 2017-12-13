package com.numnu.android.network.response;

public class TagsItem{
	private int id;
	private String text;
	private int displayorder;

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setText(String text){
		this.text = text;
	}

	public String getText(){
		return text;
	}

	public void setDisplayorder(int displayorder){
		this.displayorder = displayorder;
	}

	public int getDisplayorder(){
		return displayorder;
	}

	@Override
 	public String toString(){
		return 
			"TagsItem{" + 
			"id = '" + id + '\'' + 
			",text = '" + text + '\'' + 
			",displayorder = '" + displayorder + '\'' + 
			"}";
		}
}
