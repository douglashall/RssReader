<%@ page import="edu.harvard.rssreader.RssChannel" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <script type="text/javascript" src="https://www.google.com/jsapi?key=ABQIAAAAI0NvLcZ6AIGrsXWrV_Jg-BR-ItZtfys1-SV2ohcoJiTTIODY5BTzpB-20zoANEkxvOSGQhczVckEIA"></script>
        <script type="text/javascript">
    		google.load("feeds", "1");
        	
			function initialize() {
				var channels = ${channelList};
				
				var feedControl = new google.feeds.FeedControl();
				$(channels).each(function(){
					feedControl.addFeed(this.source, this.title);
				});

				feedControl.setNumEntries(${readerTopic.displayNumberOfItems});
				feedControl.draw(document.getElementById('rss-reader-content-${pageContentId}'));
			}
			
    		google.setOnLoadCallback(initialize);
    	</script>
    </head>
    <body>
    	<g:set var="styles"></g:set>
    	<g:if test="${readerTopic.displayTitle != '1'}">
    		<g:set var="styles" value="${styles} hide-title"/>
    	</g:if>
    	<g:if test="${readerTopic.displayDate != '1'}">
    		<g:set var="styles" value="${styles} hide-date"/>
    	</g:if>
    	<g:if test="${readerTopic.displayDescription != '1'}">
    		<g:set var="styles" value="${styles} hide-description"/>
    	</g:if>
    	<div class="rss-reader-content">
    		<div class="${styles}">
    			<div id="rss-reader-content-${pageContentId}"></div>
    		</div>
    	</div>
    </body>
</html>
