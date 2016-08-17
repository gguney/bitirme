<!--Designed Developed By Gokhan Guney-->
<!--st081101028@etu.edu.tr-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Article Info</title>

<script type="text/javascript">

</script>

<link href="css/style.css" rel="stylesheet" type="text/css" />
</head>
<body>


<?php
include "connect.php";
?> 
<div id="wrap">
<div class="header_home">


<a href="index.php" class="sitename">Article Search & Analysis Portal</a>

</div>
<div class="menu">
<INPUT name="Button2"  TYPE="button" onclick="history.go(-1);" VALUE="Back" class="formButton">
<INPUT name="Button2"  TYPE="button" onclick="location.href='searchAuthor.php'" VALUE="Author Search" style="width:140px" class="formButton">

</div>
<?php 
include "navigatorMenu.php";
?>
<div class="scrollbar" id="real">



<div id="inputDiv">

<?php

	$id = str_replace(substr($_SERVER["REQUEST_URI"], 0, strpos($_SERVER["REQUEST_URI"], '?')) . '?', '', $_SERVER["REQUEST_URI"]);
 echo "<label class='formLabel'>#".$id." Article Info</label><br><br>";

					$strSQL = "select 
					AF as Authors,
					TI as Title,
					SO as Subject,
					 LA as Language,
					 AB as Abstract,
					  C1 as Departments,
					  CR as Cited_References,
					   PY as Publication_Year,
					   SC as Categories,
					   UT as DOI 
					   from bil496.real where ArticleID =".$id.";";
						
	
							
							$result = mysql_query($strSQL,$path);

			echo "<table border='thick' class='tableArticle'>";	

		while($row = mysql_fetch_array($result))//SQL Printing 
		{      
					for($i = 0; $i < mysql_num_fields($result); $i++) //Print for SQL row
					{		
						if ($i==0)
							$fieldName = "Authors";
							
						else if ($i==1)
							$fieldName = "Title";
						else if ($i==2)
							$fieldName = "Subject";
						else if ($i==3)
							$fieldName = "Language";
						else if ($i==4)
							$fieldName = "Abstract";
						else if ($i==5)
							$fieldName = "Departments";
						else if ($i==6)
							$fieldName = "Cited References";
						else if ($i==7)
							$fieldName = "Publication Year";
						else if ($i==8)
							$fieldName = "Categories";
						else if ($i==9)
							$fieldName = "DOI";
													
						
							echo "<tr><td><label class='midLabel' style='font-size:16px;color:#000;'><b>".$fieldName."</b></label></td><td style='text-align:center' ><label class= 'midItalicLabel' style='font-size:16px;color:#000;'>".$row[$i]."</label></td></tr>";
					}
			

			
		 }
			echo "</table>";	

?>



</div>




</div>

</div>

</body>
</html>