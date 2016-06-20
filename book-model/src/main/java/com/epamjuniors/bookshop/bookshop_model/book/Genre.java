package com.epamjuniors.bookshop.bookshop_model.book;

public enum Genre {
	PROGRAMMING,
	FICTION,
	BIOGRAPHY,
	ROMANCE,
	COMEDY,
	DRAMA,
	NONFICTION,
	SATIRE,
	TRAGEDY,
	HORROR;
	
	@Override
	public String toString(){
		if (this == Genre.NONFICTION){
			return "non-fiction";
		} else return name().toLowerCase();
	}
}
