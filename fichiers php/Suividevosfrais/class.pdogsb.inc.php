<?php
/**
 * Classe d'accès aux données.
 *
 * Utilise les services de la classe PDO
 * pour l'application GSB
 * Les attributs sont tous statiques,
 * les 4 premiers pour la connexion
 * $monPdo de type PDO
 * $monPdoGsb qui contiendra l'unique instance de la classe
 *
 * PHP Version 7
 *
 * @category  PPE
 * @package   GSB
 * @author    Cheri Bibi - Réseau CERTA <contact@reseaucerta.org>
 * @author    José GIL <jgil@ac-nice.fr>
 * @copyright 2017 Réseau CERTA
 * @license   Réseau CERTA
 * @version   Release: 1.0
 * @link      http://www.php.net/manual/fr/book.pdo.php PHP Data Objects sur php.net
 */

/*
 use PHPUnit\Framework\TestCase;

class PdoGsbTest extends TestCase
{
 */

class PdoGsb
{
    private static $serveur = 'mysql:host=localhost';
   
    private static $bdd = 'dbname=gsb_frais';
    private static $user = 'userGsb';
    private static $mdp = 'secret'; 
    /*
    private static $bdd = 'dbname=wh1l2sdy_gsb_frais';
    private static $user = 'wh1l2sdy_grunam';
    private static $mdp = 'grunam1979';
    */
    private static $monPdo;
    private static $monPdoGsb = null;

    /**
     * Constructeur public pour PHPUnit, crée l'instance de PDO qui sera sollicitée
     * pour toutes les méthodes de la classe
     */
    public function __construct()
    {
        
		
		try {
            //$conn = new PDO("mysql:host=$serveur;dbname=$bd", $login, $mdp);
            //return $conn;
			
			PdoGsb::$monPdo = new PDO(
            PdoGsb::$serveur . ';' . PdoGsb::$bdd,
            PdoGsb::$user,
            PdoGsb::$mdp
			);
			PdoGsb::$monPdo->query('SET CHARACTER SET utf8');
			
        } catch (PDOException $e) {
            print "Erreur de connexion PDO ";
            die();
        }
		
		
		
    }

    /**
     * Méthode destructeur appelée dès qu'il n'y a plus de référence sur un
     * objet donné, ou dans n'importe quel ordre pendant la séquence d'arrêt.
     */
    public function __destruct()
    {
        PdoGsb::$monPdo = null;
    }

    /**
     * Fonction statique qui crée l'unique instance de la classe
     * Appel : $instancePdoGsb = PdoGsb::getPdoGsb();
     *
     * @return PdoGsb instance de la classe PdoGsb
     */
    public static function getPdoGsb()
    {
        if (PdoGsb::$monPdoGsb == null) {
            PdoGsb::$monPdoGsb = new PdoGsb();
        }
        return PdoGsb::$monPdoGsb;
    }

    /**
     * Retourne les informations d'un visiteur
     *
     * @param String $login login du visiteur
     * @param String $mdp   mot de passe du visiteur
     *
     * @return Array l'id, le nom et le prénom sous la forme d'un tableau associatif
     * 
     * @assert ('dandre', 'oppg5') == array('id'=>'a17', 'nom'=>'Andre', 'prenom'=>'David', 'comptable'=>'0', 0=>'a17', 1=>'Andre', 2=>'David', 3=>'0')
     * @assert ('cbedos', 'gmhxd') != array('id'=>'a55', 'nom'=>'Bedos', 'prenom'=>'Christian', 'comptable'=>'0', 0=>'a55', 1=>'Bedos', 2=>'Christian', 3=>'1')
     * @assert ('cbedos', 'gmhxd') != array('id'=>'a55', 'nom'=>'Bedos', 'prenom'=>'Christian', 'comptable'=>'0', 0=>'a55', 1=>'Bedos', 2=>'ZOZO', 3=>'0')
     * @assert ('zeze', '99999') == false
     */
    public function getInfosVisiteur($login, $mdp)
    {
        $requetePrepare = PdoGsb::$monPdo->prepare(
            'SELECT * FROM visiteur '
            . 'WHERE visiteur.login = :unLogin AND visiteur.mdp = :unMdp'
        );
        $requetePrepare->bindParam(':unLogin', $login, PDO::PARAM_STR);
        $requetePrepare->bindParam(':unMdp', $mdp, PDO::PARAM_STR);
        $requetePrepare->execute();
        return $requetePrepare->fetch();
    }
    
    /**
     * Teste si un visiteur existe pour un id,
     * passé en paramètre
     *
     * @param String $idVisiteur id du visiteur
     *
     * @return Boolean vrai ou faux
     *
     * @assert ('a17') == true
     * @assert ('a55') == true
     * @assert (1212) == false
     * @assert ('COCO') == false
     */
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
}	
