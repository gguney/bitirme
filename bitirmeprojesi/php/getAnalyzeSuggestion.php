<?php
	include 'connect.php';
	$info = str_replace(substr($_SERVER["REQUEST_URI"], 0, strpos($_SERVER["REQUEST_URI"], '?')) . '?', '', $_SERVER["REQUEST_URI"]);
	$info = substr($info,0,strpos($info,'&'));

	$field= $_GET['search'];
	
	$termin = substr($field, 0, strpos($field, '*'));

	$no = substr($field,strpos($field, '*')+1);

	$termin = mysql_real_escape_string($termin);
	$columnName = "";
	if ($info == "author")
	{	
	$sql = "SELECT AUName FROM bil496.authors WHERE AUName LIKE '".$termin."%'";
	$columnName = "AUName";

	}
	else if ($info == "subject")
	{	
	$sql = "SELECT SName FROM bil496.subjects WHERE SName LIKE '".$termin."%'";
	$columnName = "SName";
	}
	else if ($info == "category")
	{	
	$sql = "SELECT CName FROM bil496.categories WHERE CName LIKE '".$termin."%'";
		$columnName = "CName";

	}	
	else if ($info == "institution")
	{	
	$sql = "SELECT IName FROM bil496.institutions WHERE IName LIKE '".$termin."%'";
		$columnName = "IName";

	}	
	else if ($info == "city")
	{	
	$sql = "SELECT CiName FROM bil496.cities WHERE CiName LIKE '".$termin."%'";
		$columnName = "CiName";

	}
	else if ($info == "country")
	{	
	$sql = "SELECT CoName FROM bil496.countries WHERE CoName LIKE '".$termin."%'";
		$columnName = "CoName";

	}
	
	
	$res = mysql_query($sql) or die(mysql_error());
	
	while($row = mysql_fetch_assoc($res)){
		echo "<label class='suggestboxLi' onclick='javascript:putSuggestionMulti(this,".$no.");'>".$row[$columnName]."</label></br>";
	}
?>