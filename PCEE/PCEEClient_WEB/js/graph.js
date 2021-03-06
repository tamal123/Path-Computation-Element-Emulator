var net;
   
function  drawNetworkgraph(nodes, links){
	net=new tNetwork.Network();
	net.addSVG('mySVG');
	console.log('B.AAM');

	for (var i = 0, len = nodes.length; i < len; i++) {
		var res = nodes[i].split(" ");
		net.addNode(res[0],parseFloat(res[1]),parseFloat(res[2]),"images/node.png",{width:30,height:30},"",res[0]);
	}

	for (var i = 0, len = links.length; i < len; i++) {
		var res = links[i].split(" ");
		net.addLink(res[0],res[1],"","","","40 Gbps");
		net.addLink(res[1],res[0]);
	}


	net.globalTransformMatrix = 'matrix(1.0 0 0 1.0 0 0)';
	net.setLinkEventHandler('click', function(evt) {alert ('clicked link with id '+evt.target.id);});
	setTimeout('net.drawNetwork()',300);setTimeout('net.startEditMode()',500);
}

function  deleteNetworkgraph(){
	net=new tNetwork.Network();
	net.addSVG('mySVG');
	console.log('B.AAM');

	net.globalTransformMatrix = 'matrix(1.0 0 0 1.0 0 0)';
	net.setLinkEventHandler('click', function(evt) {alert ('clicked link with id '+evt.target.id);});
	setTimeout('net.drawNetwork()',300);setTimeout('net.startEditMode()',500);
}

function highlightPath(nodes){
	net.highlightAll(false)
	for(var i=0;i<nodes.length;i++){
			net.toggleHighlight('node', nodes[i]);
		}
	net.drawNetwork();
	for(var i=0;i<nodes.length;i++){
		net.toggleHighlight('link',nodes[i]+':'+nodes[i+1]);
	}
}