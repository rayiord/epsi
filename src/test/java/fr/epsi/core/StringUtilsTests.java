package fr.epsi.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

public class StringUtilsTests {

	@ParameterizedTest(name = "{0} est un palindrome")

	@ValueSource(strings = { "kayak", "non" })
	public void palindrome(String mot) {
		assertTrue(StringUtils.isPalindrome(mot));
	}

	@ParameterizedTest(name = "{0} n'est pas un palindrome")

	@ValueSource(strings = { "oui", "eau" })
	public void pasPalindrome(String mot) {
		assertFalse(StringUtils.isPalindrome(mot));
	}

	@ParameterizedTest(name = "Le codage fonctionne")
	@CsvFileSource(resources = { "/data/vegeta.csv" }, delimiter = ';')
	public void encode(String lettre, String expected, int step) {
		assertEquals(expected, StringUtils.cesarEncode(lettre, step));
	}

	@ParameterizedTest(name = "Le décodage fonctionne")
	@CsvFileSource(resources = { "/data/vegeta.csv" }, delimiter = ';')
	public void decode(String expected, String lettre, int step) {
		assertEquals(expected, StringUtils.cesarDecode(lettre, step));
	}

}
