package com.ojha.server;

/**
 * Created by alexandra on 01/01/2015.
 */
public class Parser {

    public String parse(String inputLine) {

        String[] lineFragments = inputLine.split(" ");
        if (lineFragments.length < 1) {
            return null;
        }

        String command = lineFragments[0];

        if (command.equals("set")) {
            return this.set(lineFragments);
        }

        throw new RuntimeException("command not found: " + command);
    }

    private String set(String[] lineFragments) {
        return null;
    }
}
