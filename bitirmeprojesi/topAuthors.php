<!--Designed Developed By Gokhan Guney-->
<!--st081101028@etu.edu.tr-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Top Authors</title>



<link href="css/style.css" rel="stylesheet" type="text/css" />

</head>
<body>
 
<div id="wrap">
<div class="header_home">


<a href="index.php" class="sitename">Article Search & Analysis Portal</a>

</div>

<div class="menu">
	<INPUT name="Button2"  TYPE="button" onclick="history.go(-1);" VALUE="Back" class="formButton">

<INPUT name="Button2"  TYPE="button" onclick="location.href='searchAuthor.php';" VALUE="Search Author" class="formButton2">
</div>
<?php 
include "navigatorMenu3.php";
?>
<div class="scrollbar" id="real">

<div id="inputDiv">


<?php
include "connect.php";
include "parseURL.php";
?>

<?php
	$strSQL = "select 
	au.AUID as AuthorID,
	au.AUName as Author_Name,
	au.AUAddressID as AUAddressID,
	ins.IName as Institution_Name,
	AUFullAddress as Author_Address,
	CiName as City,
	CoName as Country, 
	au.TCount as Total_Count 
from bil496.authors as au,bil496.institutions as ins,bil496.cities as ci,bil496.countries as co
where au.AUAddressID = ins.IID and ins.ICityID = ci.CiID and ins.ICountryID = co.CoID ORDER BY ".$order." ".$orderType." limit ".$offset.", ".$limit.";";					

							
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
					if ($i==0 || $i==2){}
					else
					{					$field_array[$i]=$field_info->name;
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
						echo "<td><span id='round' class='description'><a class='link' href='profileAuthor.php?".$row[$i-1]."''>".$row[$i]."</a></span></td>";			
		
						}
						else if ($i==0 ||$i==2)
						{}
	
						else if ($i==3)
						
						{
						echo "<td><span id='round' class='description'><a class='link' href='profileInstitution.php?".$row[$i-1]."''>".$row[$i]."</a></span></td>";			
		
						}
						else if ($i==5)
						
						{
						echo "<td><span id='round' class='description'><a class='link' href='searchCity2.php?".$row[$i]."*0+50*IName*ASC'>".$row[$i]."</a></span></td>";			
		
						}
						else if ($i==6)
						
						{
						echo "<td><span id='round' class='description'><a class='link' href='searchCountry2.php?".$row[$i]."*0+50*IName*ASC'>".$row[$i]."</a></span></td>";			
		
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


		window.location.href="topAuthors.php"+"?"+field+"*"+offset+"+"+limit+"*"+order+"*"+orderType;
		
}
function increase()
{
	var offset = parseInt( "<?php echo $offset; ?>");
	var limit = parseInt("<?php echo $limit; ?>");
	var field = "<?php echo $field; ?>";
	var order = "<?php echo $order; ?>";
	var orderType = "<?php echo $orderType; ?>";
	 offset = offset + limit;


		window.location.href="topAuthors.php"+"?"+field+"*"+offset+"+"+limit+"*"+order+"*"+orderType;
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