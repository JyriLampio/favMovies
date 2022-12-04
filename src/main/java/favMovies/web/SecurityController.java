package favMovies.web;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SecurityController {

	//Voit hakea tarvittaessa usernamen 
    @RequestMapping(value = "/username", method = RequestMethod.GET)
    @ResponseBody
    public String currentUserName(Principal principal) {
    	//String auth = SecurityContextHolder.getContext().getAuthentication().getName();
        return principal.getName();
    }

    public String getUserName() {
    	String auth = SecurityContextHolder.getContext().getAuthentication().getName();
        return auth;
    }
}
