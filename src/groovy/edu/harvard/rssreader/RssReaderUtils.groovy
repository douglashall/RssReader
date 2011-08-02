package edu.harvard.rssreader

import javax.servlet.http.HttpServletRequest;

class RssReaderUtils {

	static long parseReaderIdFromIsitesParams(Map params) {
		long id = -1;
		String context = params.context;
		if (context != null && context.trim().length() > 0) {
			String[] items = context.split("=");
			if (items.length == 2) {
				try {
					id = Long.parseLong(items[1]);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return id;
	}
	
}
