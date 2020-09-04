package home.lflt.utils;

public final class Constants {
    public final static String INPUT_REQUIRED = "Input Required";
    public final static String BAD_EMAIL = "Email not supported";
    public final static String PLACEHOLDER = "Placeholder";

    public final static long DAY_IN_MINUTES = 1440;
    public final static long DAY_OFFSET_IN_MINUTES = 72;
    public final static long WEEK_IN_HOURS = 168;
    public final static long WEEK_OFFSET_IN_HOURS = 8;
    public final static long MONTH_IN_HOURS = 672;
    public final static long MONTH_OFFSET_IN_HOURS = 33;
    public final static int LIMIT_99 = 99;

    public final static int MIN_AGE = 9;
    public final static int MAX_AGE = 99;

    public enum buyingAlgorithm {
        RANDOM, DROP, POSITIVE, USER
    }

    public enum portfolioState {
        USER, RANDOM, OVER
    }
}
