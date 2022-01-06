package parser.commands;

import parser.Parser;
import exceptions.ParserException;

import java.util.Arrays;

public class Command {
    public String name, description, syntaxTip;
    public String[] validFlagSets;
    public Parser parser;

    public Command(Parser parser){
        this.parser = parser;
        init();
    }

    public String getSyntaxTip(){
        return "Синтаксис: " + syntaxTip;
    }

    public void init(){
        name = "cmd";
        description = "Базовая команда";
        syntaxTip = "cmd";
        validFlagSets = new String[]{""};
    }

    public boolean isSubChar(char subChar, String str){
        int strLen = str.length();
        for (int i = 0; i < strLen; i++)
            if (subChar == str.charAt(i))
                return true;
        return false;
    }

    public boolean isSubChar(char subChar, char[] str){
        for (char c : str)
            if (subChar == c)
                return true;
        return false;
    }

    public boolean isValidFlagSet(char[] flags){
        int i, j;
        if (flags.length == 0)
            return true;
        for (i = 0; i < validFlagSets.length; i++){
            j = 0;
            while (j < flags.length && isSubChar(flags[j], validFlagSets[i]))
                j++;
            if (j == flags.length)
                return true;
        }
        return false;
    }

    public void function(char[] flags, String[] args) throws ParserException {
        System.out.println(flags);
        System.out.println(Arrays.toString(args));
        System.out.println(isValidFlagSet(flags));
    }
}
