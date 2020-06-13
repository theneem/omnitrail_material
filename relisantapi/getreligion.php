<?php

header("Access-Control-Allow-Origin: *");

header("Access-Control-Allow-Headers: Content-Type, origin");

include('config.php');



$input = file_get_contents('php://input'); 
$data = json_decode($input, true); 



	$q=mysqli_query($conn, "SELECT * FROM `Religion` where 	ReligionID = ".$data['relid']	); 

	while ($row=mysqli_fetch_object($q)){
    		$data[]=$row; 
	}

	echo json_encode($data); 
	echo mysqli_error($con); 





?>