package fr.epsi.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

class MathUtilsTests {

	@ParameterizedTest(name = "factorial of {0} is {1}")

	@CsvFileSource(resources = { "/data/sangoku.csv" }, delimiter = ';')
	public void factoriel(double number, double expected) {
		assertEquals(expected, MathUtils.factorial(number));
	}

	@ParameterizedTest(name = "{0} est un nombre premier")

	@ValueSource(ints = { 2, 3, 5, 7 })
	public void premier(int x) {
		assertTrue(MathUtils.isPrimeNumber(x));
	}

	@ParameterizedTest(name = "{0} n'est pas un nombre premier")

	@ValueSource(ints = { 1, 4, 6, 8, 9, 10 })
	public void pasPremier(int x) {
		assertFalse(MathUtils.isPrimeNumber(x));
	}
}