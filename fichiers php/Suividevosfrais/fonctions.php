<?php
    function connexionPDO(){
		
		$bd = 'gsb_frais';
		$login = 'userGsb';
		$mdp = 'secret'; 
		/*
		$bd = 'dbname=wh1l2sdy_gsb_frais';
		$login = 'wh1l2sdy_grunam';
		$mdp = 'grunam1979';
		*/
		/*
        $login="root";
        $mdp="";
        $bd="gsb_frais";
		*/
        $serveur="localhost";
		
        try {
            $conn = new PDO("mysql:host=$serveur;dbname=$bd", $login, $mdp);
            return $conn;
        } catch (PDOException $e) {
            print "Erreur de connexion PDO ";
            die();
        }
    }
	
	
	
	 public function estUnVisiteur($idVisiteur)
    {
        $boolReturn = false;
        $requetePrepare = PdoGsb::$monPdo->prepare(
            'SELECT * FROM visiteur '
            . 'WHERE visiteur.id = :unVisiteur'
        );
        $requetePrepare->bindParam(':unVisiteur', $idVisiteur, PDO::PARAM_STR);
        $requetePrepare->execute();
        if ($requetePrepare->fetch()) {
            $boolReturn = true;
        }
        return $boolReturn;
    }

	
?>
