<?php
header("Access-Control-Allow-Origin: *");

    include('config.php');


	$relegionid = $_REQUEST['rid'];


	$query = "SELECT  `SaintID`, `SaintName`, Religion.ReligionName, `ParentSaintID`, `SectID`,RSect.ReligionName as SectName, `Samudai`, `SaintDesc`, `SaintStory`, `BirthDate`, `SaintDate`, `DeathDate`, `SaintIMG`  From Saint
	inner join `Religion`
	on Religion.ReligionID = Saint.ReligionID
	left outer join (select ReligionID, ReligionName from Religion ) as RSect 
	on RSect.ReligionID = SectID 
	 where Saint.ReligionID =".$relegionid;


	$data=array(); 
	$q=mysqli_query($conn, $query); 

	while ($row=mysqli_fetch_object($q)){
    		$data[]=$row; 
	}

	echo json_encode($data); 
	echo mysqli_error($con); 


?>