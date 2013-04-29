package de.dhbw.td.core.resources;

import de.dhbw.td.core.util.EFlavor;
import de.dhbw.td.core.util.GameConstants;

public enum EInformationText {
	
	CODE_LVL1("C"),
	CODE_LVL2("Java"),
	CODE_LVL3("PHP"),
	CODE_LVL4("Assembler"),
	MATH_LVL1("Analysis"),
	MATH_LVL2("Lineare Algebra"),
	MATH_LVL3("Numerik"),
	MATH_LVL4("Statistik"),
	SOCIAL_LVL1("Projektmanagement"),
	SOCIAL_LVL2("Intercultural Communication"),
	SOCIAL_LVL3("Software Engineering I"),
	SOCIAL_LVL4("Software Engineering II"),
	SOCIAL_LVL5("Advanced Software Engineering"),
	SOCIAL_LVL6("Software Quality"),
	TECHINF_LVL1("Digitaltechnik"),
	TECHINF_LVL2("Betriebssysteme"),
	TECHINF_LVL3("Rechnertechnik"),
	TECHINF_LVL4("Verteilte Systeme"),
	THEOINF_LVL1("Logik"),
	THEOINF_LVL2("Algorithmen"),
	THEOINF_LVL3("Formale Sprachen"),
	THEOINF_LVL4("Compilerbau"),
	THEOINF_LVL5("Relationale Algebra"),
	WIWI_LVL1("BWL"),
	WIWI_LVL2("Business processes"),
	WIWI_LVL3("Worfklow management"),
	WIWI_LVL4("eBusiness"),
	WIWI_LVL5("Wissensmanagement"),
	WIWI_LVL6("Data Mining");
	
	private final String text;
	
	EInformationText(String text) {
		this.text = text;
	}
	
	private static EInformationText getTowerName(EFlavor flavor, int levelNumber) {
		String prefix = GameConstants.mapFlavorToImagePrefix(flavor);
		String enumName = String.format("%s_LVL%d", prefix, levelNumber);
		return EInformationText.valueOf(enumName);
	}
	
	public static String getInformationText(EFlavor flavor, int levelNumber) {
		String towerName = getTowerName(flavor, levelNumber).text;
		return String.format("%s", towerName);
	}
}
