<%@ page import="edu.harvard.rssreader.RssChannel" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <script>
        	$(document).ready(function(){
        		Ext.QuickTips.init();
        		var topicId = '${topicId}'.replace('_', '.');
        		var rssReaderTopic = JSON.parse('${readerTopic}')
            	var store = new Ext.data.JsonStore({
                	root: 'channels',
                	idProperty: 'id',
                    fields: [
                       {name: 'id'},
                       {name: 'title'},
                       {name: 'description'},
                       {name: 'link'},
                       {name: 'source'}
                    ]
                });
                store.loadData(${channelList});

                var sm = new Ext.grid.CheckboxSelectionModel({
                    listeners: {
                        'selectionchange': function(selModel){
                        	Ext.getCmp('remove-btn').setDisabled(!selModel.hasSelection());
                        }
                    }
                });
                
                var grid = new Ext.grid.GridPanel({
                    store: store,
                    sm: sm,
                    ddGroup: 'order-dd-group',
                    columns: [
                        sm,
                        {
                            header   : 'Title', 
                            sortable : false,
                            dataIndex: 'title'
                        },
                        {
                            id       : 'source',
                            header   : 'Source', 
                            sortable : false,
                            dataIndex: 'source'
                        }
                    ],
                    loadMask: {msg: 'Processing...'},
                    stripeRows: true,
                    autoExpandColumn: 'source',
                    enableHdMenu: false,
                    enableColumnHide: false,
                    enableColumnMove: false,
                    enableDragDrop: true,
                    height: 350,
                    width: 700,
                    title: 'RSS Channels',
                    header: false,
                    viewConfig: {
                        autoFill: true,
                        forceFit: true,
                        deferEmptyText: false,
                        emptyText: 'Click the Add Channel button to add a new channel.'
                    },
                    tbar: [{
                        id: 'add-btn',
                        text: 'Add Channel',
                        icon: '/js/isites/resources/images/fugue/_overlay/feed--plus.png',
                        handler: function(){
                        	Ext.MessageBox.prompt('Add Channel', 'Please enter the RSS source URL (e.g. http://news.google.com/?output=rss)', function(btn, text){
                            	if (btn == 'ok') {
	                                grid.loadMask.show();
	                            	$.ajax({
	        	                    	url: RssReader.constructUrl('rssChannel/create?format=json', topicId),
	        	                    	type: RssReader.requestType('POST'),
	        	                    	headers: {
	        	                        	'Accept': 'application/json'
	        	                        },
	        	                        dataType: 'json',
	        	                        data: {
	            	                        source: text
	        	                        },
	        	                    	success: function(data){
	        	                    		grid.loadMask.hide();
		        	                    	if (data.success) {
		        	                    		store.add([new store.recordType(data.data, data.data.id)]);
		        	                    	} else {
		        	                    		Ext.MessageBox.alert('Unable to add channel', 'The URL you entered does not appear to be a valid RSS feed.');
			        	                    }
	        	                        }
	        	                    });
                            	}
                            });
                        }
                    },{
                        id: 'remove-btn',
                        text: 'Remove Selected',
                        icon: '/js/isites/resources/images/fugue/_overlay/feed--minus.png',
                        disabled: true,
                        handler: function(){
                        	var selections = sm.getSelections();
                            if (selections.length == 0) {
                                return;
                            }
                            var channelIds = [];
                            $(selections).each(function(){
                                channelIds.push(this.get('id'));
                            });

                            grid.loadMask.show();
                        	$.ajax({
    	                    	url: RssReader.constructUrl('rssChannel/delete?format=json', topicId),
    	                    	type: RssReader.requestType('DELETE'),
    	                    	headers: {
    	                        	'Accept': 'application/json'
    	                        },
    	                        dataType: 'json',
    	                        data: {
        	                        channelIds: channelIds.toString()
    	                        },
    	                    	success: function(data){
        	                    	if (data.success) {
	        	                    	grid.getStore().remove(selections);
	        	                    	grid.loadMask.hide();
        	                    	}
    	                        }
    	                    });
                        }
                    },{
                        id: 'pref-btn',
                        text: 'Set Preferences',
                        icon: '/js/isites/resources/images/fugue/_overlay/feed--pencil.png',
                        handler: function(){
                            var w = new Ext.Window({
                                id: 'pref-win',
                                title: 'Set Preferences',
                                width: 400,
                                height: 300,
                                buttonAlign: 'center',
                                layout: 'fit',
                                border: false,
                                items: [{
                                    border: false,
	                                items: [{
		                                layout: 'form',
		                                bodyStyle: 'padding:8px',
		                                border: false,
		                                items: [{
			                                id: 'pref-display-title',
		                                    xtype: 'checkbox',
		                                    hideLabel: true,
		                                    boxLabel: 'Diplay channel title',
		                                    name: 'displayTitle',
		                                    checked: rssReaderTopic.displayTitle == '1'
		                                },{
		                                	id: 'pref-display-date',
		                                	xtype: 'checkbox',
		                                    hideLabel: true,
		                                	boxLabel: 'Display item author/date',
		                                    name: 'displayDate',
		                                    checked: rssReaderTopic.displayDate == '1'
		                                },{
		                                	id: 'pref-display-description',
		                                	xtype: 'checkbox',
		                                    hideLabel: true,
		                                	boxLabel: 'Display item description',
		                                    name: 'displayDescription',
		                                    checked: rssReaderTopic.displayDescription == '1'
		                                }]
	                                },{
		                                layout: 'form',
		                                labelAlign: 'left',
		                                labelWidth: 225,
		                                bodyStyle: 'padding:8px',
		                                border: false,
		                                items: [{
		                                	id: 'pref-display-items',
		                                    xtype: 'textfield',
		                                	fieldLabel: 'Limit the number of items per channel to',
		                                    name: 'displayNumberOfItems',
		                                    width: 30,
		                                    value: rssReaderTopic.displayNumberOfItems
		                                },{
		                                    xtype: 'label',
		                                	text: "Hint: enter 'all' to display all recent items"
		                                }]
	                                }]
                                }],
                                buttons: [{
                                    id: 'save-pref-btn',
                                    text: 'Save',
                                    handler: function(){
                                        rssReaderTopic.displayDate = Ext.getCmp('pref-display-date').getValue() ? '1' : '0',
                                        rssReaderTopic.displayDescription = Ext.getCmp('pref-display-description').getValue() ? '1' : '0',
                                        rssReaderTopic.displayNumberOfItems = Ext.getCmp('pref-display-items').getValue(),
                                        rssReaderTopic.displayTitle = Ext.getCmp('pref-display-title').getValue() ? '1' : '0'

                                    	$.ajax({
                	                    	url: RssReader.constructUrl('rssReaderTopic/update?format=json', topicId),
                	                    	type: RssReader.requestType('PUT'),
                	                    	headers: {
                	                        	'Accept': 'application/json'
                	                        },
                	                        dataType: 'json',
                	                        data: {
                    	                        rssReaderTopic: JSON.stringify(rssReaderTopic)
                	                        },
                	                    	success: function(data){
                    	                    	w.close();
                	                        }
                	                    });
                                    }
                                },{
                                    text: 'Cancel',
                                    handler: function(){
                                        Ext.getCmp('pref-win').close();
                                    }
                                }]
                            });
                            w.show();
                        }
                    }]
                });

                grid.render('channel-ct');

                var ddTarget = new Ext.dd.DropTarget(grid.getView().mainBody, {
                    ddGroup : 'order-dd-group',
                    notifyDrop : function(dd, e, data){
                        var rows = sm.getSelections();
                        var cindex = dd.getDragData(e).rowIndex;
                        if (sm.hasSelection()) {
                            for (i = 0; i &lt; rows.length; i++) {
                                store.remove(store.getById(rows[i].id));
                                store.insert(cindex,rows[i]);
                            }
                            sm.selectRecords(rows);

                            var channelIds = [];
                            store.each(function(r){
                                channelIds.push(r.get('id'));
                            });
                            
                            grid.loadMask.show();
                        	$.ajax({
    	                    	url: RssReader.constructUrl('rssChannel/order?format=json', topicId),
    	                    	type: RssReader.requestType('PUT'),
    	                    	headers: {
    	                        	'Accept': 'application/json'
    	                        },
    	                        dataType: 'json',
    	                        data: {
        	                        channelIds: channelIds.toString()
    	                        },
    	                    	success: function(data){
        	                    	if (data.success) {
	        	                    	grid.loadMask.hide();
        	                    	}
    	                        }
    	                    });
                        }  
                    }
                });
            });
        </script>
    </head>
    <body>
    	<p>Hint: Drag and drop selected rows to reorder the display of your channels.</p>
    	<div id="channel-ct"></div>
    </body>
</html>
