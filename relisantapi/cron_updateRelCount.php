<?php
header("Access-Control-Allow-Origin: *");

    include('config.php');

	//$relegionid = $_REQUEST['rid'];



	$query = "UPDATE Religion SET noofgod = (  SELECT count(*) from God where God.ReligionID =  Religion.ReligionID )
    , nooftemple = (  SELECT count(*) from temple where temple.ReligionID =  Religion.ReligionID )
    , noofsaint =  (  SELECT count(*) from Saint where Saint.ReligionID =  Religion.ReligionID )";

	echo $query;

	if ($conn->query($query) === TRUE) {
        echo "<BR> Religion count updated successfully ";

    } else {
        echo "Error: " . $sql . "<br>" . $conn->error;
    }




	echo mysqli_error($con); 
    

?>