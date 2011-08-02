package edu.harvard.rssreader

import java.util.Map;

class IsitesPermissionUtils {

	public static String ADMIN = '15'
	
	public static boolean hasAdminPermission(String permissions) {
		return permissions && permissions.split(',').find {it == IsitesPermissionUtils.ADMIN}
	}
	
}
