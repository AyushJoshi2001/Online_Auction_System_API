package filters;

import ninja.Context;
import ninja.Filter;
import ninja.FilterChain;
import ninja.Result;

public class CorsHandlerFilter implements Filter {
	@Override
	public Result filter(FilterChain filterChain, Context context) {
		Result result = filterChain.next(context);
		result.addHeader("Content-Type", "application/json").addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE").addHeader("Access-Control-Allow-Credentials", "true").addHeader("Access-Control-Allow-Origin", "http://localhost:4200").addHeader("Access-Control-Allow-Headers", "content-type");
		return result;
	}
}
