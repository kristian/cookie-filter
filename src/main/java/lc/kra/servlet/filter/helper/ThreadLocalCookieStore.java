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

package lc.kra.servlet.filter.helper;

import java.net.CookieManager;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.util.List;

public class ThreadLocalCookieStore implements CookieStore {
	private final static ThreadLocal<CookieStore> stores = new ThreadLocal<CookieStore>() {
		@Override protected synchronized CookieStore initialValue() { 
			return (new CookieManager()).getCookieStore(); //InMemoryCookieStore 
		}
	};
	
	@Override public void add(URI uri, HttpCookie cookie) { getStore().add(uri,cookie); }
	@Override public List<HttpCookie> get(URI uri) { return getStore().get(uri); }
	@Override public List<HttpCookie> getCookies() { return getStore().getCookies(); }
	@Override public List<URI> getURIs() { return getStore().getURIs(); }
	@Override public boolean remove(URI uri, HttpCookie cookie) { return getStore().remove(uri,cookie); }
	@Override public boolean removeAll() { return getStore().removeAll(); }
	@Override public int hashCode() { return getStore().hashCode(); }
	
	protected CookieStore getStore() { return stores.get(); }
	public void purgeStore() { stores.remove(); }
}