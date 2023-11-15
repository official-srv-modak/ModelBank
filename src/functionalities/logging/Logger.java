package functionalities.logging;

public abstract class Logger {
    public static void logError(String className, String message)
    {
        System.out.println("Class : "+className+" error message "+message);
    }

    public static void logSuccess(String className, String message)
    {
        System.out.println("Class : "+className+" success message "+message);
    }
}
