package fr.duboc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Pokemon {
	
	private int ID_pokemon;
	private String nom_pokemon;
	private int ID_type;
	private int NO_national;
	/**
	 * @param iD_Pokemon
	 * @param nom_Pokemon
	 * @param iD_type
	 * @param nO_national
	 */
	public Pokemon(int iD_pokemon, String nom_pokemon, int iD_type, int nO_national) {
		super();
		ID_pokemon = iD_pokemon;
		this.nom_pokemon = nom_pokemon;
		ID_type = iD_type;
		NO_national = nO_national;
	}
	

	
	

}
