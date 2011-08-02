package edu.harvard.rssreader

class RssChannel {

	String title
	String description
	String link
	String source
	Long ttl
	Date pubDate
	Date lastPolled
	String isPublic
	String rawXml
	Integer pollStatus
	
    static constraints = {
		title(blank: false)
		description(blank: false)
		link(blank: false)
		source(blank: false)
		ttl(nullable: true)
		pubDate(nullable: true)
		lastPolled(nullable: true)
		isPublic(nullable: true)
		rawXml(nullable: true)
		pollStatus(nullable: true)
    }
	
	static mapping = {
		version false
		columns {
			id column: 'channel_id'
		}
		id generator: 'sequence', params:[sequence:'channel_id_sq']
	}
	
}
