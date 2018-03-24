<?php
//include "fonctions.php";
require_once './class.pdogsb.inc.php';


/*
$pdo = PdoGsb::getPdoGsb();
$visiteur = $pdo->getInfosVisiteur("dandre", "oppg5");
print_r(json_encode($visiteur));
echo "<br>";
$response = array();

$response["success"] = "1";
$response["status"] = utf8_encode ("authentification r�ussie !");
$response["username"] = $visiteur["login"];
$response["mdp"] = $visiteur["mdp"];

print_r(json_encode($response));

*/

// test si le param�tre "operation" est pr�sent
if (isset($_REQUEST["operation"])) {
	
	
	if ($_REQUEST["operation"]=="connexion") {

		try {
			// cr�ation d'un curseur pour r�cup�rer les profils
			print("connexion%");
			
			$lesdonnees = $_REQUEST["lesdonnees"] ;
			$donnee = json_decode($lesdonnees) ;
			$login = $donnee[2] ;
			$mdp = $donnee[3] ;
	
			$pdo = PdoGsb::getPdoGsb();
			//$cnx = connexionPDO();
			$response = array();
			
			$visiteur = $pdo->getInfosVisiteur($login, $mdp);
			if (!is_array($visiteur)) {
				$response["success"] = "0";
				$response["status"] = utf8_encode ("username ou password incorrect(s)!");
				$response["username"] = "";
				$response["mdp"] = "";
				print(json_encode($response));
			} else {
				$response["success"] = "1";
				$response["status"] = utf8_encode ("authentification r�ussie !");
				$response["username"] = $visiteur["login"];
				$response["mdp"] = $visiteur["mdp"];
				print(json_encode($response));
			}
			/*
			$req = $cnx->prepare("select * from visiteurs where ");
			$req->execute();
			*/
			// s'il y a un profil, on r�cup�re le premier
			/*
			if ($ligne = $req->fetch(PDO::FETCH_ASSOC)){
				print(json_encode($ligne));
			}
			*/
			
		// capture d'erreur d'acc�s � la base de donn�es
		} catch (PDOException $e) {
			$response["success"] = "0";
			$response["status"] = $e->getMessage();
			$response["username"] = "";
			$response["mdp"] = "";
			print(json_encode($response));
			print "Erreur !" . $e->getMessage();
			die();
		}

	
	
	/*
	// demande de r�cup�ration du dernier profil enregistr�
	if ($_REQUEST["operation"]=="dernier") {

		try {
			// cr�ation d'un curseur pour r�cup�rer les profils
			print("dernier%");
			$cnx = connexionPDO();
			$req = $cnx->prepare("select * from profil order by datemesure desc");
			$req->execute();
		  
			// s'il y a un profil, on r�cup�re le premier
			if ($ligne = $req->fetch(PDO::FETCH_ASSOC)){
				print(json_encode($ligne));
			}

		// capture d'erreur d'acc�s � la base de donn�es
		} catch (PDOException $e) {
			print "Erreur !" . $e->getMessage();
			die();
		}
	*/
	// enregistrement dans la table profil du profil re�u
	}elseif ($_REQUEST["operation"]=="enreg") {
		
		// r�cup�ration des donn�es en post
		$lesdonnees = $_REQUEST["lesdonnees"] ;
		$donnee = json_decode($lesdonnees) ;
		$datemesure = $donnee[0] ;
		$poids = $donnee[1] ;
		$taille = $donnee[2] ;
		$age = $donnee[3] ;
		$sexe = $donnee[4] ;
		// insertion dans la base de donn�es
		try {
			print ("enreg%") ;
			$cnx = connexionPDO();
			$larequete = "insert into profil (datemesure, poids, taille, age, sexe)" ;
			$larequete .= " values (\"$datemesure\", $poids, $taille, $age, $sexe)" ;
			print ($larequete);
			$req = $cnx->prepare($larequete);
			$req->execute();
			
		// capture d'erreur d'acc�s � la base de donn�es
		} catch (PDOException $e) {
			print "Erreur !" . $e->getMessage();
			die();
		}

	}

}

?>