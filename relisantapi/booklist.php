<?php
header("Access-Control-Allow-Origin: *");

    include('config.php');


	$relegionid = $_REQUEST['rid'];


	$query = "SELECT `BookID`, Religion.ReligionName, `BookName`, `BookDesc`, `Author`, `BookImg`  FROM `Book` 
	inner join `Religion`
	on Religion.ReligionID = Book.ReligionID
	 where Book.ReligionID =".$relegionid;


	$data=array(); 
	$q=mysqli_query($conn, $query); 

	while ($row=mysqli_fetch_object($q)){
    		$data[]=$row; 
	}

	echo json_encode($data); 
	echo mysqli_error($con); 


?>