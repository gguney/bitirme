<!--Designed Developed By Gokhan Guney-->
<!--st081101028@etu.edu.tr-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Expert Author Search</title>
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

<INPUT name="Button2"  TYPE="button" onclick="location.href='experts.php';" VALUE="Back" class="formButton">
</div>
<?php 
include "navigatorMenu4.php";
?>
<div class="scrollbar" id="real" style="background-image:url(images/a.png);background-repeat:no-repeat">



<div id="inputDiv" >




<table cellspacing="4px">
<tr>
<td>
 <label class="formLabel">Subject Name</label>
</td>
<td>

	<input name="query" autocomplete="off"  class='formInput' type='txt' id='search_input' onKeyUp='javascript:getSearchSuggestion(event, this.value,"subject");' style="width:500px;"/> 
    	<input name="limit" autocomplete="off"  class='formInput' type='txt' id='search_input' value="50" style="display:none;"/> 

</td>
<td>

<button class="formButton" onclick="searchs()"> Search </button>

<script>

function searchs()
{

		var field = document.getElementById('search_input').value;

		var offset = 0;
		var limit = 50;
		var order ="Total_Count";
		var orderType = "DESC"
		window.location.href="expertAuthor2.php?"+field+"*"+offset+"+"+limit+"*"+order+"*"+orderType;

}



</script>

</td>
</tr>
<tr>
<td>
</td>
    <td height="100px">

    
    <span class="dropdown">
            	<ul id='suggestbox' class="suggestbox" style="width:500px">Please Write Something to See Suggestions</ul>

    </span>

</td>
</tr>
</table>



</div>

</div>

</div>


</body>
</html>