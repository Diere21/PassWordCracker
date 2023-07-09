import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean running = true;

        while (running) {
            System.out.println("Que souhaitez-vous faire ?");
            System.out.println("1. Hasher un mot de passe");
            System.out.println("2. Craquer un mot de passe");
            System.out.println("3. Quitter");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Vider le tampon

            switch (choice) {
                case 1:
                    System.out.print("Veuillez saisir le mot de passe à hasher : ");
                    String passwordToHash = scanner.nextLine();
                    PasswordHasher passwordHasher = new PasswordHasher();
                    String hashedPassword = passwordHasher.hashPassword(passwordToHash);
                    System.out.println("Mot de passe hashé : " + hashedPassword);
                    break;
                case 2:
                    System.out.print("Veuillez saisir le mot de passe à craquer : ");
                    String passwordToCrack = scanner.nextLine();
                    System.out.print("Veuillez saisir le type de cracker (BruteForce, Dictionnary) : ");
                    String crackerType = scanner.nextLine();
                    PassWordCracker cracker = CrackerFactory.createPassWordCracker(crackerType);
                    if (passwordToCrack.startsWith("$")) {
                        // Le mot de passe est hashé, supprimer le préfixe "$" pour obtenir le hash
                        String hashToCrack = passwordToCrack.substring(1);
                        cracker.crackHashed(hashToCrack);
                    } else {
                        // Le mot de passe est simple, utiliser crackSimple
                        cracker.crackSimple(passwordToCrack);
                    }
                    break;


                case 3:
                    running = false;
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
            }

            if (running) {
                System.out.print("Souhaitez-vous continuer (N/Y) ? ");
                String continueChoice = scanner.nextLine();
                if (!continueChoice.equalsIgnoreCase("Y")) {
                    running = false;
                }
            }
        }

        scanner.close();
    }
}