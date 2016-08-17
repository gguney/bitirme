<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Subject Search</title>



<link href="css/style.css" rel="stylesheet" type="text/css" />

</head>
<body>
 
<div id="wrap">
<div class="header_home">


<a href="index.php" class="sitename">Article Search & Analysis Portal</a>

</div>

<div class="menu">
	<INPUT name="Button2"  TYPE="button" onclick="history.go(-1);" VALUE="Back" class="formButton">

<INPUT name="Button2"  TYPE="button" onclick="location.href='searchSubject.php';" VALUE="Subject Search" class="formButton2">

</div>
<?php 
include "navigatorMenu.php";
?>
<div class="scrollbar" id="real">

<div id="inputDiv">


<?php
include "connect.php";
include "parseURL.php";
?>

<?php

	$strSQL = "Select SID,SName As Subject,TCount as Total_Count from bil496.subjects where SName LIKE '".$field."%' ORDER BY ".$order." ".$orderType." limit ".$offset.", ".$limit.";";					


							
							$result = mysql_query($strSQL,$path) or die(mysql_error());

		$columnsDone = 0;
$recordCount = 0;


	while($row = mysql_fetch_array($result))//SQL Printing 
	{      
		if($columnsDone == 0) //Column Names Printing 
		{
			echo "<table cellspacing='1' cellpadding='1' ><tr>";
				//print column name
				$field_array= array();
				for($i = 0; $i < mysql_num_fields($result); $i++) 
				{
					$field_info = mysql_fetch_field($result);
					
						 if ($i==0)
						{
						}else
						{
												$field_array[$i]=$field_info->name;
					echo "<td class='formLabel'>{$field_info->name}</td>";
						}

				}
				echo "</tr>";	
				$columnsDone = 1;
		}
		
		
			echo "<tr>";
				for($i = 0; $i < mysql_num_fields($result); $i++) //Print for SQL row
				{		
		
						if ($i==1)
						
						{
						echo "<td><span id='round' class='description'><a class='link' href='profileSubject.php?".$row[$i-1]."*0+50*RID*ASC'>".$row[$i]."</a></span></td>";			
		
						}
						else if ($i==0)
						{
						}
						else
						{
						echo "<td><span id='round' class='description'>".$row[$i]."</span></td>";			
		
						}		
				}
			echo "</tr>";
			$recordCount ++;

		
	 }
	echo "</table>";		
						

?>
</div>

</form>
<script>

function decrease()
{
	
	var offset = parseInt( "<?php echo $offset; ?>");
	var limit = parseInt("<?php echo $limit; ?>");
	var field = "<?php echo $field; ?>"

	var order = "<?php echo $order; ?>";
	var orderType = "<?php echo $orderType; ?>";
 	offset = offset - limit;

		if (offset < 0)
		{
			offset = 0;
		}


		window.location.href="searchSubject2.php"+"?"+field+"*"+offset+"+"+limit+"*"+order+"*"+orderType;
		
}
function increase()
{
	var offset = parseInt( "<?php echo $offset; ?>");
	var limit = parseInt("<?php echo $limit; ?>");
	var field = "<?php echo $field; ?>";
	var order = "<?php echo $order; ?>";
	var orderType = "<?php echo $orderType; ?>";
	 offset = offset + limit;


		window.location.href="searchSubject2.php"+"?"+field+"*"+offset+"+"+limit+"*"+order+"*"+orderType;
}
</script>
</div>
<?php 
if ($offset==0)
{
	
}
else
{
	echo '<button onclick="decrease()" class="formButton" >Previous</button>';
}
if ($recordCount<50)
{
	
}
else
{
	echo '<button onclick="increase()" class="formButton" >Next</button>';
}

?>


</div>


</body>
</html>

