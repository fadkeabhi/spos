import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

class Pass2 {
    private static String input = "";
    private static String inputL = "";
    private static String inputS = "";
    private static String output = "";
    private static List<String> literals = new ArrayList<String>();
    private static List<String> symbols = new ArrayList<String>();

    private static void readInput() {
        try {
            FileInputStream ip = new FileInputStream("inputs/input.txt");
            int temp;
            while ((temp = ip.read()) != -1) {
                input += (char) temp;
            }

            input = input.toUpperCase();

        } catch (Exception e) {
            System.out.println("Error " + e);
        }
    }

    private static void readLiteral() {
        try {
            FileInputStream ip = new FileInputStream("inputs/littable.txt");
            int temp;
            while ((temp = ip.read()) != -1) {
                inputL += (char) temp;
            }

            String num = "";
            for (int i = 0; i < inputL.length(); i++) {
                if (inputL.charAt(i) == '=') {
                    i += 2;
                    // find second '
                    while (inputL.charAt(i) != '\'') {
                        i++;
                    }

                    // skip spaces
                    while (inputL.charAt(i) < '0' || inputL.charAt(i) > '9') {

                        i++;
                    }

                    // take number
                    while (i < inputL.length() && (inputL.charAt(i) >= '0' && inputL.charAt(i) <= '9')) {
                        num += inputL.charAt(i);
                        i++;
                    }
                    literals.add(num);
                    num = "";

                }
            }
            System.out.println(literals);

        } catch (Exception e) {
            System.out.println("Error " + e);
        }
    }

    private static void readSymbol() {
        try {
            FileInputStream ip = new FileInputStream("./inputs/symtable.txt");
            int temp;
            while ((temp = ip.read()) != -1) {
                inputS += (char) temp;
            }

            inputS = inputS.toUpperCase();

            int i = 0;

            String num = "";
            while(i<inputS.length()){
                // find symbol name start
                while(i<inputS.length() && (inputS.charAt(i) < 'A' || inputS.charAt(i) > 'Z')){
                    i++;
                }


                // skip symbol name
                while(i<inputS.length() && (inputS.charAt(i) >= 'A' && inputS.charAt(i) <= 'Z')){

                    i++;
                }



                // find address start
                while(i<inputS.length() && (inputS.charAt(i) < '0' || inputS.charAt(i) > '9')){
                    i++;
                }

                // Add adress to num
                while(i<inputS.length() && (inputS.charAt(i) >= '0' && inputS.charAt(i) <= '9')){
                    num += inputS.charAt(i);
                    i++;
                }

                symbols.add(num);
                num = "";
            }
            System.out.println(symbols);


        } catch (Exception e) {
            System.out.println("Error " + e);
        }
    }

    private static void calculate() {
        String temp = "";
        for (int i = 0; i < input.length(); i++) {

            char j = input.charAt(i);
            if (j == '\n') {
                temp += '\n';
                output += temp;
                temp = "";
            } else if (j == '(') {
                // skip ADs
                if (input.charAt(i + 4) == 'A' && input.charAt(i + 5) == 'D') {
                    temp = "";
                    // skip to eol
                    while (input.charAt(i) != '\n') {
                        i++;
                    }
                }

                // for IS
                if (input.charAt(i + 4) == 'I' && input.charAt(i + 5) == 'S') {
                    temp += String.format("%s%s\t", input.charAt(i + 1), input.charAt(i + 2));

                    while (input.charAt(i) != ')') {
                        i++;
                    }
                }

                // for literals, symbols, constants
                if (input.charAt(i + 1) == 'L' || input.charAt(i + 1) == 'S' || input.charAt(i + 1) == 'C') {
                    char category = input.charAt(i + 1);
                    String num = "";
                    int value = -1;
                    i += 3;
                    while (input.charAt(i) != ')') {
                        num += input.charAt(i);
                        i++;
                    }

                    // constant
                    if (category == 'C') {
                        value = Integer.parseInt(num);
                    } else if (category == 'L') {
                        value = Integer.parseInt(num);
                        value = Integer.parseInt(literals.get(value - 1));
                    } else if (category == 'S') {
                        value = Integer.parseInt(num);
                        value = Integer.parseInt(symbols.get(value - 1));
                    }

                    temp += String.format("%s\t", value);

                }

            } else if (j >= '0' && j <= '9') {
                temp += j;
                // place tab if next is not number
                if (i + 1 != input.length() && !(input.charAt(i + 1) >= '0' && input.charAt(i + 1) <= '9')) {
                    temp += '\t';
                }
            }

            // System.out.println(input.charAt(i+4));

        }

        output += temp;
    }

    private static void writeOutput(){
        try{
            FileOutputStream op = new FileOutputStream("./outputs/output.txt");
            op.write(output.getBytes());
        } catch(Exception e){
            System.out.println("Error" + e);
        }

        
    }

    public static void main(String[] args) {
        readInput();
        readLiteral();
        readSymbol();
        calculate();
        writeOutput();

        System.out.println(output.toString());
    }
}