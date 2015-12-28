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
import java.io.PrintWriter;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookieStore;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lc.kra.servlet.filter.helper.ThreadLocalCookieStore;

/**
 * Servlet implementation class CookieServlet
 */
@WebServlet("/CookieServlet")
public class CookieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain");
		PrintWriter writer = response.getWriter();
		
		CookieHandler handler = CookieHandler.getDefault();
		if(!(handler instanceof CookieManager)) {
			writer.write("Default CookieHandler not an instance of CookieManager!");
			return;
		}
		
		CookieStore store = ((CookieManager)handler).getCookieStore();
		if(!(store instanceof ThreadLocalCookieStore)) {
			writer.write("CookieStore not an instance of ThreadLocalCookieStore!");
			return;
		}
		
		response.getWriter().println(((ThreadLocalCookieStore)store).hashCode());
	}
}
