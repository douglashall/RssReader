package edu.harvard.rssreader

import grails.test.*

class RssReaderUtilsTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testParseReaderIdFromIsitesParams() {
		assertEquals(-1, RssReaderUtils.parseReaderIdFromIsitesParams([:]))
		assertEquals(52079, RssReaderUtils.parseReaderIdFromIsitesParams([
			coreToolId:14744, 
			pageContentId:'icb.pagecontent910911', 
			siteType:12, 
			requestId:55, 
			keyword:'k36643', 
			userid:'e2e94c1b6ef71cb40caef0ace256f7af224a182ef136', 
			siteId:'icb.site36503', 
			context:'readerid=52079', 
			permissions:'7,8,9,10,11,12,13,14,15,17,16,18,21,20', 
			topicId:'icb.topic939166', 
			urlRoot:'localhost.harvard.edu', 
			remoteAddr:'127.0.0.1', 
			pageid:'icb.page431802', 
			action:'list', 
			controller:'rssChannel'
		]))
    }
}
