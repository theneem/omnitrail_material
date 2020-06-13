<?php



function getwikisimple($title, $ReligionID, $wikilinkid)
{
    echo "<br>" . "=============================================================================================================" . "<br>";

    echo "start";

    //INIITAILIASE REQUIRED VARIABLE

    $CityID = 'NULL';
    $lang =  'NULL';
    $lat = 'NULL';


    $safeurl = str_replace(' ', '%20', "$title");


    // GET SUMMARY API 
    $json_string = file_get_contents("https://en.wikipedia.org/api/rest_v1/page/summary/" . $safeurl);
    $obj = json_decode($json_string);

    /*
        `address` varchar(2000) NOT NULL,		==
        `CityID` int(100) NOT NULL,
        `country_code` char(2) NOT NULL,
        `zip` varchar(10) NOT NULL,
        
        `ContactPerson` varchar(2000) NOT NULL, //not available in wiki
        `ContactNumber` varchar(2000) NOT NULL, //not available in wiki
  */

    $TempleName = $obj->{'title'};
    $TempleStory = $obj->{'extract'};
    $TempleIMG = $obj->{'originalimage'}->{"source"};
    $wiki_link = $obj->{'content_urls'}->{"mobile"}->{"page"};

    //  GET COORDINATES 
    $json_string = file_get_contents("https://en.wikipedia.org//w/api.php?action=query&format=json&prop=coordinates&titles=" . $safeurl);
    $result = json_decode($json_string, true);

    foreach ($result["query"]["pages"] as $k => $v) {
       if($v["coordinates"][0]["lat"] != "")
            $lat = $v["coordinates"][0]["lat"];
        if($v["coordinates"][0]["lon"] != "")
            $lang = $v["coordinates"][0]["lon"];
        
            //get city , state, country and zip code. 
        $outAddress = getAddress($lang , $lat);

         //check if city is found in the table  

         $conn = mysqli_connect("localhost", "scienceclub_sclub", "S=Pj,i5nNf~@", "scienceclub_relisant");
                    if (mysqli_connect_errno()) {
                        echo "Failed to connect to MySQL: " . mysqli_connect_error();
                    }

        $city = trim($outAddress->City);
echo "City".$city;

         if ($city != "") {
            $cityquery = "SELECT `CityID`, `country_code`, `state_code`, `city_name` FROM `city` WHERE city_name like '%" . $city . "%'";

            $citydata = array();
            $cityq = mysqli_query($conn, $cityquery);

            $row_cnt = $cityq->num_rows;

            if ($row_cnt > 0) {
                echo "<BR> City record found :";
                $row = mysqli_fetch_object($cityq);

                $CityID = $row->CityID;
                echo "CitiID: " . $CityID;
            }
            else
            {
                //citi does not found in table we need to insert 
                    // search State in the table 
                    $state = trim($outAddress->State);
                    if($state != "")
                    {
                        $statequery = "SELECT `country_code`, `state_code`, `state_name` FROM `State` WHERE `state_name`like '%".$state."%'";
                        echo "<br>".$statequery;
                        $statedata = array();
                        $stateq = mysqli_query($conn, $statequery);

                        $row_cnt = $stateq->num_rows;

                        if ($row_cnt > 0) {
                            echo "<BR> state record found :";
                            $row = mysqli_fetch_object($stateq);

                            $SateID = $row->state_code;
                            $CountryID = $row->country_code;
                            echo "StateID: " . $SateID;
                            //sate found we need to insert city in to city table
                            $citiinsert = "INSERT INTO `City`(`country_code`, `state_code`, `city_name`) VALUES ('$CountryID','$SateID','$city')";
                            echo "<br>".$citiinsert;
                            if ($conn->query($citiinsert) === TRUE) {
                                $CityID =  $conn->insert_id;;
                                echo "<BR> City inserted successfully ".$CityID;
                                
                        
                            } else {
                                echo "Error: " . $sql . "<br>" . $conn->error;
                            }
                        }
                        else
                        {
                            //state not found we need to insert the state as well as city 
                            


                        }
                    }

            }
        }
        $conn->close();

        echo $outAddress->City.", ".$outAddress->State.", ".$outAddress->Country.", ".$outAddress->Zipcode;



    }
    //GET INFORBOX CONTENT

    $json_string = file_get_contents("http://en.wikipedia.org/w/api.php?action=query&prop=revisions&rvprop=content&format=json&rvsection=0&titles=" . $safeurl);
    $result = json_decode($json_string, true);



    foreach ($result["query"]["pages"] as $k => $v) {
        $infobox = $v["revisions"][0]["*"];
        //echo("<br>");
    }

    $iboxdataary = explode("}", $infobox);
    $GoverningBody = '';


    foreach ($iboxdataary as $ibox) {
        $iDetail = explode("|", $ibox);
        foreach ($iDetail as $iDVaue) {
            $str = $iDVaue;


            $str = str_replace("[", "", $str);
            $str = str_replace("]", "", $str);

            //  `PrimaryDeity` varchar(2000) NOT NULL,	== info.query.pages.xxxx.revisions.contentmodel => deity
            if (startsWith($str, " deity")) {
                $PrimaryDeity =  str_replace("deity = ", "", $str);
                //echo "==> PrimaryDeity :".$PrimaryDeity;

            }
            //  `GoverningBody` varchar(2000) NOT NULL,	== info.query.pages.xxxx.revisions.contentmodel => governing_body 
            if (startsWith($str, " governing_body")) {

                $GoverningBody =  str_replace("=", "", str_replace("governing_body", "", $str));

            }

            if (startsWith($str, " creator")) {
                $Creator = str_replace("creator = ", "", $str);
            }
            //  `CompletionPerios` varchar(2000) NOT NULL     == info.query.pages.xxxx.revisions.contentmodel => year_completed
            if (startsWith($str, " year_completed")) {
                $CompletionPerios = str_replace("year_completed =", "", $str);
            }


            if (startsWith($str, " native_name ")) {
                $native_name = str_replace("=", "", str_replace("native_name", "", $str));
            }

            if (startsWith($str, " location")) {
                if (strpos($str, ',') !== false) {
                    //echo 'More than one city';
                } else {
                    $conn = mysqli_connect("localhost", "scienceclub_sclub", "S=Pj,i5nNf~@", "scienceclub_relisant");
                    if (mysqli_connect_errno()) {
                        echo "Failed to connect to MySQL: " . mysqli_connect_error();
                    }
                    $str = trim(str_replace("=", "", str_replace("location", "", $str)));

                    //only one city found than search in the city table 
                    if ($str != "") {
                        $cityquery = "SELECT `CityID`, `country_code`, `state_code`, `city_name` FROM `city` WHERE city_name like '%" . $str . "%'";

                        $citydata = array();
                        $cityq = mysqli_query($conn, $cityquery);

                        $row_cnt = $cityq->num_rows;

                        if ($row_cnt > 0) {
                            echo "<BR> City record found :";
                            $row = mysqli_fetch_object($cityq);

                            $CityID = $row->CityID;
                            echo "CitiID: " . $CityID;
                        }
                    }
                    $conn->close();
                }
            }

            if (
                startsWith($str, " location")
                or startsWith($str, " native_name")
                or startsWith($str, " festival")

            ) {

                // echo "==> with |:" . $str;
                // echo ("<br>");
            }
        }
    }

    // insert into temple table


    $conn = mysqli_connect("localhost", "scienceclub_sclub", "S=Pj,i5nNf~@", "scienceclub_relisant");
    if (mysqli_connect_errno()) {
        echo "Failed to connect to MySQL: " . mysqli_connect_error();
    }

    $TempleStory = mysqli_real_escape_string($conn,$TempleStory);


    $strInsert = "  INSERT INTO `temple` ( `ReligionID`, `TempleName`, `TempleStory`, `TempleIMG`, `address`, `CityID`, `zip`, `lang`, `lat`, `wiki_link`, `PrimaryDeity`, `GoverningBody`, `ContactPerson`, `ContactNumber`, `Creator`, `CompletionPerios`, `native_name`) VALUES
( $ReligionID, '$TempleName', '$TempleStory', '$TempleIMG', '$address', $CityID, 
'$zip', $lang, $lat, '$wiki_link', '$PrimaryDeity', '$GoverningBody', 
'$ContactPerson', '$ContactNumber', '$Creator', '$CompletionPerios', '$native_name')";


    if ($conn->query($strInsert) === TRUE) {
        echo "<BR> Temple inserted successfully ";

        //
        $updateWikilink = "UPDATE wikilinks SET processed= TRUE 
        WHERE wikilinkid = $wikilinkid";
        $conn->query($updateWikilink); 


    } else {
        echo "Error: " . $sql . "<br>" . $conn->error;
    }



    echo "<br>.Insert:: " . $strInsert . "<br>";
}

