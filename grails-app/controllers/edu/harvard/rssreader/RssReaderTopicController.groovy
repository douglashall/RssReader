package edu.harvard.rssreader

import grails.converters.*

import org.codehaus.groovy.grails.web.json.*

class RssReaderTopicController {
	
	def update = {
		def readerId = RssReaderUtils.parseReaderIdFromIsitesParams(params)
		def rssReaderTopic = new RssReaderTopic(new JSONObject(params.rssReaderTopic))
		rssReaderTopic.id = readerId
		rssReaderTopic.save()
		
		withFormat {
			html {render rssReaderTopic as JSON}
			json {render rssReaderTopic as JSON}
			xml {render rssReaderTopic as XML}
		}
	}
	
}
