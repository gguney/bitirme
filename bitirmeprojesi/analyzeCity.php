<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>City Analyze</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<script type='text/javascript' src='js/jquery1.8.1.js'></script>
<script type='text/javascript' src='js/js.js'></script>


</head>
<body>


<div id="wrap">
<div class="header_home">


<a href="index.php" class="sitename">Article Search & Analysis Portal</a>


</div>
<div class="menu">

<INPUT name="Button2"  TYPE="button" onclick="location.href='analyze.php';" VALUE="Back" class="formButton">
</div>
<?php 
include "navigatorMenu2.php";
?>
<div class="scrollbar" id="real" style="background-image:url(images/a.png);background-repeat:no-repeat">

<div id="inputDiv" >
<table cellspacing="1px">
<tr>
<td>
 <label class="formLabel">#1 City</label>
</td>
<td>
	<input name="query" autocomplete="off"  class='formInput' type='txt' id='search_input0' onKeyUp='javascript:getAnalyzeSuggestion(event, this.value,"city",0);' style="width:500px;"/> 

</td>
</tr>
<tr>
<td>
</td>
    <td height="20px">
    <span class="dropdown">
            	<ul id='suggestbox0' class="suggestbox" style="width:500px">Please Write Something to See Suggestions</ul>

    </span>

</td>
</tr>
<tr>
<td>
 <label class="formLabel">#2 City</label>
</td>
<td>

	<input name="query" autocomplete="off"  class='formInput' type='txt' id='search_input1' onKeyUp='javascript:getAnalyzeSuggestion(event, this.value,"city",1);' style="width:500px;"/> 

</td>

</tr>
<tr>
<td>
</td>
    <td height="20px">
    <span class="dropdown">
            	<ul id='suggestbox1' class="suggestbox" style="width:500px">Please Write Something to See Suggestions</ul>
    </span>

</td>
</tr>
<tr>
<td>
 <label class="formLabel">#3 City</label>
</td>
<td>

	<input name="query" autocomplete="off"  class='formInput' type='txt' id='search_input2' onKeyUp='javascript:getAnalyzeSuggestion(event, this.value,"city",2);' style="width:500px;"/> 

</td>
</tr>
<tr>
<td>
</td>
    <td height="20px">
    <span class="dropdown">
            	<ul id='suggestbox2' class="suggestbox" style="width:500px">Please Write Something to See Suggestions</ul>

    </span>

</td>
</tr>
<tr>
<td>
 <label class="formLabel">#4 City</label>
</td>
<td>

	<input name="query" autocomplete="off"  class='formInput' type='txt' id='search_input3' onKeyUp='javascript:getAnalyzeSuggestion(event, this.value,"city",3);' style="width:500px;"/> 

</td>

</tr>
<tr>
<td>
</td>
    <td height="20px">
    <span class="dropdown">
            	<ul id='suggestbox3' class="suggestbox" style="width:500px">Please Write Something to See Suggestions</ul>
    </span>

</td>
</tr>
<tr>
<td>
 <label class="formLabel">#5 City</label>
</td>
<td>

	<input name="query" autocomplete="off"  class='formInput' type='txt' id='search_input4' onKeyUp='javascript:getAnalyzeSuggestion(event, this.value,"city",4);' style="width:500px;"/> 

</td>

</tr>
<tr>
<td>
</td>
    <td height="20px">
    <span class="dropdown">
            	<ul id='suggestbox4' class="suggestbox" style="width:500px">Please Write Something to See Suggestions</ul>
    </span>

</td>
</tr>
<tr><td colspan="2"> <label class="formLabel">Start Year: </label><input id ="inputStart" class="formInput" style="width:100px"><label class="formLabel">End Year: </label><input id ="inputEnd" class="formInput" style="width:100px"><button class="formButton" onclick="analyzeci()"> Search </button>

<script>

function analyzeci()
{

		var field0 = document.getElementById('search_input0').value;
		var field1 = document.getElementById('search_input1').value;
		var field2 = document.getElementById('search_input2').value;
		var field3 = document.getElementById('search_input3').value;
		var field4 = document.getElementById('search_input4').value;

		var start = document.getElementById('inputStart').value;
		var end = document.getElementById('inputEnd').value;
		var order ="PY";
		var orderType = "ASC"
		window.location.href="analyzeCity2.php?"+field0+"{}"+field1+"{}"+field2+"{}"+field3+"{}"+field4
		+"*"+start+"+"+end+"*"+order+"*"+orderType;

}



</script>
</td></tr>
</table>



</div>

</div>

</div>


</body>
</html>