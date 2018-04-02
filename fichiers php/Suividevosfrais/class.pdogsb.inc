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
	
	
	/**
     * Teste si un visiteur possède une fiche de frais
     * pour le mois et l'id passés en paramètres
     *
     * @param String $idVisiteur id du visiteur
     * @param String $mois       mois sous la forme aaaamm
     *
     * @return Boolean vrai ou faux
     *
     * @assert ('a131', '202501') == true
     * @assert ('a131', '201612') == false
     * @assert ('ZOZO', '202501') == true
     * @assert ('ZOZO', '201612') == true
     * @assert ('a131', 'mon année est mon mois') == true
     */
    public function estPremierFraisMois($idVisiteur, $mois)
    {
        $boolReturn = false;
        $requetePrepare = PdoGsb::$monPdo->prepare(
            'SELECT fichefrais.mois FROM fichefrais '
            . 'WHERE fichefrais.mois = :unMois '
            . 'AND fichefrais.idvisiteur = :unIdVisiteur'
        );
        $requetePrepare->bindParam(':unMois', $mois, PDO::PARAM_STR);
        $requetePrepare->bindParam(':unIdVisiteur', $idVisiteur, PDO::PARAM_STR);
        $requetePrepare->execute();
        if (!$requetePrepare->fetch()) {
            $boolReturn = true;
        }
        return $boolReturn;
    }
	
	
	
	 /**
     * Cloture les fiches de frais du mois précédent.
     * Passe le champ idEtat à CL et met la date de modif à aujourd'hui.
     *
     * @param String $mois mois sous la forme aaaamm
     *
     * @return null
     */
    public function cloturerFichesFrais($mois)
    {
        $requetePrepare = PdoGSB::$monPdo->prepare(
            'UPDATE ficheFrais '
            . "SET idetat = 'CL', datemodif = now() "
            . "WHERE idetat = 'CR'"
            . 'AND fichefrais.mois <= :unMois'
        );
        $requetePrepare->bindParam(':unMois', $mois, PDO::PARAM_STR);
        $requetePrepare->execute();
    }
	
	
	 /**
     * Crée une nouvelle fiche de frais et les lignes de frais au forfait
     * pour un visiteur et un mois donnés
     *
     * Récupère le dernier mois en cours de traitement, met à 'CL' son champs
     * idEtat, crée une nouvelle fiche de frais avec un idEtat à 'CR' et crée
     * les lignes de frais forfait de quantités nulles
     *
     * @param String $idVisiteur id du visiteur
     * @param String $mois       mois sous la forme aaaamm
     *
     * @return null
     */
    public function creeNouvellesLignesFrais($idVisiteur, $mois)
    {
        $dernierMois = $this->dernierMoisSaisi($idVisiteur);
        $laDerniereFiche = $this->getLesInfosFicheFrais($idVisiteur, $dernierMois);
        if ($mois > $dernierMois && $mois >= Utils::getMois(date('d/m/Y'))) {
            $idEtat = 'CR';
        } else {
            $idEtat = 'CL';
        }
        if ($laDerniereFiche['idEtat'] == 'CR' &&  $mois > $dernierMois) {
            $this->majEtatFicheFrais($idVisiteur, $dernierMois, 'CL');
        }
        $requetePrepare = PdoGsb::$monPdo->prepare(
            'INSERT INTO fichefrais (idvisiteur,mois,nbjustificatifs,'
            . 'montantvalide,datemodif,idetat) '
            . "VALUES (:unIdVisiteur,:unMois,0,0,now(),:unEtat)"
        );
        $requetePrepare->bindParam(':unIdVisiteur', $idVisiteur, PDO::PARAM_STR);
        $requetePrepare->bindParam(':unMois', $mois, PDO::PARAM_STR);
        $requetePrepare->bindParam(':unEtat', $idEtat, PDO::PARAM_STR);
        $requetePrepare->execute();
        $lesIdFrais = $this->getLesIdFrais();
        foreach ($lesIdFrais as $unIdFrais) {
            $requetePrepare = PdoGsb::$monPdo->prepare(
                'INSERT INTO lignefraisforfait (idvisiteur,mois,'
                . 'idfraisforfait,quantite) '
                . 'VALUES(:unIdVisiteur, :unMois, :idFrais, 0)'
            );
            $requetePrepare->bindParam(':unIdVisiteur', $idVisiteur, PDO::PARAM_STR);
            $requetePrepare->bindParam(':unMois', $mois, PDO::PARAM_STR);
            $requetePrepare->bindParam(
                ':idFrais',
                $unIdFrais['idfrais'],
                PDO::PARAM_STR
            );
            $requetePrepare->execute();
        }
    }
	
	 /**
     * Retourne le dernier mois saisi
     * d'une fiche de frais pour un visiteur donné
     *
     * @param String $idVisiteur id du visiteur
     *
     * @return String le mois sous la forme aaaamm
     *
     * @asset ('f21') == '201710'
     * @asset ('a131') != '201710'
     * @asset ('ZOZO') == NULL
     */
    public function dernierMoisSaisi($idVisiteur)
    {
        $requetePrepare = PdoGsb::$monPdo->prepare(
            'SELECT MAX(mois) as dernierMois '
            . 'FROM fichefrais '
            . 'WHERE fichefrais.idvisiteur = :unIdVisiteur'
        );
        $requetePrepare->bindParam(':unIdVisiteur', $idVisiteur, PDO::PARAM_STR);
        $requetePrepare->execute();
        $laLigne = $requetePrepare->fetch();
        $dernierMois = $laLigne['dernierMois'];
        return $dernierMois;
    }
	
	/**
     * Retourne tous les id des frais forfaitaires
     *
     * @return Array un tableau associatif
     *
     * @assert () == array(array('idfrais'=>'ETP', 0=>'ETP'), array('idfrais'=>'KM', 0=>'KM'), array('idfrais'=>'NUI', 0=>'NUI'), array('idfrais'=>'REP', 0=>'REP'))
     * @assert () != NULL
     */
    public function getLesIdFrais()
    {
        $requetePrepare = PdoGsb::$monPdo->prepare(
            'SELECT fraisforfait.id as idfrais '
            . 'FROM fraisforfait ORDER BY fraisforfait.id'
        );
        $requetePrepare->execute();
        return $requetePrepare->fetchAll();
    }
	
	/**
     * Retourne sous forme d'un tableau associatif toutes les lignes de frais
     * au forfait concernées par les deux paramètres
     *
     * @param String $idVisiteur id du visiteur
     * @param String $mois       mois sous la forme aaaamm
     *
     * @return Array l'id, le libelle et la quantité sous la forme d'un tableau
     *               associatif
     *
     * @assert ('a131', '201609') == array(0=>array('idfrais'=>'ETP', 'libelle'=>'Forfait Etape', 'quantite'=>'11',  0=>'ETP', 1=>'Forfait Etape', 2=>'11'), 1=>array('idfrais'=>'KM', 'libelle'=>'Frais Kilométrique', 'quantite'=>'847', 0=>'KM', 1=>'Frais Kilométrique', 2=>'847'), 2=>array('idfrais'=>'NUI', 'libelle'=>'Nuitée Hôtel', 'quantite'=>'13', 0=>'NUI', 1=>'Nuitée Hôtel',  2=>'13'), 3=>array('idfrais'=>'REP', 'libelle'=>'Repas Restaurant', 'quantite'=>'12', 0=>'REP', 1=>'Repas Restaurant', 2=>'12'))
     * @assert ('zozo66464', '201609') == array()
     * @assert ('a131', '300040') == array()
     */
    public function getLesFraisForfait($idVisiteur, $mois)
    {
        $requetePrepare = PdoGSB::$monPdo->prepare(
            'SELECT fraisforfait.id as idfrais, '
            . 'fraisforfait.libelle as libelle, '
            . 'lignefraisforfait.quantite as quantite '
            . 'FROM lignefraisforfait '
            . 'INNER JOIN fraisforfait '
            . 'ON fraisforfait.id = lignefraisforfait.idfraisforfait '
            . 'WHERE lignefraisforfait.idvisiteur = :unIdVisiteur '
            . 'AND lignefraisforfait.mois = :unMois '
            . 'ORDER BY lignefraisforfait.idfraisforfait'
        );
        $requetePrepare->bindParam(':unIdVisiteur', $idVisiteur, PDO::PARAM_STR);
        $requetePrepare->bindParam(':unMois', $mois, PDO::PARAM_STR);
        $requetePrepare->execute();
        return $requetePrepare->fetchAll();
    }
	
	
	/**
     * Met à jour la table ligneFraisForfait pour un visiteur,
     * un mois et les frais donnés
     *
     * @param String $idVisiteur id du visiteur
     * @param String $mois       mois sous la forme aaaamm
     * @param Array  $lesFrais   tableau associatif de clé idFrais et
     *                           de valeur la quantité pour ce frais
     *
     * @return null
     */
    public function majFraisForfait($idVisiteur, $mois, $lesFrais)
    {
        $lesCles = array_keys($lesFrais);
        foreach ($lesCles as $unIdFrais) {
            $qte = $lesFrais[$unIdFrais];
            $requetePrepare = PdoGSB::$monPdo->prepare(
                'UPDATE lignefraisforfait '
                . 'SET lignefraisforfait.quantite = :uneQte '
                . 'WHERE lignefraisforfait.idvisiteur = :unIdVisiteur '
                . 'AND lignefraisforfait.mois = :unMois '
                . 'AND lignefraisforfait.idfraisforfait = :idFrais'
            );
            $requetePrepare->bindParam(':uneQte', $qte, PDO::PARAM_INT);
            $requetePrepare->bindParam(':unIdVisiteur', $idVisiteur, PDO::PARAM_STR);
            $requetePrepare->bindParam(':unMois', $mois, PDO::PARAM_STR);
            $requetePrepare->bindParam(':idFrais', $unIdFrais, PDO::PARAM_STR);
            $requetePrepare->execute();
        }
    }

	/**
     * Crée un nouveau frais hors forfait pour un visiteur et un mois donnés
     * à partir des informations fournies en paramètres
     *
     * @param String $idVisiteur id du visiteur
     * @param String $mois       mois sous la forme aaaamm
     * @param String $libelle    libellé du frais
     * @param String $date       date du frais au format français jj/mm/aaaa
     * @param Float  $montant    montant du frais
     *
     * @return null
     */
    public function creeNouveauFraisHorsForfait(
        $idVisiteur,
        $mois,
        $libelle,
        $date,
        $montant
    ) {
        $libelleFiltre = Utils::filtrerChainePourBdd(Utils::filtrerChainePourVue($libelle));
        $requetePrepare = PdoGSB::$monPdo->prepare(
            'INSERT INTO lignefraishorsforfait '
            . 'VALUES (null, :unIdVisiteur,:unMois, :unLibelle, :uneDate,'
            . ':unMontant, null) '
        );
        $requetePrepare->bindParam(':unIdVisiteur', $idVisiteur, PDO::PARAM_STR);
        $requetePrepare->bindParam(':unMois', $mois, PDO::PARAM_STR);
        $requetePrepare->bindParam(':unLibelle', $libelleFiltre, PDO::PARAM_STR);
        $requetePrepare->bindParam(':uneDate', $date, PDO::PARAM_STR);
        $requetePrepare->bindParam(':unMontant', $montant, PDO::PARAM_STR);
        $requetePrepare->execute();
    }
	
	/**
     * Retourne les informations d'une fiche de frais d'un visiteur pour un
     * mois donné
     *
     * @param String $idVisiteur id du visiteur
     * @param String $mois       mois sous la forme aaaamm
     *
     * @return Array un tableau avec des champs de jointure entre une fiche de frais
     *         et la ligne d'état
     *
     * @assert ('a131', '201612') == Array ( 'idEtat' => 'RB', 'dateModif' => '2017-02-01', 'nbJustificatifs' => '0', 'montantValide' => '5545.91', 'libEtat' => 'Remboursée', 0 => 'RB', 1 => '2017-02-01', 2 => '0', 3 => '5545.91', 4 => 'Remboursée' )
     * @assert ('a131', '201612') != Array ( 'idEtat' => 'CL', 'dateModif' => '2017-02-01', 'nbJustificatifs' => '0', 'montantValide' => '5545.91', 'libEtat' => 'Remboursée', 0 => 'RB', 1 => '2017-02-01', 2 => '0', 3 => '5545.91', 4 => 'Remboursée' )
     * @assert ('a131', '201612') != Array ()
     * @assert ('a131', '201612') != NULL
     *
     */
    public function getLesInfosFicheFrais($idVisiteur, $mois)
    {
        $requetePrepare = PdoGSB::$monPdo->prepare(
            'SELECT fichefrais.idetat as idEtat, '
            . 'fichefrais.datemodif as dateModif, '
            . 'fichefrais.nbjustificatifs as nbJustificatifs, '
            . 'fichefrais.montantvalide as montantValide, '
            . 'etat.libelle as libEtat '
            . 'FROM fichefrais '
            . 'INNER JOIN etat ON fichefrais.idetat = etat.id '
            . 'WHERE fichefrais.idvisiteur = :unIdVisiteur '
            . 'AND fichefrais.mois = :unMois'
        );
        $requetePrepare->bindParam(':unIdVisiteur', $idVisiteur, PDO::PARAM_STR);
        $requetePrepare->bindParam(':unMois', $mois, PDO::PARAM_STR);
        $requetePrepare->execute();
        $laLigne = $requetePrepare->fetch();
        return $laLigne;
    }
	
	/**
     * Modifie l'état et la date de modification d'une fiche de frais.
     * Modifie le champ idEtat et met la date de modif à aujourd'hui.
     *
     * @param String  $idVisiteur      id du visiteur
     * @param String  $mois            mois sous la forme aaaamm
     * @param String  $etat            nouvel état de la fiche de frais
     * @param Boolean $nbJustificatif  paramètre optionnel nombre de justificatifs
     * 
     * @return null
     */
    public function majEtatFicheFrais($idVisiteur, $mois, $etat, $nbJustificatif = false)
    {
        $req = 'UPDATE fichefrais SET idetat = :unEtat, datemodif = now() ';
        if ($nbJustificatif) {
            $req .=  ', nbjustificatifs = :unNbJustificatifs ';
        }
        $req .= 'WHERE fichefrais.idvisiteur = :unIdVisiteur '
        . 'AND fichefrais.mois = :unMois';
        $requetePrepare = PdoGSB::$monPdo->prepare($req);
        $requetePrepare->bindParam(':unEtat', $etat, PDO::PARAM_STR);
        $requetePrepare->bindParam(':unIdVisiteur', $idVisiteur, PDO::PARAM_STR);
        $requetePrepare->bindParam(':unMois', $mois, PDO::PARAM_STR);
        if ($nbJustificatif) {
            $requetePrepare->bindParam(':unNbJustificatifs', $nbJustificatif, PDO::PARAM_STR);
        }
        $requetePrepare->execute();
    }
	
	/**
     * Efface les frais hors forfait de ligneFraisHorsForfait dont
     * le mois est passé en paramètre
     *
     * @param String $mois  mois sous la forme aaaamm
     *
     * @return null
     */
    public function effacerLesFraisHorsForfait($idVisiteur, $mois)
    {
        $requetePrepare = PdoGSB::$monPdo->prepare(
            'DELETE FROM lignefraishorsforfait '
            . 'WHERE lignefraishorsforfait.refuse IS NULL '
            . 'AND lignefraishorsforfait.idvisiteur = :unIdVisiteur '
            . 'AND lignefraishorsforfait.mois = :unMois' 
        );
        $requetePrepare->bindParam(':unMois', $mois, PDO::PARAM_STR);
        $requetePrepare->bindParam(':unIdVisiteur', $idVisiteur, PDO::PARAM_INT);
        $requetePrepare->execute();
    }
	
	
}	
