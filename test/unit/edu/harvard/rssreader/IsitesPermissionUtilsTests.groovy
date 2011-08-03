package edu.harvard.rssreader

import grails.test.*

class IsitesPermissionUtilsTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testHasAdminPermission() {
		def permissions = ""
		assert !IsitesPermissionUtils.hasAdminPermission(permissions)
		permissions = "7,8,9";
		assert !IsitesPermissionUtils.hasAdminPermission(permissions)
		permissions = "15"
		assert IsitesPermissionUtils.hasAdminPermission(permissions)
		permissions = "7,8,9,15,17,19"
		assert IsitesPermissionUtils.hasAdminPermission(permissions)
    }
}
