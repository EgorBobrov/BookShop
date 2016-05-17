package bookshop.service.book.conversion;

import java.beans.PropertyEditorSupport; 

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Component;

import bookshop.model.book.Author;
import bookshop.service.book.AuthorService;

@Component 
public class AuthorConverter extends PropertyEditorSupport { 
 
	@Autowired
    private AuthorService authorService; 
 
    /**
     * {@inheritDoc} 
     */ 
    @Override 
    public void setAsText(final String text) { 
        try { 
            final Author author = this.toAuthor(text); 
            System.out.println("Will try to persist author");
            this.authorService.persistAuthor(author);

        } catch (final Exception e) { 
 
        } 
    } 
    
    public Author toAuthor (String text){
    	//this is to be redone yet
    	return new Author (text);
    }
}
