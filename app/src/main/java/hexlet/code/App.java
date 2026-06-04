package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(
        name = "gendiff",
        description = "Compares two configuration files and shows a difference.",
        mixinStandardHelpOptions = true,
        version = "gendiff 1.0"
)
public class App implements Runnable {

    @Parameters(
            index = "0",
            paramLabel = "filepath1",
            description = "Path to first file"
    )
    private String filepath1;

    @Parameters(
            index = "1",
            paramLabel = "filepath2",
            description = "Path to second file"
    )
    private String filepath2;

    @Option(
            names = {"-f", "--format"},
            description = "Output format (default: stylish)",
            defaultValue = "stylish"
    )
    private String format;

    @Override
    public void run() {
        try {
            String result = Differ.generate(filepath1, filepath2, format);
            System.out.println(result);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}