function getwiki($titles, $props)
{

    /*
    get_info.php

    MediaWiki API Demos
    Demo of `Info` module: Send a GET request to display information about a page.

    MIT License
*/

    $endPoint = "https://en.wikipedia.org/w/api.php";
    $params = [
        "action" => "query",
        "format" => "json",
        "titles" => "Albert Einstein",
        "prop" => "info",
        "inprop" => "url|talkid"
    ];

    $url = $endPoint . "?" . http_build_query($params);

    echo ($url);
    $ch = curl_init($url);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    echo ("====> CH : ");
    echo ($ch);
    $output = curl_exec($ch);
    curl_close($ch);

    echo ("====>output");
    echo ($output);

    $result = json_decode($output, true);
    echo ("====>test");
    echo $result;

    foreach ($result["query"]["pages"] as $k => $v) {
        echo ($v["title"] . " has " . $v["length"] . " bytes." . "\n");
    }
}


// Function to check string starting 
// with given substring 
function startsWith($string, $startString)
{
    $len = strlen($startString);
    return (substr($string, 0, $len) === $startString);
}

function getAddress($lang, $lat)
{

    $outAddress = new GeoAddress();

    $GoogleTheNeemApi = "AIzaSyDRSI7cPxm5e82Bfrv-KP7YxVGAwxOtmic";
  
      // GET SUMMARY API 
     // $json_string = file_get_contents("https://maps.googleapis.com/maps/api/geocode/json?latlng=$lat,$lang&location_type=ROOFTOP&result_type=street_address&key=$GoogleTheNeemApi");
      $json_string = file_get_contents("https://maps.googleapis.com/maps/api/geocode/json?latlng=$lat,$lang&key=$GoogleTheNeemApi");

      $obj = json_decode($json_string,true);


      echo "<br>Google API";
      echo "<br>"."https://maps.googleapis.com/maps/api/geocode/json?latlng=$lat,$lang&location_type=ROOFTOP&result_type=street_address&key=$GoogleTheNeemApi";

      echo "<br>".$json_string;
      echo "<br".$GoogleTheNeemApi;

      echo $json_string; 
      
       // get the important data
       //$formatted_address = isset($obj['results'][0]['formatted_address']) ? $obj['results'][0]['formatted_address'] : "";
        
       //echo "<br> Address component : ".$obj['results'][0]['formatted_address'];


      //$obj->{'object'}->{'result'}->{'0'}->address_components};

      echo $obj->{'results'}->{0}->{'address_components'};

      foreach ($obj['results'][0]['address_components'] as $k => $v) {
          
        if( $v["types"][0] == 'locality')
          $outAddress->City = $v['long_name'];
        else if( $v["types"][0] == 'administrative_area_level_1')
          $outAddress->State = $v['long_name'];
        else if ( $v["types"][0] == 'country')
            $outAddress->Country = $v['long_name'];
        else if ( $v["types"][0] == 'postal_code')
            $outAddress->Zipcode = $v['long_name'];
        
        echo("<br>");
      }


    return $outAddress;

}

class GeoAddress
{
    public $City;
    public $State;
    public $Country;
    public $Zipcode;
}
