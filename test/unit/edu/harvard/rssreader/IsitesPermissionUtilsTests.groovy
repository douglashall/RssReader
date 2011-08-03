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
		assert !IsitesPermissionUtils.hasAdminPermission("")
		assert !IsitesPermissionUtils.hasAdminPermission("7,8,9")
		assert IsitesPermissionUtils.hasAdminPermission("15")
		assert IsitesPermissionUtils.hasAdminPermission("7,8,9,15,17,19")
    }
}
