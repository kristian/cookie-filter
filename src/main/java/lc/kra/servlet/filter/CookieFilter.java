/**
 * Copyright (c) 2015 Kristian Kraljic
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package lc.kra.servlet.filter;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookiePolicy;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import lc.kra.servlet.filter.helper.ThreadLocalCookieStore;

@WebFilter
public class CookieFilter implements Filter {
	private static CookieHandler defaultCookieHandler;
	private static ThreadLocalCookieStore cookieStore;
	
	@Override public void init(FilterConfig config) throws ServletException {
		if(cookieStore==null) {
			defaultCookieHandler = CookieHandler.getDefault();
			CookieHandler.setDefault(new java.net.CookieManager(
				cookieStore=new ThreadLocalCookieStore(), CookiePolicy.ACCEPT_ALL));
		}
	}
	@Override public void destroy() {
		CookieHandler.setDefault(defaultCookieHandler);
		defaultCookieHandler = null; cookieStore = null;
	}
	
	@Override public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		chain.doFilter(request, response);
		cookieStore.purgeStore();
	}
}