package edu.harvard.rssreader

class RssReaderChannels {

	long readerId
	long channelId
	int orderNum
	
    static constraints = {
    }
	
	static mapping = {
		version false
		columns {
			id column: 'reader_channels_id'
		}
		id generator: 'sequence', params:[sequence:'reader_channels_id_sq']
	}
	
}
