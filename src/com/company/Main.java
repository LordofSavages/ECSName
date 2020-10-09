package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;

public class Main {

    public static String getInitials() {
        // getting username from console
        Scanner userInput = new Scanner(System.in);
        System.out.println("----------------\nenter username");
        String username = userInput.nextLine();

        // exit condition
        if (username.equals("exit")) System.exit(0);

        // trying to returning first 3 characters (initials) of username
        try {
            return username.substring(0, 3);
        } catch (StringIndexOutOfBoundsException e) {
            // print out error message, return null
            System.out.println("invalid input, too few characters");
            return null;
        }
    }

    public static String getName(String initials) throws IOException {
        String line;

        URL url = new URL("https://www.ecs.soton.ac.uk/people/" + initials);
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

        // looping through looking for line that contains < ... property="name">
        while ((line = br.readLine()) != null) {
            if (line.contains("property=\"name\">")) break;
        }

        if (line != null) {
            int beginIndex = line.indexOf("property=\"name\">") + 16;
            int endIndex = line.indexOf("<em property=\"honorificSuffix\">");
            return line.substring(beginIndex, endIndex);
        } else {
            /* line == null => couldn't find <... property="name"> in page.
             print out error message and return null */
            System.out.println("information not found");
            return null;
        }
    }

    public static void main(String[] args) throws IOException {
        String initials, output;

        System.out.println("type exit to exit"); //duh
        while (true) {
            //lazy while loop to keep asking for user input

            do {
                // handling too few characters
                initials = getInitials();
            } while (initials == null);

            output = getName(initials);
            if (output != null) {
                // handling information not found
                System.out.println(output);
            }
        }
    }
}
