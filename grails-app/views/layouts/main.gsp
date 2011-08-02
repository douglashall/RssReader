<!DOCTYPE html>
<html>
    <head>
    	<script>
    		Ext.namespace('RssReader');
    		
	    	RssReader.constructUrl = function(url, topicId) {
				try {
					return Isites.constructUrl(url) + '&topicId=' + topicId;
				} catch(e) {
					return '/RssReader/' + url;
				}
			}
			
			RssReader.requestType = function(type) {
				try {
					if (Isites) {
						if (type == 'GET') {
							return type;
						} else {
							return 'POST';
						}
					}
				} catch(e) {
					return type;
				}
			}
		</script>
        <g:layoutHead />
    </head>
    <body>
    	<titlebar>
			<icon state="edit">
				<title>Edit</title>
				<image>edit_icon.jpg</image>
				<request>rssChannel/edit</request>
				<permission>15</permission>
			</icon>
		</titlebar>
        <g:layoutBody />
    </body>
</html>