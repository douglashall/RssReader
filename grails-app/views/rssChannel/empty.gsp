<%@ page import="edu.harvard.rssreader.RssChannel" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
    </head>
    <body>
    	<g:if test="${isAdmin}">
	    	<i>
	    		<p>No RSS channel has been configured.</p>
	   			<ul>
	   				<li><a href="edit:rssChannel/edit">Please click here to create a new channel.</a></li>
	   			</ul>
	   		</i>
	   	</g:if>
    </body>
</html>
