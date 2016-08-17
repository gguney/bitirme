<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Institution Analyze</title>
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
<INPUT name="Button2"  TYPE="button" onclick="location.href='analyzeInstitution.php';" VALUE="Back" class="formButton">
</div>

<div class="scrollbar" id="real" style="margin-top:0px;">
<div id="inputDiv">


<?php
include "connect.php";
include "parseMultiURL.php";
?>


<?php

			echo "<table><tr>";
			
			
			$strSQL = "select DISTINCT insta.PY from bil496.instanalysis as insta,bil496.institutions as ins where insta.IID = ins.IID and PY>=".$startPY." and ".$endPY.">=PY and IName='".$field0."' OR IName='".$field1."' OR IName='".$field2."' OR IName='".$field3."' OR IName='".$field4."' ORDER BY ".$order." ".$orderType.";";
				
				
					$result = mysql_query($strSQL,$path) or die(mysql_error());
					
					$headerDone = 0;
				$tmpStartPY= 0;
				$tmpStartPY2 = 0;
				$tmpEndPY= 0;
						
			echo "<td class='analyze'>";//td of big table
			echo "<table>";//small table 1
			
			
 while($row = mysql_fetch_array($result))//SQL Printing Year writing
	{     
		if ($headerDone == 0 )
		{

						echo "<tr class='analyze'><td><label class='midLabel2'>YEARS</label></a></td></tr>";		
						$tmpStartPY = $startPY;	
						$tmpStartPY2 = $startPY;	

						
		$headerDone =1;

		}

				for($i = 0; $i < mysql_num_fields($result); $i++) //Print for SQL row
				{		
						$tmpEndPY= $endPY;

				}
			
	 }
 
 while ($tmpStartPY2 <= $tmpEndPY)
 {
	 echo "<tr class='analyze'><td><label class='midLabel2'>".$tmpStartPY2."</label></td></tr>";
	 $tmpStartPY2++;
 }
			
				echo "<tr class='analyze'><td><label class='midLabel2'>TOTAL: </label></td></tr>";

			echo "</table>";//small table 1 ends
			echo "</td>";//big table td ends
			
			
			
			
			$tmpStartPY2 = $startPY;
for ($c = 0; $c <=4;$c++)
{

$fieldName = ""; 

$varName = 'field'.$c;
$fieldName .= $$varName; 
if ($fieldName !="")
{
echo "<td class='analyze'><table>";
	$total =0;
$lastYear = 0;
$strSQL = "select insta.IID,IName,insta.PY,insta.TCount from bil496.instanalysis as insta,bil496.institutions as ins where insta.IID = ins.IID and PY>=".$startPY." and ".$endPY.">=PY and IName='".$fieldName."' ORDER BY ".$order." ".$orderType.";";


	$result = mysql_query($strSQL,$path) or die(mysql_error());
 $tmpStartPY = $startPY;
	

	$headerDone = 0;

 while($row = mysql_fetch_array($result))//SQL Printing 
	{     
	
		if ($headerDone == 0 )
		{

						echo "<tr class='analyze'><td><a class='link' href='profileInstitution.php?".$row[0]."'><label class='midLabel2' title='".$row[1]."'>".substr($row[1],0,4)."</label></a></td></tr>";			
	
			$headerDone =1;

		}
		while ($row[2] > $tmpStartPY)
		{			//echo $row[2]." , ".$tmpStartPY;
						
								echo "<tr class='analyze'><td><label class='midLabel2'> - </label></td></tr>";
								$tmpStartPY ++;
								


		}	
		if ($row[2] == $tmpStartPY)//Author's year count printing
		{
			
				for($i = 0; $i < mysql_num_fields($result); $i++) //Print for SQL row
				{		
					if($i == 0 || $i == 1)
					{
					}
					if ($i == 3 )//Count
					{
						$total = $row[3] + $total;
						$count = $row[3] * 0.2;
					echo "<tr class='analyze'><td><input class='countBar' style='width:".$count."px' value='' readonly><label class='midLabel2'>".$row[3]."</label></td></tr>";
						$lastYear = $row[2];
	

					}

				}
												$tmpStartPY ++;

		}

	 }
	 
	while ($lastYear < $endPY)
	{										

				
					echo "<tr class='analyze'><td><label class='midLabel2'> - </label></td></tr>";
										$lastYear++;

					

	}

	$count = $total *1;
	echo "<tr class='analyze'><td><label class='midLabel2'>".$total."</label></td></tr>";
	
	
					
	echo "</table>";
	echo "</td>";


}

}		
			
			echo "</tr></table>";//big table ends

?>
</div>
</div>
</div>
</body>
</html>