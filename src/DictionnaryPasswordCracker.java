import java.io.File;
import java.io.FileNotFoundException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class DictionnaryPasswordCracker implements PassWordCracker {
  
  //   cette méthode permet de chercher des mot de passe dans le dictionaire
    public String crackSimple(String password) {
        // Implémentation spécifique pour cracker un mot de passe simple avec le dictionnaire
        String cheminDictionnaire = "./Dictionnaire.txt"; // Chemin vers le fichier du dictionnaire

        // Charger le dictionnaire depuis le fichier
        File fichierDictionnaire = new File(cheminDictionnaire);
        try {
            Scanner scanner = new Scanner(fichierDictionnaire);
            while (scanner.hasNextLine()) {
                String mot = scanner.nextLine().trim();

                // Vérifier si le mot correspond au mot de passe
                if (mot.equals(password)) {
                    System.out.println("Mot de passe trouvé : " + mot);
                    break;
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("Fin de l'attaque par dictionnaire du mot de passe simple.");
        return null;
    }

    //  Cette méthode me permet de  comparer le hash donner en entré avec le hash des password du dictionaire 

    public String crackHashed(String hashedPassword) {
        // Implémentation spécifique pour cracker un mot de passe hashé avec le dictionnaire
        String cheminDictionnaire = "./Dictionnaire.txt"; // Chemin vers le fichier du dictionnaire

        // Charger le dictionnaire depuis le fichier
        File fichierDictionnaire = new File(cheminDictionnaire);
        try (Scanner scanner = new Scanner(fichierDictionnaire)) {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            while (scanner.hasNextLine()) {
                String mot = scanner.nextLine().trim();
                byte[] hash = md.digest(mot.getBytes());
                StringBuilder sb = new StringBuilder();

                for (byte b : hash) {
                    sb.append(String.format("%02x", b));
                }

                // Vérifier si le hash correspond au mot de passe hashé fourni en entrée
                if (sb.toString().equals(hashedPassword)) {
                    System.out.println("Mot de passe trouvé : " + mot);
                    return mot;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        System.out.println("Fin de l'attaque par dictionnaire.");
        return null;
    }



        // Méthode pour calculer le hash d'un mot de passe dans le dictionnaire
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


