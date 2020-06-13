<?php
header("Access-Control-Allow-Origin: *");

    include('config.php');

	$relegionid = $_REQUEST['rid'];


	$query = "SELECT  `TempleID`, `TempleName`, Religion.ReligionName, `TempleStory`, `TempleIMG`, 
	address, zip,lang,lat, PrimaryDeity,
    City.city_name AS 'city_name', 
    State.state_name AS 'state_name',
	Country.country_name AS 'country_name'
From temple
	inner join `Religion`
		on Religion.ReligionID = temple.ReligionID
    left outer JOIN City
    	on temple.CityID = City.CityID
    left outer JOIN State
    	ON State.state_code = City.state_code
    left outer JOIN Country 
		ON State.country_code = Country.country_code
		where temple.ReligionID = ".$relegionid;


	$data=array(); 
	$q=mysqli_query($conn, $query); 

	while ($row=mysqli_fetch_object($q)){
    		$data[]=$row; 
	}

	echo json_encode($data); 
	echo mysqli_error($con); 


?>