<%-- 
    Document   : printkundinfo
    Created on : 2008-jun-16, 19:42:28
    Author     : ulf
--%>
<%@ page import="se.saljex.sxserver.SXUtil" %>
<%@ page import="se.saljex.sxserver.web.*" %>

 <script type="text/javascript">
$(document).ready(function() {
/*	alert("start");
	$("input[name='bsok']").click(function() { alert("click"); loadsokres(); });
	$("#sokstr").keypress(function() { alert("keypress"); loadsokres(); });*/
});
function loadsokres() {
	sokstr = $("#sokstr").val();

/*	$("#divdoclist").load("?get=kundlista&sokstr=" + sokstr  );*/
	$("#divdoclist").load("?", {get: "kundlista", sokstr: sokstr } );
	}
	 
function loadsokres2(e) {
	loadsokres();
}

		 
 </script> 

<div id="divdocsok">

<h1>S�k kund</h1>
<form>
S�k: <input id="sokstr" type="text" name="sokstr" value="" onkeyup="loadsokres2(event);"/>
<input type="button" value="S�k" name="bsok" onclick="loadsokres();" />
</form>
</div>