<?php
header("Access-Control-Allow-Origin: *");

    include('config.php');

	$parentID = $_REQUEST['pid'];
	$parentType = $_REQUEST['ptype'];


	if($parentType == "temple")
	{

	$query = "SELECT `FeatureID`, `FeatureName`, `FeatureDetails`, `FeatureType`, `FeatureParentID`, `FeatureImg`, temple.TempleName as TempleName
    FROM `Feature`
    INNER JOIN temple
        on temple.TempleID = FeatureParentID
        WHERE FeatureType = 'temple' and FeatureParentID=".$parentID;

}
elseif($parentType == 'saint')	
{

	$query = "SELECT `FeatureID`, `FeatureName`, `FeatureDetails`, `FeatureType`, `FeatureParentID`, `FeatureImg`, Saint.SaintName as SaintName
    FROM `Feature`
    INNER JOIN Saint
        on Saint.SaintID = FeatureParentID
        WHERE FeatureType = 'saint' and FeatureParentID= ".$parentID;
}



	$data=array(); 
	$q=mysqli_query($conn, $query); 

	while ($row=mysqli_fetch_object($q)){
    		$data[]=$row; 
	}

	echo json_encode($data); 
	echo mysqli_error($con); 


?>