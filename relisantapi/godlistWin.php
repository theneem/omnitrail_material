<?php
header("Access-Control-Allow-Origin: *");

    include('config.php');

$input = file_get_contents('php://input'); 
$data = json_decode($input, true); 

	$q=mysqli_query($conn, "SELECT * FROM `God` where ReligionID = ".$data['relid']); 

	while ($row=mysqli_fetch_object($q)){
    		$data[]=$row; 
	}

	//echo json_encode($data); 

echo ($data['relid']);

	
echo mysqli_error($con); 


?>