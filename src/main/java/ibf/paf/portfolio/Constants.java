package ibf.paf.portfolio;

public class Constants {

    private Constants() {
        throw new IllegalStateException("Utility class");
      }

    public static final String API_URL_SEARCH = "https://alpha-vantage.p.rapidapi.com";
    public static final String HOST_SEARCH = "alpha-vantage.p.rapidapi.com";
    public static final String ENV_SEARCH_KEY = "RAPIDAPI_KEY";
    public static final String API_URL_FLASK = "http://159.223.59.64/api";
}
