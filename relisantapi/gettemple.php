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
	`ContactPerson`, `ContactNumber`, `Creator`, `CompletionPerios`,
	CASE
    WHEN fb_username is not  NULL THEN fb_username
    WHEN gmail_username is not  NULL  THEN gmail_username
    WHEN username is not  NULL THEN username
    ELSE 'Omnitrail'
  END as created_by
From temple
	inner join `Religion`
		on Religion.ReligionID = temple.ReligionID
    INNER JOIN City
    	on temple.CityID = City.CityID
    INNER JOIN State
    	ON State.state_code = City.state_code
    INNER JOIN Country 
		ON State.country_code = Country.country_code
	left outer JOIN users
    	on temple.created_by = users.id	
		where temple.TempleID = ".$templeID;


	$data=array(); 
	$q=mysqli_query($conn, $query); 

	while ($row=mysqli_fetch_object($q)){
    		$data[]=$row; 
	}

	echo json_encode($data); 
	echo mysqli_error($con); 


?>