package filters;

import com.google.inject.Inject;

import dao.UserDao;
import ninja.Context;
import ninja.Filter;
import ninja.FilterChain;
import ninja.Result;
import ninja.Results;

public class ValidateUserFilter implements Filter {
	@Inject
	UserDao userDao;
	
	@Override
	public Result filter(FilterChain filterChain, Context context) {
		String email = "";
		String password = "";
		if(context.getSession()==null || context.getSession().get("email")==null || context.getSession().get("password")==null) {
			return Results.forbidden().json().render("Unauthorized User...");
		}
		else {
			email = context.getSession().get("email");
			password = context.getSession().get("password");
			if(userDao.isUserAndPasswordValid(email, password)) {
				return filterChain.next(context);
			}
		}
		
		return Results.forbidden().json().render("Unauthorized User");
	}
	

}
