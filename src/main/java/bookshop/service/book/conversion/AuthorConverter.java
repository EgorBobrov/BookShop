package bookshop.service.book.conversion;

import java.beans.PropertyEditorSupport; 
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Component;

import bookshop.model.book.Author;
import bookshop.service.book.BookService;

@Component 
public class AuthorConverter extends PropertyEditorSupport { 
 
	@Autowired
    private BookService bookService;
	
	@SuppressWarnings("unused")
	private AuthorConverter() {}
	
	public AuthorConverter (BookService bService){
		this.bookService=bService;
	}
 
    /**
     * {@inheritDoc} 
     */ 
    @Override 
    public void setAsText(final String text) { 
        try {
            final Set<Author> authors = toAuthor(text); 
            System.out.println("AuthorConverter: "+authors.size()+ " authors");
            this.bookService.persistAuthors(authors);

        } catch (final Exception e) { 
 
        } 
    } 
    
    public static Set<Author> toAuthor (String text){
    	String[] authors = text.replaceAll("\\[|\\]", "").split(",");
    	Set<Author> setAuthors = new HashSet<Author>();
    	for (String a: authors){
    		setAuthors.add(new Author(a.trim()));
    	}
    	return setAuthors;
    }
}
