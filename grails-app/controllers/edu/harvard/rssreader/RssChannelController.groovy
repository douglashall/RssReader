package edu.harvard.rssreader

import grails.converters.*
import grails.util.Environment
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder

import org.codehaus.groovy.grails.web.json.*

class RssChannelController {

    def list = {
		def readerId = RssReaderUtils.parseReaderIdFromIsitesParams(params)
		def readerTopic = RssReaderTopic.get(readerId)
		def channels = RssReaderChannels.findAllByReaderId(readerId).sort {a,b ->
			a.orderNum < b.orderNum ? -1 : 1
		} .collect {
			RssChannel.get(it.channelId)
		}
		
		
		withFormat {
			html {
				render(
					view: channels.size() > 0 ? 'list' : 'empty',
					model: [
						readerTopic:readerTopic, 
						channelList:channels as JSON, 
						topicId:params.topicId.replaceAll('\\.', '_'), 
						pageContentId:params.pageContentId.replaceAll('\\.', '_'),
						isAdmin: IsitesPermissionUtils.hasAdminPermission(params.permissions)
					]
				)
			}
			json {render channels as JSON}
			xml {render channels as XML}
		}
	}
	
	def edit = {
		def readerId = RssReaderUtils.parseReaderIdFromIsitesParams(params)
		def readerTopic = RssReaderTopic.get(readerId) as JSON
		def channels = RssReaderChannels.findAllByReaderId(readerId).sort {a,b ->
			a.orderNum < b.orderNum ? -1 : 1
		} .collect {
			RssChannel.get(it.channelId)
		}
		
		withFormat {
			html {[
				readerTopic: readerTopic.toString(), 
				channelList:[channels: channels] as JSON, 
				topicId:params.topicId.replaceAll('\\.', '_')
			]}
			json {render channels as JSON}
			xml {render channels as XML}
		}
	}
	
	def create = {
		def readerId = RssReaderUtils.parseReaderIdFromIsitesParams(params)
		def source = params.source
		
		def xmlFeed
		try {
			def http = new HTTPBuilder(source)
			if (Environment.current != Environment.DEVELOPMENT) http.setProxy('10.34.5.254', 8080, 'http')
			xmlFeed = http.get(contentType: ContentType.XML)
		} catch (Exception e) {
			e.printStackTrace()
			withFormat {
				html {render(contentType: "application/json"){[success:false]} as JSON}
				json {render(contentType: "application/json"){[success:false]} as JSON}
				xml {render(contentType: "application/json"){[success:false]} as XML}
			}
			return
		}
		
		def rssChannel = new RssChannel()
		rssChannel.title = xmlFeed.channel.title.text()
		rssChannel.description = xmlFeed.channel.description.text()
		rssChannel.link = xmlFeed.channel.link.text()
		rssChannel.source = source
		rssChannel.save()
		
		def channels = RssReaderChannels.findAllByReaderId(readerId).sort {a,b ->
			a.orderNum < b.orderNum ? -1 : 1
		}
		def size = channels.size()
		def order = size > 0 ? channels[channels.size() - 1].orderNum + 1 : 0
		
		def rssReaderChannels = new RssReaderChannels()
		rssReaderChannels.readerId = readerId
		rssReaderChannels.channelId = rssChannel.id
		rssReaderChannels.orderNum = order
		rssReaderChannels.save()
		
		withFormat {
			html {render(contentType: "application/json"){[success:true, data:rssChannel]} as JSON}
			json {render(contentType: "application/json"){[success:true, data:rssChannel]} as JSON}
			xml {render(contentType: "application/json"){[success:true, data:rssChannel]} as XML}
		}
	}
	
	def delete = {
		def readerId = RssReaderUtils.parseReaderIdFromIsitesParams(params)
		params.channelIds.split(',').each {
			def channel = RssChannel.get(Long.parseLong(it))
			RssReaderChannels.findByReaderIdAndChannelId(readerId, channel.id).delete(flush:true)
			channel.delete(flush:true)
		}
		
		// Order channels
		RssReaderChannels.findAllByReaderId(readerId).sort {a,b ->
			a.orderNum < b.orderNum ? -1 : 1
		}.eachWithIndex {item, index ->
			item.orderNum = index
			item.save()
		}
		
		withFormat {
			html {render(contentType: "application/json"){[success:true]} as JSON}
			json {render(contentType: "application/json"){[success:true]} as JSON}
			xml {render(contentType: "application/xml"){[success:true]} as XML}
		}
	}
	
	def order = {
		def readerId = RssReaderUtils.parseReaderIdFromIsitesParams(params)
		params.channelIds.split(',').eachWithIndex {item, index ->
			def readerChannel = RssReaderChannels.findByReaderIdAndChannelId(readerId, item)
			readerChannel.orderNum = index
			readerChannel.save()
		}
		
		withFormat {
			html {render(contentType: "application/json"){[success:true]} as JSON}
			json {render(contentType: "application/json"){[success:true]} as JSON}
			xml {render(contentType: "application/xml"){[success:true]} as XML}
		}
	}
	
}
