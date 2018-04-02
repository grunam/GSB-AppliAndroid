<?php
require_once './class.utils.inc.php';
require_once './class.pdogsb.inc.php';

$pdo = PdoGsb::getPdoGsb();


// test si le paramètre "operation" est présent
if (isset($_REQUEST["operation"])) {
	
	
	if ($_REQUEST["operation"]=="connexion") {

		try {
			// création d'un curseur pour récupérer les profils
			print("connexion%");
			
			$lesdonnees = $_REQUEST["lesdonnees"] ;
			$donnee = json_decode($lesdonnees) ;
			$login = $donnee[2] ;
			$mdp = $donnee[3] ;
	
			$pdo = PdoGsb::getPdoGsb();
			//$cnx = connexionPDO();
			$response = array();
			
			$visiteur = $pdo->getInfosVisiteur($login, $mdp);
			if (!is_array($visiteur) || $visiteur["comptable"] == 1) {
				$response["success"] = "0";
				$response["status"] = utf8_encode ("username ou password incorrect(s)!");
				$response["username"] = "";
				$response["mdp"] = "";
				print(json_encode($response));
			} else {
				$response["success"] = "1";
				$response["status"] = utf8_encode ("authentification réussie !");
				$response["username"] = $visiteur["login"];
				$response["mdp"] = $visiteur["mdp"];

				print(json_encode($response));
			}
			
		// capture d'erreur d'accès à la base de données
		} catch (PDOException $e) {
			$response["success"] = "0";
			$response["status"] = "Erreur !" . $e->getMessage();
			$response["username"] = "";
			$response["mdp"] = "";
			print(json_encode($response));
			//print "Erreur !" . $e->getMessage();
			//die();
		}
	
	// enregistrement dans la table profil du profil reçu
	}elseif ($_REQUEST["operation"]=="enreg") {
			
		try {
			// création d'un curseur pour récupérer les profils
			print("enreg%");
			
			$lesdonnees = $_REQUEST["lesdonnees"] ;
			$lesdonnees = utf8_encode($lesdonnees);
			$donnee = json_decode($lesdonnees) ;
			$login = $donnee[2] ;
			$mdp = $donnee[3] ;
			$data = $donnee[4] ;
			
			$pdo = PdoGsb::getPdoGsb();
			$response = array();
			
			$visiteur = $pdo->getInfosVisiteur($login, $mdp);
			
			if (is_array($visiteur)) {
			
				////[mois, nbEtape, nbKm, nbNuitee, nbRepas, [[jourFraisHF, montantFraisHF, motifFraisHF], [jourFraisHF, montantFraisHF, motifFraisHF]]]
				
				$mois = $data[0];
				$nbEtape = $data[1];
				$nbKm = $data[2];
				$nbNuitee = $data[3];
				$nbRepas = $data[4];
				$fraisHF = $data[5];
				
				if($pdo->estPremierFraisMois($visiteur["id"], $mois)){
					
					$pdo->creeNouvellesLignesFrais($visiteur["id"], $mois);
					
				}	
				$lesFraisForfait = $pdo->getLesFraisForfait($visiteur["id"], $mois);
				$lesFrais = array(); 
				$i = 1;
				foreach ($lesFraisForfait as $unFraisForfait) {
					$idFrais = $unFraisForfait['idfrais'];
					$lesFrais[$idFrais] = $data[$i];
					$i++;
				}
				$pdo->majFraisForfait($visiteur["id"], $mois, $lesFrais);
				
				$pdo->effacerLesFraisHorsForfait($visiteur["id"], $mois);
				
				foreach ($fraisHF as $unFraisHF) {
					$date = strval($mois).strval($unFraisHF[0]);
					$dateEn = Utils::dateAnglaisNonCompact($date);
					$pdo->creeNouveauFraisHorsForfait($visiteur["id"], $mois, $unFraisHF[2], $dateEn, $unFraisHF[1]);
				}
					
				$response["success"] = "2";
				$response["status"] = utf8_encode("transfert réussi !");
				$response["username"] = $visiteur["login"];
				$response["mdp"] = $visiteur["mdp"];
				print(json_encode($response));
					
			}
				
		// capture d'erreur d'accès à la base de données
		} catch (PDOException $e) {
			$response["success"] = "0";
			$response["status"] = "erreur !" ;
			$response["username"] = "";
			$response["mdp"] = "";
			print(json_encode($response));
			//print "Erreur !" . $e->getMessage();
			//die();
		}		
		
	}

}

?>