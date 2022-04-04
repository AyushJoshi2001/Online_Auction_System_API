package filters;

import com.google.inject.Inject;

import dao.AdminDao;
import ninja.Context;
import ninja.Filter;
import ninja.FilterChain;
import ninja.Result;
import ninja.Results;

public class AdminFilter implements Filter {
	@Inject
	AdminDao adminDao;
	
	@Override
	public Result filter(FilterChain filterChain, Context context) {
		String email = context.getSession().get("email");
		if(adminDao.isValidAdmin(email)) {
			return filterChain.next(context);
		}
		return Results.forbidden().json().render("Unauthorized Admin Access");
	}
}
