package com.example.carrot.common;

import java.io.PrintStream;
import org.springframework.boot.Banner;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.ansi.AnsiStyle;
import org.springframework.core.env.Environment;

public class TryBanner implements Banner {
//    private static final String[] BANNER = new String[]{"", "  _____                                 \n |  __ \\                               \n | |__) |_ _ _ __ __ _ _ __   __ _      \n |  ___/ _` | '__/ _` | '_ \\ / _` |    \n | |  | (_| | | | (_| | | | | (_| |     \n |_|   \\__,_|_|  \\__,_|_| |_|\\__,_|  \n                                        \n                                   "};
//    private static final String[] BANNER = new String[]{""," __      __                       \n/  \\    /  \\_____ _______  _____  \n\\   \\/\\/   /\\__  \\\\_  __ \\/     \\ \n \\        /  / __ \\|  | \\/  Y Y  \\\n  \\__/\\  /  (____  /__|  |__|_|  /\n       \\/        \\/            \\ / "};
    private static final String[] BANNER = new String[]{"_________                             __   \n\\_   ___ \\_____ ______________  _____/  |_ \n/    \\  \\/\\__  \\\\_  __ \\_  __ \\/  _ \\   __\\\n\\     \\____/ __ \\|  | \\/|  | \\(  <_> )  |  \n \\______  (____  /__|   |__|   \\____/|__|  \n        \\/     \\/                          "};
    private static final String SPRING_BOOT = " :: Powered by warm personally :: ";
    private static final int STRAP_LINE_SIZE = 42;

    public TryBanner() {
    }

    public void printBanner(Environment environment, Class<?> sourceClass, PrintStream printStream) {
        String[] var4 = BANNER;
        int var5 = var4.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            String line = var4[var6];
            printStream.println(line);
        }

        String version = "(v1.0)";

        String padding;
        for(padding = ""; padding.length() < STRAP_LINE_SIZE - (version.length() + SPRING_BOOT.length()); padding = padding + " ") {
        }

        printStream.println(AnsiOutput.toString(new Object[]{AnsiColor.BRIGHT_RED, SPRING_BOOT, AnsiColor.DEFAULT, padding, AnsiStyle.FAINT, version}));
        printStream.println();
    }
}
