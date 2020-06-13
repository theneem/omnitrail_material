<?php
header("Access-Control-Allow-Origin: *");

    include('config.php');


	$saintid = $_REQUEST['sid'];


	$query = "SELECT  Saint.SaintID, Saint.SaintName, Religion.ReligionName, 
    PSaint.SaintName as ParentSaintName ,
    Saint.ParentSaintID, Saint.SectID, RSect.ReligionName as SectName, 
    Saint.Samudai, 
    Saint.SaintDesc, 
    Saint.SaintStory, 
    Saint.BirthDate, 
    Saint.SaintDate, 
    Saint.DeathDate, 
    Saint.SaintIMG ,
    Saint.CurrentAddress,
    Saint.CurrentCity,
    Saint.wiki_link, 
    Saint.ChiefFollower, 
    Saint.ChiefFollowerContact,
     City.city_name AS 'city_name', 
    State.state_name AS 'state_name',
	Country.country_name AS 'country_name'
    
    From Saint
    left outer join Saint as PSaint
    	on PSaint.SaintID = Saint.ParentSaintID
	inner join `Religion`
	on Religion.ReligionID = Saint.ReligionID
	left outer join (select ReligionID, ReligionName from Religion ) as RSect 
	on RSect.ReligionID = Saint.SectID 
     left outer  JOIN City
    	on City.CityID = Saint.CurrentCity
    left outer JOIN State
    	ON State.state_code = City.state_code
    left outer JOIN Country 
		ON State.country_code = Country.country_code
	 where Saint.SaintID = ".$saintid;


	$data=array(); 
	$q=mysqli_query($conn, $query); 

	while ($row=mysqli_fetch_object($q)){
    		$data[]=$row; 
	}

	echo json_encode($data); 
	echo mysqli_error($con); 


?>