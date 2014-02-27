package com.vero.model.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the SEARCHABLE_TEXT database table.
 * 
 */
@Entity
@Table(name="SEARCHABLE_TEXT")
@NamedQuery(name="SearchableText.findAll", query="SELECT s FROM SearchableText s")
public class SearchableText extends SchemaData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(length=100)
	private String name;

	@Column(name="OBJECT_TYPE", nullable=false, length=10)
	private String objectType;

	public SearchableText() {
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getObjectType() {
		return this.objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

}