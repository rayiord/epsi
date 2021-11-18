package fr.epsi.core;

public final class StringUtils {
    private static final int FIRST_CHAR = 'A';
    private static final int LAST_CHAR = 'Z';
    private static final int NB_CHARS = (LAST_CHAR - FIRST_CHAR) + 1;

    private StringUtils() {
    }

    /**
     * Encodes a sentence with Cesar with a specified step.
     *
     * @param sentence the sentence to decode.
     * @param step     the step to use.
     * @return the encoded sentence.
     */
    public static String cesarEncode(String sentence, int step) {
        char[] decoded = new char[sentence.length()];
        for (int i = 0; i < sentence.length(); i++) {
            char currentChar = sentence.charAt(i);
            if (currentChar == ' ' || currentChar == ',' || currentChar == '.') {
                decoded[i] = currentChar;
            } else {
                decoded[i] = encrypt(currentChar, (step % NB_CHARS));
            }
        }

        return new String(decoded);
    }

    /**
     * Decodes a sentence with Cesar with a specified step.
     *
     * @param sentence the sentence to decode.
     * @param step     the step to use.
     * @return the decoded sentence.
     */
    public static String cesarDecode(String sentence, int step) {
        return cesarEncode(sentence, -step);
    }

    /**
     * Checks if the sentence is a palindrome.
     *
     * @param sentence the sentence to check.
     * @return true if the sentence is a palindrome, false otherwise.
     */
    public static boolean isPalindrome(String sentence) {
        sentence = sentence.replaceAll("\\s+", "");
        char[] reverse = new char[sentence.length()];
        int length = sentence.length();
        for (int i = 0; i < length; i++) {
            reverse[i] = sentence.charAt(length - i - 1);
        }

        return new String(reverse).equals(sentence);
    }

    /**
     * Encrypts a character with a given step.
     *
     * @param character the character to encrypt.
     * @param step      the step to use
     * @return the encrypted character.
     */
    private static char encrypt(char character, int step) {
        int charIndexInAlphabet = (character - FIRST_CHAR);
        int encryptedCharIndex = charIndexInAlphabet + step + NB_CHARS;
        return (char) ((encryptedCharIndex % NB_CHARS) + FIRST_CHAR);
    }


}
