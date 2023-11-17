import java.io.FileInputStream;
import java.util.List;
import java.util.ArrayList;

public class MPass2 {

    private static List<List<String>> MNT = new ArrayList<>();
    private static List<List<String>> MDT = new ArrayList<>();
    private static List<List<String>> ALA = new ArrayList<>();
    private static List<List<String>> inputTokens = new ArrayList<>();
    private static String input = "";
    private static String inputMDT = "";
    private static String inputALA = "";
    private static String output = "";

    private static void takeInput() {
        try {
            FileInputStream ip = new FileInputStream("./inputs/Pass_II_Input.txt");
            int temp;

            while ((temp = ip.read()) != -1) {
                input += (char) temp;
            }

        } catch (Exception e) {
            System.out.println("Error " + e);
        }
    }

    private static void takeInputMDT() {
        try {
            FileInputStream ip = new FileInputStream("./inputs/MDTable.txt");
            int temp;

            while ((temp = ip.read()) != -1) {
                inputMDT += (char) temp;
            }

        } catch (Exception e) {
            System.out.println("Error " + e);
        }
    }

    private static void takeInputALA() {
        try {
            FileInputStream ip = new FileInputStream("./inputs/ALAtable.txt");
            int temp;

            while ((temp = ip.read()) != -1) {
                inputALA += (char) temp;
            }
            inputALA.replaceAll("&", "");

        } catch (Exception e) {
            System.out.println("Error " + e);
        }
    }

    private static void handleMNT() {
        try {
            String input = "";
            FileInputStream ip = new FileInputStream("./inputs/MNTtable.txt");
            int temp;
            while ((temp = ip.read()) != -1) {
                input += (char) temp;
            }
            // System.out.println(input);
            // tokenize

            for (int i = 0; i < input.length() - 1; i++) {
                String token1 = "";
                String token2 = "";

                while (input.charAt(i) >= 'A'
                        && input.charAt(i) <= 'Z') {
                    token1 += input.charAt(i);
                    i++;
                }

                // skip space
                i++;

                while (input.charAt(i) >= '0'
                        && input.charAt(i) <= '9') {
                    token2 += input.charAt(i);
                    i++;
                }
                i++;

                List<String> MNTLine = new ArrayList<>();
                MNTLine.add(token1);
                MNTLine.add(token2);
                MNT.add(MNTLine);

                System.out.println("." + token1 + "." + token2 + ".");
            }

        } catch (Exception e) {
            System.out.println("Error " + e);
        }
    }

    private static boolean isChar(char e) {

        if (e >= 'A' && e <= 'Z') {
            return true;
        } else if (e >= '0' && e <= '9') {
            return true;
        } else if (e == '=') {
            return true;
        }
        return false;
    }

    private static int isMacro(String e) {

        for (int i = 0; i < MNT.size(); i++) {
            if (e.equals(MNT.get(i).get(0))) {
                return Integer.parseInt(MNT.get(i).get(1));
            }
        }

        return -1;

    }

    private static void tokenizeInput() {

        List<String> token = new ArrayList<>();
        String temp = "";
        for (int i = 0; i < input.length(); i++) {
            char j = input.charAt(i);
            if (j == '\n') {
                if (temp.length() > 0) {
                    token.add(temp);
                    temp = "";
                }

                if (token.size() > 0) {

                    inputTokens.add(new ArrayList<>(token));
                    token.clear();

                }
            } else if (isChar(j)) {
                temp += j;
            } else {
                // not a character
                if (temp.length() > 0) {
                    token.add(temp);
                    temp = "";
                }
            }
        }

    }

    private static void tokenizeInputMDT() {

        List<String> token = new ArrayList<>();
        String temp = "";
        for (int i = 0; i < inputMDT.length(); i++) {
            char j = inputMDT.charAt(i);
            if (j == '\n') {
                if (temp.length() > 0) {
                    token.add(temp);
                    temp = "";
                }

                if (token.size() > 0) {

                    MDT.add(new ArrayList<>(token));
                    token.clear();

                }
            } else if (isChar(j)) {
                temp += j;
            } else {
                // not a character
                if (temp.length() > 0) {
                    token.add(temp);
                    temp = "";
                }
            }
        }
    }

    private static void tokenizeInputALA() {

        List<String> token = new ArrayList<>();
        String temp = "";
        for (int i = 0; i < inputALA.length(); i++) {
            char j = inputALA.charAt(i);
            if (j == '\n') {
                if (temp.length() > 0) {
                    token.add(temp);
                    temp = "";
                }

                if (token.size() > 0) {

                    ALA.add(new ArrayList<>(token));
                    token.clear();

                }
            } else if (isChar(j)) {
                temp += j;
            } else {
                // not a character
                if (temp.length() > 0) {
                    token.add(temp);
                    temp = "";
                }
            }
        }

    }

    private static void process() {
        for (int i = 0; i < inputTokens.size(); i++) {
            int ptr = isMacro(inputTokens.get(i).get(0));
            if (ptr == -1) {
                // send to output as it is
                for (int j = 0; j < inputTokens.get(i).size(); j++) {
                    output += inputTokens.get(i).get(j) + "\t";
                }
                output += '\n';
            } else {
                // hurray its a macro  
                System.out.println(ptr);
                // Store macro code in the temp
                String temp = "";
                for (int j = ptr; j < MDT.size(); j++) {
                    if (MDT.get(j).get(1).equals("MEND")) {
                        break;
                    }

                    for (int k = 1; k < MDT.get(j).size(); k++) {
                        temp += MDT.get(j).get(k) + "\t";
                    }
                    temp += "\n";
                }

                // replace parameters with actual parameters from ALA
                // find location of macro in ALA
                int j = 0;
                for (j = 0; j < ALA.size(); j++) {
                    if (inputTokens.get(i).get(0).equals(ALA.get(j).get(0))) {
                        break;
                    }
                }
                // j point -> ALA NAME
                // loop till end
                j++;
                while (!ALA.get(j).get(0).equals("END")) {
                    temp = temp.replaceAll(ALA.get(j).get(0).toString(), ALA.get(j).get(1));

                    j++;
                }
                System.out.println(temp);

                output += temp;
            }
        }
    }

    public static void main(String args[]) {
        handleMNT();
        System.out.println(MNT);
        takeInput();
        takeInputMDT();
        takeInputALA();

        // System.out.println(input);
        tokenizeInput();
        System.out.println(inputTokens);
        tokenizeInputMDT();
        System.out.println(MDT);
        tokenizeInputALA();
        System.out.println(ALA);

        process();

        System.out.println("\n\n++++++ OUTPUT +++++++");
        System.out.println(output);

    }
}
