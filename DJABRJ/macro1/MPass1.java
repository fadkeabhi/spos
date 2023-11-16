import java.io.FileInputStream;
import java.io.FileOutputStream;

public class MPass1 {

    private static String input = "";
    private static String output = "";
    private static String MNT = "";
    private static String MDT = "";
    private static String ALA = "";
    private static int MDTCounter = 0;

    private static void readIp() {
        try {
            FileInputStream ip = new FileInputStream("./inputs/input.txt");
            int temp;
            while ((temp = ip.read()) != -1) {
                input += (char) temp;
            }

            input = input.toUpperCase();

            ip.close();

        } catch (Exception e) {
            System.out.println("Error " + e);
        }
    }

    private static void processor() {
        int i = 0;

        while (i < input.length()) {

            // skip spaces
            while (input.charAt(i) < 'A' || input.charAt(i) > 'Z') {
                i++;
            }

            // check if macro word
            if (i < input.length() - 5
                    && input.charAt(i) == 'M'
                    && input.charAt(i + 1) == 'A'
                    && input.charAt(i + 2) == 'C'
                    && input.charAt(i + 3) == 'R'
                    && input.charAt(i + 4) == 'O') {

                // skip spaces and macro keyword
                i += 5;
                while (input.charAt(i) < 'A' || input.charAt(i) > 'Z') {
                    i++;
                }

                int j = i;
                String macroname = "";
                // get macro name
                while (input.charAt(j) >= 'A' && input.charAt(j) <= 'Z') {
                    macroname += input.charAt(j);
                    j++;
                }

                String parameters = "";
                while (input.charAt(j) != '\n') {
                    parameters += input.charAt(j);
                    j++;
                }

                System.out.println(macroname);
                System.out.println(parameters);

                // Handle ALA from parameters
                ALA += macroname + '\n';
                int positionCounter = 1;
                String defValue;
                for (int k = 0; k < parameters.length(); k++) {
                    if (parameters.charAt(k) == '&') {
                        String parName = "";
                        while (parameters.charAt(k) != ','
                                && parameters.charAt(k) != '='
                                && parameters.charAt(k) != '\n') {
                            parName += parameters.charAt(k);
                            k++;
                        }
                        System.out.println(parName);
                        defValue = "";
                        if (parameters.charAt(k) == ','
                                || parameters.charAt(k) == '\n') {
                            // positined
                            System.out.println("pos: " + parName);
                            defValue = String.format("N%s", positionCounter++);

                        } else if (parameters.charAt(k) == '=' && parameters.charAt(k + 1) == ',') {
                            // keyword
                            System.out.println("key: " + parName);
                            defValue = String.format("N%s", positionCounter++);

                        } else if (parameters.charAt(k) == '=') {
                            // default
                            System.out.println("def: " + parName);

                            // find the deafult value
                            while (k < parameters.length()
                                    && parameters.charAt(k) != '&') {
                                defValue += parameters.charAt(k);
                                k++;
                            }

                        }

                        ALA += parName + " " + defValue + '\n';
                        
                    }
                    

                }
                ALA += "END\n";

                i = ++j;
                // System.out.println(input.charAt(i));

                // find MEND
                MDT += String.format("%s ", ++MDTCounter);
                MNT += String.format("%s %s\n", macroname, MDTCounter);
                while (true) {
                    if (input.charAt(i) == 'M'
                            && input.charAt(i + 1) == 'E'
                            && input.charAt(i + 2) == 'N'
                            && input.charAt(i + 3) == 'D') {
                        MDT += "MEND\n";
                        break;
                    }

                    MDT += input.charAt(i);
                    if (input.charAt(i) == '\n') {
                        MDT += String.format("%s ", ++MDTCounter);
                    }
                    i++;
                }

                // Macro handle end
            } else if (i < input.length() - 5
                    && input.charAt(i) == 'S'
                    && input.charAt(i + 1) == 'T'
                    && input.charAt(i + 2) == 'A'
                    && input.charAt(i + 3) == 'R'
                    && input.charAt(i + 4) == 'T') {

                System.out.println("START ENCOUNTERED");
                while (true) {
                    if (input.charAt(i) == 'E'
                            && input.charAt(i + 1) == 'N'
                            && input.charAt(i + 2) == 'D') {
                        output += "END\n";
                        break;
                    }

                    output += input.charAt(i);

                    i++;
                }
            }

            i++;

        }

    }

    public static void outputFiles() {
        try {
            FileOutputStream op = new FileOutputStream("./outputs/output.txt");
            op.write(output.getBytes());
            op.close();

            op = new FileOutputStream("./outputs/ala.txt");
            op.write(ALA.getBytes());
            op.close();

            op = new FileOutputStream("./outputs/mdt.txt");
            op.write(MDT.getBytes());
            op.close();

            op = new FileOutputStream("./outputs/mnt.txt");
            op.write(MNT.getBytes());
            op.close();

        } catch (Exception e) {
            System.out.println("Error " + e);

        }
    }

    public static void main(String[] args) {
        readIp();
        // System.out.println(input);
        processor();
        // System.out.println(MDT);
        // System.out.println(MNT);
        // System.out.println(output);
        // System.out.println(ALA);

        outputFiles();
    }

}
