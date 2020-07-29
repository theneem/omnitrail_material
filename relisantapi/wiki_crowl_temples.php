<?php
header("Access-Control-Allow-Origin: *");

	include('config.php');
	include('sharedfunction.php');

	$relegionid = $_REQUEST['rid'];


 //getwikisimple("Swaminarayan_Mandir,_Gadhada",$relegionid );

 // Girnar_Jain_temples

 echo("======================>"."<br>");
	//getwiki('Palitana','info');


	$query = "SELECT 
    `wikilinkid`, `type`, `ReligionID`, `wikilink`
    FROM `wikilinks` WHERE `processed` = 0 and  type = 'temple' and  `ReligionID`  = ".$relegionid;

$conn=mysqli_connect("localhost","scienceclub_sclub","S=Pj,i5nNf~@","scienceclub_relisant");
    if (mysqli_connect_errno())
        {
            echo "Failed to connect to MySQL: " . mysqli_connect_error();
        }


	$data=array(); 
	$q=mysqli_query($conn, $query); 
echo "hey";
	while ($row=mysqli_fetch_object($q)){
			$data[]=$row; 
echo "printing";
			//echo json_encode($row->wikilink);
			echo "<br>";
			//echo substr($row->wikilink, strrchr($row->wikilink, '//') + 1);   
			$wikiTitle =  substr(strrchr($row->wikilink, '/'), 1); 
			//echo $wikiTitle ; 
			//echo "<br>";

			getwikisimple($wikiTitle,$relegionid,$row->wikilinkid );
 

	}

	//echo json_encode($data); 
	echo mysqli_error($con); 


?>