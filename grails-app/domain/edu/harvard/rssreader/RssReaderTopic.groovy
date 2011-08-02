package edu.harvard.rssreader

class RssReaderTopic {

	String displayDate
	String displayDescription
	int displayNumberOfItems
	String displayTitle
	String displayChannelDescription
	
    static constraints = {
		displayDate(nullable: true)
		displayDescription(nullable: true)
		displayNumberOfItems(nullable: true)
		displayTitle(nullable: true)
		displayChannelDescription(nullable: true)
    }
	
	static mapping = {
		version false
		columns {
			id column: 'reader_id'
		}
		id generator: 'sequence', params:[sequence:'reader_id_sq']
	}
	
}
