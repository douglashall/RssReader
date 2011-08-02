package edu.harvard.rssreader

import grails.converters.JSON

class IsitestoolController {
	
	def create = {
		if (!IsitesPermissionUtils.hasAdminPermission(params.permissions)) {
			render 'not_authorized'
			return
		}
		
		def readerTopic = new RssReaderTopic(
			displayDate: '1',
			displayDescription: '1',
			displayNumberOfItems: 4,
			displayTitle: '1',
			displayChannelDescription: '0',
			displayImages: '0'
		)
		readerTopic.save()
		
		render 'readerid=' + readerTopic.id
	}
	
	def clone = {
		if (!IsitesPermissionUtils.hasAdminPermission(params.permissions)) {
			render 'not_authorized'
			return
		}
		
		def readerId = RssReaderUtils.parseReaderIdFromIsitesParams(params)
		def readerTopic = RssReaderTopic.get(readerId)
		
		def readerTopicClone = new RssReaderTopic(readerTopic.properties)
		readerTopicClone.save()
		
		RssReaderChannels.findAllByReaderId(readerId).each {
			def channel = RssChannel.get(it.channelId)
			
			def channelClone = new RssChannel(channel.properties)
			channelClone.save()
			
			def readerChannelsClone = new RssReaderChannels([
				readerId: readerTopicClone.id,
				channelId: channelClone.id,
				orderNum: it.orderNum
			])
			readerChannelsClone.save()
		}
		render 'readerid=' + readerTopicClone.id
	}
	
	def delete = {
		if (!IsitesPermissionUtils.hasAdminPermission(params.permissions)) {
			render 'not_authorized'
			return
		}
		
		def readerId = RssReaderUtils.parseReaderIdFromIsitesParams(params)
		def readerTopic = RssReaderTopic.get(readerId)
		RssReaderChannels.findAllByReaderId(readerId).each {
			def channel = RssChannel.get(it.channelId)
			it.delete()
			channel.delete()
		}
		readerTopic.delete()
		render 'readerid=' + readerId
	}
	
}
