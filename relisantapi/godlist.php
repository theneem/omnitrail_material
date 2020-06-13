<?php
header("Access-Control-Allow-Origin: *");

    include('config.php');


	$relegionid = $_REQUEST['rid'];


	$query = "SELECT `GodID`, `ReligionID`, `GodName`, `ParentGoodID`, `GodImg`, `GodDesc`, `GodStory` FROM `God` 
	where ReligionID =".$relegionid;
	
	$data=array(); 
	$q=mysqli_query($conn, $query); 

	while ($row=mysqli_fetch_object($q)){
    		$data[]=$row; 
	}

	echo json_encode($data); 
	echo mysqli_error($con); 


?>