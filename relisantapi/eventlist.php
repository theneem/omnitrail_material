<?php
header("Access-Control-Allow-Origin: *");

    include('config.php');

	$parentID = $_REQUEST['pid'];
	$parentType = $_REQUEST['ptype'];


	if($parentType == "temple")
	{
		
	$query = "SELECT  `EventID`, `EventName`, `EventDetails`, `EventType`, `EventParentID`,  `EentStartDate`, `EventEndDate`, 
	`EventImg`, temple.TempleName as TempleName
    FROM `Events` 
    INNER JOIN temple
    on temple.TempleID = EventParentID
    WHERE EventType = 'temple' and EventParentID  = ".$parentID;

	}
	elseif($parentType == 'saint')	
	{

		$query = "SELECT  `EventID`, `EventName`, `EventDetails`, `EventType`, `EventParentID`,  `EentStartDate`,
		`EventEndDate`, `EventImg`, Saint.SaintName as SaintName
    	FROM `Events` 
    	INNER JOIN Saint
    	on Saint.SaintID = EventParentID
		WHERE EventType = 'saint' and EventParentID =  ".$parentID;
	
		
	}

	$data=array(); 
	$q=mysqli_query($conn, $query); 

	while ($row=mysqli_fetch_object($q)){
    		$data[]=$row; 
	}

	echo json_encode($data); 
	echo mysqli_error($con); 


?>