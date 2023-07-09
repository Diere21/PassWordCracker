public interface PassWordCracker {
    String crackSimple(String password);
    String crackHashed(String password);
}