<?php
header("Access-Control-Allow-Origin: *");

    include('config.php');

	$templeID = $_REQUEST['tid'];


	$query = "SELECT  `TempleID`, `TempleName`, Religion.ReligionName, `TempleStory`, `TempleIMG`, 
	address, zip,lang,lat, 
    City.city_name AS 'city_name', 
    State.state_name AS 'state_name',
	Country.country_name AS 'country_name',
	`wiki_link`, `PrimaryDeity`, `GoverningBody`,
	`ContactPerson`, `ContactNumber`, `Creator`, `CompletionPerios`
From temple
	inner join `Religion`
		on Religion.ReligionID = temple.ReligionID
    INNER JOIN City
    	on temple.CityID = City.CityID
    INNER JOIN State
    	ON State.state_code = City.state_code
    INNER JOIN Country 
		ON State.country_code = Country.country_code
		where temple.TempleID = ".$templeID;


	$data=array(); 
	$q=mysqli_query($conn, $query); 

	while ($row=mysqli_fetch_object($q)){
    		$data[]=$row; 
	}

	echo json_encode($data); 
	echo mysqli_error($con); 


?>