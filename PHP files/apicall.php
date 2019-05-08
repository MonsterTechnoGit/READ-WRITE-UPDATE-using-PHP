<?php

$servername = "fdb20.awardspace.net";
$username = "3044752_entc";
$password = "zxcvbnm@123";
$database = "3044752_entc";


//creating a new connection object using mysqli
$conn = new mysqli($servername, $username, $password, $database);

//if there is some error connecting to the database
//with die we will stop the further execution by displaying a message causing the error
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$response = array();

if (isset($_GET['apicall'])) {

    switch ($_GET['apicall']) {

        case 'signup':
            if (isTheseParametersAvailable(array('username', 'email', 'password', 'gender'))) {
                $username = $_POST['username'];
                $email = $_POST['email'];
                $password = md5($_POST['password']);
                $gender = $_POST['gender'];


                $stmt = $conn->prepare("SELECT id FROM usersnew WHERE username = ? OR email = ?");
                $stmt->bind_param("ss", $username, $email);
                $stmt->execute();
                $stmt->store_result();

                if ($stmt->num_rows > 0) {
                    $response['error'] = true;
                    $response['message'] = 'User already registered';
                    $stmt->close();
                } else {
                    $stmt = $conn->prepare("INSERT INTO usersnew (username, email, password, gender) VALUES (?, ?, ?, ?)");
                    $stmt->bind_param("ssss", $username, $email, $password, $gender);

                    if ($stmt->execute()) {
                        $stmt = $conn->prepare("SELECT id, id, username, email, gender FROM usersnew WHERE username = ?");
                        $stmt->bind_param("s", $username);
                        $stmt->execute();
                        $stmt->bind_result($userid, $id, $username, $email, $gender);
                        $stmt->fetch();

                        $user = array(
                            'id' => $id,
                            'username' => $username,
                            'email' => $email,
                            'gender' => $gender,
                        );

                        $stmt->close();

                        $response['error'] = false;
                        $response['message'] = 'User registered successfully';
                        $response['user'] = $user;
                    }
                }

            } else {
                $response['error'] = true;
                $response['message'] = 'required parameters are not available';
            }

			echo json_encode($response);
            break;

        case 'login':

            if (isTheseParametersAvailable(array('username', 'password'))) {

                $username = $_POST['username'];
                $password = md5($_POST['password']);

                $stmt = $conn->prepare("SELECT id, username, email, gender,fullname FROM usersnew WHERE username = ? AND password = ?");
                $stmt->bind_param("ss", $username, $password);

                $stmt->execute();

                $stmt->store_result();

                if ($stmt->num_rows > 0) {

                    $stmt->bind_result($id, $username, $email, $gender, $fullname);
                    $stmt->fetch();

                    $user = array(
                        'id' => $id,
                        'username' => $username,
                        'email' => $email,
                        'gender' => $gender,
                        'fullname' => $fullname
                    );

                    $response['error'] = false;
                    $response['message'] = 'Login successfull';
                    $response['user'] = $user;
                } else {
                    $response['error'] = false;
                    $response['message'] = 'Invalid username or password';
                }
            }
			echo json_encode($response);
            break;

        case 'update':
            if (isTheseParametersAvailable(array('email', 'phone', 'emphone', 'fullname'))) {
                $emailID = $_POST['email'];
                $fullname = $_POST['fullname'];
                $phone = $_POST['phone'];
                $emphone = $_POST['emphone'];

                $conn = new mysqli($servername, $username, $password, $database);


                // Check connection
                if ($conn->connect_error) {
                    die("Connection failed: " . $conn->connect_error);
                }

                $sql = "UPDATE usersnew SET fullname='$fullname',phone='$phone',emphone='$emphone' WHERE email='$emailID'";

                if ($conn->query($sql) === TRUE) {
                    $response['error'] = false;
                    $response['message'] = 'User Details Updated';
                } else {
                    $response['error'] = true;
                    $response['message'] = 'Something went wrong';
                }
            } else {
                $response['error'] = false;
                $response['message'] = 'required parameters are not available';
            }
			echo json_encode($response);
            break;

        case 'updateresult':

            $checking = $_POST['checking'];
            $emailID = $_POST['email'];

            if($checking=='true'){
                $conn = new mysqli($servername, $username, $password, $database);


                // Check connection
                if ($conn->connect_error) {
                    die("Connection failed: " . $conn->connect_error);
                }

                $sql = "UPDATE usersnew SET checking=1 WHERE email='$emailID'";

                if ($conn->query($sql) === TRUE) {
                    $response['error'] = false;
                    $response['message'] = 'User Details Updated';
                } else {
                    $response['error'] = true;
                    $response['message'] = 'Something went wrong';
                }
            }
			echo json_encode($response);

            break;

        case 'questions':
        $resultout = array();
        $conn = new mysqli($servername, $username, $password, $database);

        if ($conn->connect_error) {
            die("Connection failed: " . $conn->connect_error);
        }

        $stmt = $conn->prepare("SELECT id, question, answer_yes,answer_no, answer_one,answer_two,answer_three,answer_four FROM questions;");

        $stmt->execute();

        $stmt->bind_result($id,$question,$answer_yes,$answer_no,$answer_one,$answer_two,$answer_three,$answer_four);

        while ($stmt->fetch()) {
            $temp = array();
            $temp['id']=$id;
            $temp['question']=$question;
            $temp['answer_yes']=$answer_yes;
            $temp['answer_no']=$answer_no;
            $temp['answer_one']=$answer_one;
            $temp['answer_two']=$answer_two;
            $temp['answer_three']=$answer_three;
            $temp['answer_four'] = $answer_four;
            array_push($resultout, $temp);
        }

        echo json_encode($resultout);

        break;

        case 'userdetails':
            $resultout = array();
            $emailID = $_POST['email'];
            $conn = new mysqli($servername, $username, $password, $database);

            if ($conn->connect_error) {
                die("Connection failed: " . $conn->connect_error);
            }

            $stmt = $conn->prepare("SELECT id, username, email,gender,fullname,phone,emphone FROM usersnew WHERE email='$emailID';");

            $stmt->execute();

            $stmt->bind_result($id,$username,$email,$gender,$fullname,$phone,$emphone);

            while ($stmt->fetch()) {
                $temp = array();
                $temp['id']=$id;
                $temp['username']=$username;
                $temp['email']=$email;
                $temp['gender']=$gender;
                $temp['fullname']=$fullname;
                $temp['phone']=$phone;
                $temp['emphone']=$emphone;
                array_push($resultout, $temp);
            }

            echo json_encode($resultout);

            break;

        case 'checking':
            $resultout = array();
            $emailID = $_POST['email'];
            $conn = new mysqli($servername, $username, $password, $database);

            if ($conn->connect_error) {
                die("Connection failed: " . $conn->connect_error);
            }

            $stmt = $conn->prepare("SELECT checking FROM usersnew WHERE email='$emailID';");

            $stmt->execute();

            $stmt->bind_result($checking);

            while ($stmt->fetch()) {
                $temp = array();
                $temp['checking']=$checking;
                array_push($resultout, $temp);
            }

            echo json_encode($resultout);

            break;


        default:
            $response['error'] = true;
            $response['message'] = 'Invalid Operation Called';
    }

} else {
    $response['error'] = true;
    $response['message'] = 'Invalid API Call';
}


function isTheseParametersAvailable($params)
{

    foreach ($params as $param) {
        if (!isset($_POST[$param])) {
            return false;
        }
    }
    return true;
}