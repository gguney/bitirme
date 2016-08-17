<!--Designed Developed By Gokhan Guney-->
<!--st081101028@etu.edu.tr-->
<?php
$info = str_replace(substr($_SERVER["REQUEST_URI"], 0, strpos($_SERVER["REQUEST_URI"], '?')) . '?', '', $_SERVER["REQUEST_URI"]);

			$info  = str_replace( '%20', ' ' ,$info);
			$info  = str_replace( '&amp;', '&' ,$info);

			

$fields = substr($info, 0, strpos($info, '*'));
if ($fields == "")
{
	$fields ="null";
}
$field0 ="";
$field1 ="";
$field2 ="";
$field3 ="";
$field4 ="";


$field0 = substr ($fields,0,strpos($fields,'{}'));
$fields = substr ($fields,strpos($fields,'{}')+2);


$field1 = substr ($fields,0,strpos($fields,'{}'));
$fields = substr ($fields,strpos($fields,'{}')+2);


$field2 = substr ($fields,0,strpos($fields,'{}'));
$fields = substr ($fields,strpos($fields,'{}')+2);


$field3 = substr ($fields,0,strpos($fields,'{}'));
$fields = substr ($fields,strpos($fields,'{}')+2);


$field4 = substr ($fields,0);



$info = substr($info,strpos($info, '*')+1);

$startPY = substr($info, 0,strpos($info, '+'));
$info = substr($info,strpos($info, '+')+1);


$endPY = substr($info,0,strpos($info,'*'));
$order = substr($info,strpos($info, '*')+1);
$order = substr($order ,0,strpos($order , '*'));
$orderType = substr($info,strpos($info, '*')+1);
$orderType = substr($orderType,strpos($orderType, '*')+1);
if ($endPY == 0)
{
	$endPY = 2013;	
}
if ($endPY > 2013)
{
	$endPY = 2013;	
}

if ($startPY== 0)
{
	$startPY = 1900;	
}
if ($startPY > $endPY)
{
		$startPY = 1900;
			$endPY = 2013;	
}

//echo $field0."<br>";
//echo $field1."<br>";
//echo $field2."<br>";
//echo $field3."<br>";
//echo $field4."<br>";
//echo $startPY."<br>";
//echo $endPY."<br>";
//echo $order."<br>";
//echo $orderType."<br>";


?>