package com.prokarma.zomato.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="restaurant")
public class Restaurant {

	@Id
	@Column(name="id")
	private Integer id;
	@Column(name="name")
	private String name;
	@Column(name="url")
	private String url;
	
	public Restaurant() {
        super();
    }
    public Restaurant(Integer id, String name, String url) {
        super();
        this.id = id;
        this.name = name;
        this.url = url;
    }
   
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
    public String toString() {
        return String.format("Restaurant [id=%s, name=%s, url=%s]", id, name, url);
    }

}
