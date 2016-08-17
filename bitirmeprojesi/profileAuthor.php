<!--Designed Developed By Gokhan Guney-->
<!--st081101028@etu.edu.tr-->
	<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>Author Profile</title>

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
	<INPUT name="Button2"  TYPE="button" onclick="location.href='searchAuthor.php'" VALUE="Author Search" class="formButton2">

	</div>

<?php 
include "navigatorMenu.php";
?>
	<div class="scrollbar" id="real">



	<div id="inputDiv">

	<?php
	include "connect.php";
	include "parseURL.php";
	$id = str_replace(substr($_SERVER["REQUEST_URI"], 0, strpos($_SERVER["REQUEST_URI"], '?')) . '?', '', $_SERVER["REQUEST_URI"]);

	?>
	
	<?php
	$strSQL = "Select
	 au.AUName,
	au.AUFullAddress,
	ins.IID,
	ins.IName,
	ci.CiID,
	ci.CiName,
	co.CoID,
	co.CoName,
	au.TCount,
	au.AUMore1,
	au.AUMore2
from bil496.authors as au,bil496.cities as ci,bil496.countries as co,bil496.institutions as ins
where  au.AUAddressID = ins.IID and ins.ICityID = ci.CiID and ins.ICountryID = co.CoID and au.AUID =".$id.";";
	$result = mysql_query($strSQL,$path) or die(mysql_error());
$row = mysql_fetch_array($result);




			$AUName = $row[0];
			$AUFullAddress = $row[1];
			$IID = $row[2];
			$IName = $row[3];
			$CiID = $row[4];
			$CiName = $row[5];
			$CoID = $row[6];
			$CoName = $row[7];
			$TCount = $row[8];
			$AULogo = $row[9];
			$AUSite = $row[10];

			echo "<div id='profileMenu'>";
				  if ($AULogo!=null)
				  {
					  echo '<p style="float: left;">';
					  echo '<img class="logo" src="images/Authors/'.$AULogo.'"  alt="logo">';
					  echo '</p>';
				  }
				  else
				  {
							  echo '<p style="float: left;">';
					  echo '<img class="logo" src="images/Authors/no_logo.jpg" alt="logo" >';
					  echo '</p>';
				  }
			  
							  echo "<p><label class='bigLabel'>".$AUName."</label></p>";
							  echo "<p><label class='midItalicLabel'><b>Full Address: </b>".$AUFullAddress."</label></p>";
							  echo "<p><label class='midItalicLabel'><b>Institution Name: </b><a class='link' href='profileInstitution.php?".$IID."'>".$IName."</a></label></p>";
							  echo "<p><label class='midItalicLabel'><b>Institution Address: </b><a class='link' href='searchCity2.php?".$CiName."*0+50*IName*ASC'>".$CiName."</a>,<a class='link' href='searchCountry2.php?".$CoName."*0+50*IName*ASC'>".$CoName."</a></label></p>";
							  echo "<p><label class='midLabel'><b>Total Count: </b>".$TCount."</label></p>";
							  echo "<p><a class='link' href='".$AUSite."' target='_blank'>".$AUSite."</a></p>";


	?>
<?php
$strSQL = "SELECT DISTINCT c.CID,c.CName FROM bil496.authors as au,bil496.categories as c,  bil496.aac as aac where c.CID = aac.CID and aac.AUID = au.AUID and au.AUID =".$id." ORDER BY CName ASC;";
	$result = mysql_query($strSQL,$path) or die(mysql_error());

echo "<p>";
echo "<p><label class='midLabel'><u>Author's Interest Areas</u></label></p>";
echo "</p>";
echo "<p>";

while ($row = mysql_fetch_array($result))
{
	echo "<a class='link' href='profileCategory.php?".$row[0]."*0+50*AID*ASC'>".$row[1]."</a>   ";

}
echo "</p>";

	  echo "</div>";

?>
	<?php


		
						$strSQL = "Select
						 aas.AID as ArticleID,
						 sub.SID,
						  sub.SName as Subject_Name,
						  	title.TI as Title,
						    title.PY as Publication_Year 
							from bil496.titles as title,bil496.aas as aas,bil496.authors as au,bil496.subjects as sub where au.AUID=aas.AUID and aas.SID = sub.SID and title.AID = aas.AID and au.AUID = ".$id." ORDER BY PY DESC;";
							
echo '<div id="rightHeader">';
echo "<label class='formLabel' >Author's Articles</label>";
echo "</div>";
															
		$result = mysql_query($strSQL,$path) or die(mysql_error());


			$columnsDone = 0;
	$recordCount = 0;


		while($row = mysql_fetch_array($result))//SQL Printing 
		{      
			if($columnsDone == 0) //Column Names Printing 
			{
		

	    echo '<div class="scrollbar" id="profileMenu2">';
		
				  echo "<table><tr>";
					  //print column name
					  $field_array= array();
					  for($i = 0; $i < mysql_num_fields($result); $i++) 
					  {
						  	$field_info = mysql_fetch_field($result);
							if ($i==1)
							{
							}

							  else
							  {
							  $field_array[$i]=$field_info->name;
							  echo "<td id='round' class='descriptionResizable'>{$field_info->name}</td>";
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
							//echo "<td><span id='round' class='description'><a class='link' href='profileArticle.php?".$row[$i]."''>".$row[$i]."</a></span></td>";			
							echo "<td id='round' class='description2'><a class='link' href='profileArticle.php?".$row[$i]."''>".$row[$i]."</a></td>";			

							}
							else if ($i==1 )
							{
							}
							else if ($i ==2 )
							{
								echo "<td id='round' class='description2'><a class='link' href='profileSubject.php?".$row[$i-1]."*0+50*RID*ASC''>".$row[$i]."</a></td>";			

							}
							else
							{
							echo "<td id='round' class='description2'>".$row[$i]."</td>";			
			
							}		
					}
				echo "</tr>";
				$recordCount ++;
				
			
		 }
		echo "</table>";	


echo '</div>';
		echo '<div id="midHeader">';
echo "<label class='formLabel' >Author's Co-Authors</label>";
echo "</div>";

	?>


	
<div class="scrollbar" id="real2">

	<?php

		$strSQL = str_replace(substr($_SERVER["REQUEST_URI"], 0, strpos($_SERVER["REQUEST_URI"], '?')) . '?', '', $_SERVER["REQUEST_URI"]);
		
						$strSQL = "select aucoau.CoAUID as Co_Author_ID,cau.AUName as Co_Author_Name,cau.AUFullAddress as Co_Author_Full_Address from bil496.aucoau as aucoau,bil496.authors as cau where aucoau.CoAUID = cau.AUID and aucoau.AUID=".$strSQL.";";
							
		
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
							}
							else if ($i==1)
							
							{
							echo "<td id='round' class='description3'><a class='link' href='profileAuthor.php?".$row[$i-1]."''>".$row[$i]."</a></span></td>";									
							}
			
							else if ($i==4)
							
							{
							echo "<td id='round' class='description3'><a class='link' href='profileInstitution.php?".$row[$i-1]."''>".$row[$i]."</a></span></td>";									
			
							}
							else
							{
							echo "<td id='round' class='description3'>".$row[$i]."</span></td>";			
			
							}		
					}
				echo "</tr>";
				$recordCount ++;

			
		 }
		echo "</table>";		


	?>
	</div>

	</div>





	</div>

	</div>

	</body>
	</html>