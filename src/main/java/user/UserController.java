/*
 * This is the Controller. It handles specific requests to some URLs by building specific View using 
 * some logic and the Model class. 
 */

package user;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.BasicConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
 
@Controller
public class UserController {
 
    @Autowired
    private UserDao userDao;
 
    @RequestMapping(value="/users")
    public ModelAndView userlist(HttpServletRequest request) {
        // Handle a new guest (if any):
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String admin = request.getParameter("admin");
        Boolean isAdmin = false;
        if (admin != null) isAdmin = true;
        if (name != null)
        	userDao.persist(new User(name, email, password, isAdmin));
 
        // Prepare the result view (users.jsp):
        return new ModelAndView("users.jsp", "userDao", userDao);
    }
    @RequestMapping(value="/dropuser")
    public ModelAndView dropped (HttpServletRequest request) {
        
    	BasicConfigurator.configure();
    	String email = request.getParameter("email");
        if (email!= null){
        	userDao.delete(email);
        }
        // Prepare the result view (users.jsp):
        return new ModelAndView("users.jsp", "userDao", userDao);
    }

    @RequestMapping("/user")
    // @RequestParam binds HTTP request parameters to method arguments in the controller
    // takes the 'id' request parameter from the URL, and maps it to the countryId parameter of the method
    public ModelAndView goToBook(@RequestParam(value="email", required=true) String email) {
    	return new ModelAndView("user.jsp", "userDao", userDao);
    }

}