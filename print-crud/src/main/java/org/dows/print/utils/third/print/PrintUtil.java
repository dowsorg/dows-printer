package org.dows.print.utils.third.print;

public class PrintUtil {

    public static String alignText(String text, char justified, int wide) {
        int nOfSpaces = wide - text.length();
        String spaces;
        String result = "";

        switch (justified) {
            case 'r':
                spaces = new String(new char[nOfSpaces]).replace('\0', ' ');
                result = spaces + text;
                break;
            case 'l':
                spaces = new String(new char[nOfSpaces]).replace('\0', ' ');
                result = text + spaces;
                break;
            case 'c':
                int lxNOfSpaces = nOfSpaces / 2;
                int rxNOfSpaces = (nOfSpaces % 2) == 0 ? lxNOfSpaces : lxNOfSpaces + 1;

                String lxSpaces = new String(new char[lxNOfSpaces]).replace('\0', ' ');
                String rxSpaces = new String(new char[rxNOfSpaces]).replace('\0', ' ');

                result = lxSpaces + text + rxSpaces;
                break;
        }
        return result;
    }

}
