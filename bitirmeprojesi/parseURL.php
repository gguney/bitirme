<!--Designed Developed By Gokhan Guney-->
<!--st081101028@etu.edu.tr-->
<?php
$info = str_replace(substr($_SERVER["REQUEST_URI"], 0, strpos($_SERVER["REQUEST_URI"], '?')) . '?', '', $_SERVER["REQUEST_URI"]);

			$info  = str_replace( '%20', ' ' ,$info);
			$info  = str_replace( '&amp;', '&' ,$info);


$field = substr($info, 0, strpos($info, '*'));
if ($field == "")
{
	$field ="null";
}
$info = substr($info,strpos($info, '*')+1);

$offset = substr($info, 0,strpos($info, '+'));
$info = substr($info,strpos($info, '+')+1);


$limit = substr($info,0,strpos($info,'*'));
$order = substr($info,strpos($info, '*')+1);
$order = substr($order ,0,strpos($order , '*'));
$orderType = substr($info,strpos($info, '*')+1);
$orderType = substr($orderType,strpos($orderType, '*')+1);

//echo $field."<br>";
//echo $offset."<br>";
//echo $limit."<br>";

//echo $order."<br>";
//echo $orderType."<br>";

?>