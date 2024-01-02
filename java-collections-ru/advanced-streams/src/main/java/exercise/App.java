package exercise;

import java.util.stream.Collectors;
import java.util.Arrays;

// BEGIN
class App {
    private static final String prefix  = "X_FORWARDED_";

    public static void main(String[] args) {}

    private static Boolean isEnvVariable(String str) {
        return str.startsWith("environment=") && hasForwardedPrefix(str);
    }

    private static Boolean hasForwardedPrefix(String str) {
        return str.contains(prefix);
    }

    private static String[] getEnvValues(String str) {
        return str.replace("environment=", "").replace("\"", "").split(",");
    }

    public static String getForwardedVariables(String config) {
        return Arrays.stream(config.split("\n"))
                .filter(App::isEnvVariable)
                .flatMap(s -> Arrays.stream(getEnvValues(s))
                        .filter(App::hasForwardedPrefix)
                        .map(p -> p.replaceAll(prefix, ""))
                )
                .collect(Collectors.joining(","));
    }
}
//END
