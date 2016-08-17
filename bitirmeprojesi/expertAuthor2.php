	<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>Expert Authors</title>

	<script type="text/javascript">

	</script>

	<link href="css/style.css" rel="stylesheet" type="text/css" />
	</head>
	<body>


	<div id="wrap">
	<div class="header_home">


	<a href="index.php" class="sitename">Article Search & Analysis Portal</a>

	</div>
	<div class="menu">
	<INPUT name="Button2"  TYPE="button" onclick="history.go(-1);" VALUE="Back" class="formButton">
	<INPUT name="Button2"  TYPE="button" onclick="location.href='searchSubject.php'" VALUE="Subject Search" class="formButton2">

	</div>
<?php 
include "navigatorMenu4.php";
?>

	<div class="scrollbar" id="real">



	<div id="inputDiv">

	<?php
	include "connect.php";
	include "parseURL.php";
	?>
	
	<?php
	
	$strSQL = "Select SID,SName,TCount 
	from bil496.subjects as sub
	where sub.SName= '".$field."';";

	
	$result = mysql_query($strSQL,$path) or die(mysql_error());
$row = mysql_fetch_array($result);




			$SID = $row[0];
			$SName = $row[1];
			$TCount = $row[2];

			echo "<div id='profileMenu' style='height:200px;'>";
			echo '<p style="float: left;">';
			echo '<img class="logo" src="images/Subjects/no_logo.png" alt="logo" >';
			echo '</p>';
				  


			echo "<p><label class='bigLabel'><a class='link' href='profileSubject.php?".$SID."*0+50*RID*ASC'>".$SName."</a></label></p>";
			echo "<p><label class='midLabel'><b>Total Count: </b>".$TCount."</label></p>";


	?>
<?php

	  echo "</div>";

?>
	

	
<div class="scrollbar" id="real2" style="height:420px">

<?php


								
	$strSQL = "SELECT 
	SName as Subject_Name,
	au.AUID as AuthorID,
	AUName as Author_Name,
	COUNT(AUName) AS Total_Count
    FROM bil496.authors as au,bil496.subjects as sub,bil496.aas as aas
	where au.AUID = aas.AUID and aas.SID = sub.SID and sub.SName='".$field."' 
    GROUP BY AUName 
	Order by ".$order." ".$orderType." limit ".$offset.", ".$limit.";";
				

							
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
					if ($i == 1)
					{
						
					}
					else
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

							//echo "<td id='round' class='description2'><a class='link' href='profileArticle.php?".$row[$i]."''>".$row[$i]."</a></td>";			
			if($i == 1)
			{

			}
			else if($i == 2)
			{
					echo "<td id='round' class='description3'><a class='link' href='profileAuthor.php?".$row[$i-1]."''>".$row[$i]."</a></td>";			

			}
			else
			{
										echo "<td id='round' class='description3'>".$row[$i]."</td>";			

				}
			
		
							
				}
			echo "</tr>";
			$recordCount ++;

		
	 }
	echo "</table>";		
						

?>
</div>

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


		window.location.href="expertAuthor2.php"+"?"+field+"*"+offset+"+"+limit+"*"+order+"*"+orderType;
		
}
function increase()
{
	var offset = parseInt( "<?php echo $offset; ?>");
	var limit = parseInt("<?php echo $limit; ?>");
	var field = "<?php echo $field; ?>";
	var order = "<?php echo $order; ?>";
	var orderType = "<?php echo $orderType; ?>";
	 offset = offset + limit;


		window.location.href="expertAuthor2.php"+"?"+field+"*"+offset+"+"+limit+"*"+order+"*"+orderType;
}
</script>

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

	</div>





	</div>

	</div>

	</body>
	</html>