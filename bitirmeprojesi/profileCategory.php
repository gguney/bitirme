<!--Designed Developed By Gokhan Guney-->
<!--st081101028@etu.edu.tr-->
	<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>Category Profile</title>

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
	<INPUT name="Button2"  TYPE="button" onclick="location.href='searchCategory.php'" VALUE="Category Search" class="formButton2">

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
	$strSQL = "Select CID,CName,TCount 
	from bil496.categories as cat
	where cat.CID =".$field.";";
	
	$result = mysql_query($strSQL,$path) or die(mysql_error());
$row = mysql_fetch_array($result);




			$CID = $row[0];
			$CName = $row[1];
			$TCount = $row[2];
			$CLogo = "";

			echo "<div id='profileMenu' style='height:200px;'>";
			echo '<p style="float: left;">';
			echo '<img class="logo" src="images/Categories/no_logo.png" alt="logo" >';
			echo '</p>';
				  
			  
			echo "<p><label class='bigLabel'>".$CName."</label></p>";
			echo "<p><label class='midLabel'><b>Total Count: </b>".$TCount."</label></p>";


	?>
<?php

	  echo "</div>";

?>
	

	
<div class="scrollbar" id="real2" style="height:420px">

<?php

					$strSQL = "select DISTINCT  
					aac.AID as ArticleID,
					ti.TI as Title,
					 CName as Category ,
					 sub.SID as SubjectID,
					 sub.SName as Subject 
					  from bil496.aac  as aac,bil496.categories as cat,bil496.subjects as sub,bil496.aas as aas, bil496.titles as ti where aac.AID = aas.AID and aas.SID =sub.SID and aac.AID = ti.AID and cat.CID = aac.CID and cat.CID= ".$field." ORDER BY aac.".$order." ".$orderType." limit ".$offset.", ".$limit.";";	
							
				

							
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

						if ($i==3)
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
						if ($i==0)
							
						{
							echo "<td id='round' class='description3'><a class='link' href='profileArticle.php?".$row[$i]."''>".$row[$i]."</a></td>";			

						}
						else if ($i==3)
						{
						}
						
						else if($i==4)
						{
							echo "<td id='round' class ='description3'><a class='link' href='profileSubject.php?".$row[$i-1]."*0+50*RID*ASC'>".$row[$i]."</a></td>";

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


		window.location.href="profileCategory.php"+"?"+field+"*"+offset+"+"+limit+"*"+order+"*"+orderType;
		
}
function increase()
{
	var offset = parseInt( "<?php echo $offset; ?>");
	var limit = parseInt("<?php echo $limit; ?>");
	var field = "<?php echo $field; ?>";
	var order = "<?php echo $order; ?>";
	var orderType = "<?php echo $orderType; ?>";
	 offset = offset + limit;


		window.location.href="profileCategory.php"+"?"+field+"*"+offset+"+"+limit+"*"+order+"*"+orderType;
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