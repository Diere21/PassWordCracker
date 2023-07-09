import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class BruteForcePasswordCracker implements PassWordCracker {


//    cette méthode permet de 
    public void loop(int index, String passWord, StringBuilder caractere) {
        for (int i = 97; i < 97 + 26; i++) {
            caractere.setCharAt(index, (char) i);
            if (index < passWord.length() - 1)
                loop(index + 1, passWord, caractere);

            // System.out.println(caractere);
            if (caractere.toString().equals(passWord)) {
                System.out.println("Mot de passe trouvé : " + caractere);
                System.exit(0);
            }
        }
    }

    public String crackSimple(String password) {
        StringBuilder caractere = new StringBuilder(password);
        loop(0, password, caractere);

        return null;
    }


    public String crackHashed(String hashedPassword) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            StringBuilder password = new StringBuilder();
            StringBuilder hashedAttempt = new StringBuilder();
            int MAX_PASSWORD_LENGTH = 10; // Définissez ici la longueur maximale du mot de passe à tester

            for (int length = 1; length <= MAX_PASSWORD_LENGTH; length++) {
                if (crackHashedRecursive(md, password, hashedAttempt, hashedPassword, length)) {
                    System.out.println("Mot de passe trouvé : " + password);
                    return password.toString();
                }
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        System.out.println("Le mot de passe n'a pas été trouvé");
        return null;
    }

    private boolean crackHashedRecursive(MessageDigest md, StringBuilder password, StringBuilder hashedAttempt,
        String hashedPassword, int length) {
            if (length == 0) {
                md.update(password.toString().getBytes());
                byte[] hash = md.digest();
                hashedAttempt.setLength(0);

                for (byte b : hash) {
                    hashedAttempt.append(String.format("%02x", b));
                }

                return hashedAttempt.toString().equals(hashedPassword);
            }

            for (int i = 97; i < 97 + 26; i++) {
                password.append((char) i);
                if (crackHashedRecursive(md, password, hashedAttempt, hashedPassword, length - 1)) {
                    return true;
                }
                password.deleteCharAt(password.length() - 1);
            }

             return false;
        }


    //  cette méthode permet de calculer le hash des mot de passe
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hashedPassword = new StringBuilder();

            for (byte b : hash) {
                hashedPassword.append(String.format("%02x", b));
            }

            return hashedPassword.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }
}
