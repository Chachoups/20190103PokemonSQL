package fr.duboc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Program {

	private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/aventure_pokemon?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private static final String DATABASE_LOGIN = "root";
	private static final String DATABASE_SECRET = "activ";

	public static void main(String[] args) {
		// utiliserSelectCapture();
		// utiliserSelectPokemon();
		// utiliserSelectDresseur();
		// utiliserInsertDresseur();
		// utiliserInsertPokemon();
		// utiliserSelectPokemon();
		afficherMenu();

	}

	private static final void afficherMenu() {
		int choix = 0;
		boolean estEnCoursDExecution = true;

		Scanner clavier = new Scanner(System.in);

		System.out.println("\n*Que voulez vous faire?*");
		System.out.println("1 : afficher le pokédex");
		System.out.println("2 : afficher la liste des dresseurs");
		System.out.println("3 : afficher la liste des pokémons capturés");
		System.out.println("4 : ajouter un dresseur");
		System.out.println("5 : capturer un pokémon");
		System.out.println("6 : quitter");

		choix = clavier.nextInt();
		clavier.reset();
		clavier.nextLine();

		while (estEnCoursDExecution == true) {

			switch (choix) {
			case 1:
				utiliserSelectPokemon();
				afficherMenu();

				break;

			case 2:
				utiliserSelectDresseur();
				afficherMenu();

				break;

			case 3:
				utiliserSelectCapture();
				afficherMenu();

				break;
			case 4:
				utiliserInsertDresseur();
				afficherMenu();

				break;
			case 5:

				afficherMenu();

				break;

			case 6:
				estEnCoursDExecution = false;
				System.out.println("Vous quittez le programme");
				System.exit(0);
				break;

			default:
				estEnCoursDExecution = false;
				System.out.println("Vous quittez le programme");
				break;
			}
		}
	}

	private static final void utiliserSelectPokemon() {
		try {
			System.out.println("\nVoici la liste des pokémons : \n");
			Connection connexion = DriverManager.getConnection(DATABASE_URL, DATABASE_LOGIN, DATABASE_SECRET);
			String requete = "SELECT * FROM pokemon";

			Statement executeur = connexion.createStatement();
			ResultSet resultat = executeur.executeQuery(requete);

			while (resultat.next()) {
				System.out.println(resultat.getString("nom_pokemon"));
			}

			resultat.close();
			executeur.close();
			connexion.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static final void utiliserSelectDresseur() {

		try {
			System.out.println("\nVoici la liste des dresseurs : \n");
			Connection connexion = DriverManager.getConnection(DATABASE_URL, DATABASE_LOGIN, DATABASE_SECRET);
			String requete = "SELECT * FROM dresseur";

			Statement executeur = connexion.createStatement();
			ResultSet resultat = executeur.executeQuery(requete);

			while (resultat.next()) {
				System.out.println(resultat.getString("nom_dresseur"));
			}

			resultat.close();
			executeur.close();
			connexion.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static final void utiliserSelectCapture() {
		String ID_dress = null;
		String nom_dress = null;
		String ID_poke = null;

		Scanner clavier = new Scanner(System.in);
		System.out.println("De quel dresseur voulez-vous afficher les pokémons capturés?");
		nom_dress = clavier.nextLine();
		clavier.reset();

		try {

			Connection connexion = DriverManager.getConnection(DATABASE_URL, DATABASE_LOGIN, DATABASE_SECRET);
			String requete = " SELECT nom_dresseur, ID_Dresseur, nom_pokemon, ID_pokemon FROM dresseur "
					+ "NATURAL JOIN capturer NATURAL JOIN pokemon WHERE dresseur.nom_dresseur = ?;";

			PreparedStatement preparateur = connexion.prepareStatement(requete);
			preparateur.setString(1, nom_dress);

			ResultSet resultat = preparateur.executeQuery();

			System.out.println("\nVoici la liste des pokémons de " + nom_dress + " :");
			while (resultat.next()) {
				System.out.println(resultat.getString("nom_pokemon"));
			}

			resultat.close();

			connexion.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Mix entre executeur et preparateur pour aller chercher dans une colonne et
		// trouver l'info dans la colonne correspondante
		/*
		 * //A partir du nom du dresseur choisi, aller chercher son ID dans base de
		 * données try { Connection connexion =
		 * DriverManager.getConnection(DATABASE_URL, DATABASE_LOGIN, DATABASE_SECRET);
		 * String requete = "SELECT * FROM dresseur WHERE dresseur.nom_dresseur = ?" ;
		 * 
		 * PreparedStatement preparateur = connexion.prepareStatement(requete);
		 * preparateur.setString(1, nom_dress);
		 * 
		 * ResultSet resultat = preparateur.executeQuery();
		 * 
		 * while (resultat.next()) { ID_dress=(resultat.getString("ID_Dresseur"));
		 * System.out.println(ID_dress); }
		 * 
		 * resultat.close(); preparateur.close(); connexion.close();
		 * 
		 * 
		 * } catch (SQLException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

	}

	private static final void utiliserInsertPokemon() {

		String nom_poke;
		int ID_type = 0;
		int NO_nat = 0;

		Scanner clavier = new Scanner(System.in);

		System.out.println("Quel est le nom du pokémon que vous voulez ajouter?");
		nom_poke = clavier.nextLine();
		clavier.reset();
		clavier.nextLine();

		System.out.println("Quel est le numéro du type du pokemon que vous voulez ajouter?");
		System.out.println("Pour rappel : (1,\"Feu\"),\r\n" + "(2,\"Normal\"),\r\n" + "(3,\"Eau\"),\r\n"
				+ "(4,\"Plante\"),\r\n" + "(5,\"Electrik\"),\r\n" + "(6,\"Glace\"),\r\n" + "(7,\"Combat\"),\r\n"
				+ "(8,\"Poison\"),\r\n" + "(9,\"Sol\"),\r\n" + "(10,\"Vol\"),\r\n" + "(11,\"Psy\"),\r\n"
				+ "(12,\"Insecte\"),\r\n" + "(13,\"Roche\"),\r\n" + "(14,\"Spectre\"),\r\n" + "(15,\"Dragon\"),\r\n"
				+ "(16,\"Tenebres\"),\r\n" + "(17,\"Acier\");");
		ID_type = clavier.nextInt();
		clavier.reset();
		clavier.nextLine();

		System.out.println("Quel est le numéro au pokedex national du pokémon que vous voulez ajouter?");
		NO_nat = clavier.nextInt();
		clavier.reset();
		clavier.nextLine();

		try {
			Connection connexion = DriverManager.getConnection(DATABASE_URL, DATABASE_LOGIN, DATABASE_SECRET);
			String requete = "INSERT INTO pokemon (nom_pokemon, ID_type, NO_national) VALUES (?, ?, ?)";
			PreparedStatement preparateur = connexion.prepareStatement(requete);
			preparateur.setString(1, nom_poke);
			preparateur.setInt(2, ID_type);
			preparateur.setInt(3, NO_nat);

			preparateur.execute();

			preparateur.close();
			connexion.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static final void utiliserInsertDresseur() {

		String nom_dresseur;

		Scanner clavier = new Scanner(System.in);

		System.out.println("Quel est le nom du dresseur que vous voulez ajouter?");
		nom_dresseur = clavier.nextLine();
		clavier.reset();

		try {
			Connection connexion = DriverManager.getConnection(DATABASE_URL, DATABASE_LOGIN, DATABASE_SECRET);
			String requete = "INSERT INTO pokemon (nom_dresseur) VALUES (?)";
			PreparedStatement preparateur = connexion.prepareStatement(requete);
			preparateur.setString(1, nom_dresseur);

			preparateur.execute();

			preparateur.close();
			connexion.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
