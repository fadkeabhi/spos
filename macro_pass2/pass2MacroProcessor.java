import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

@SuppressWarnings("unused")
class Ala {
  HashMap < String, String > Arguments = new HashMap < String, String > ();
}

public class pass2MacroProcessor {
  static HashMap < String, Integer > MNT = new HashMap < String, Integer > ();
  static HashMap < Integer, ArrayList < String >> MDT = new HashMap < Integer, ArrayList < String >> ();
  static HashMap < String, Ala > AlaTable = new HashMap < String, Ala > ();
  static int MDTC = 1;
  static int MNTC = 1;

  private static void createMNTtable() {
    FileReader fr = null;
    try {
      fr = new FileReader("MNT.txt");
    } catch (FileNotFoundException e1) {
      e1.printStackTrace();
    }
    BufferedReader br = new BufferedReader(fr);
    String s = null;
    try {
      while ((s = br.readLine()) != null) {
        StringTokenizer tokens = new StringTokenizer(s, " ", false);
        ArrayList < String > arrayList = new ArrayList < > ();
        while (tokens.hasMoreTokens()) {
          arrayList.add(tokens.nextToken());
        }
        MNT.put(arrayList.get(0), Integer.parseInt(arrayList.get(1)));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void createMDTtable() {
    FileReader fr = null;
    try {
      fr = new FileReader("MDT.txt");
    } catch (FileNotFoundException e1) {
      e1.printStackTrace();
    }
    BufferedReader br = new BufferedReader(fr);
    String s = null;
    try {
      int i = 1;
      while ((s = br.readLine()) != null) {
        StringTokenizer tokens = new StringTokenizer(s, " ", false);
        ArrayList < String > arrayList = new ArrayList < > ();
        while (tokens.hasMoreTokens()) {
          arrayList.add(tokens.nextToken());
        }
        MDT.put(i, arrayList);
        i++;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void createALAtable() {
    FileReader fr = null;
    try {
      fr = new FileReader("ALA.txt");
    } catch (FileNotFoundException e1) {
      e1.printStackTrace();
    }
    BufferedReader br = new BufferedReader(fr);
    String s = null;
    try {
      int lineno = 1;
      while ((s = br.readLine()) != null) {
        Ala argument = new Ala();
        String curr = s;
        while (!(s = br.readLine()).equals("END")) {
          StringTokenizer tokens = new StringTokenizer(s, " ", false);
          ArrayList < String > arrayList = new ArrayList < > ();
          while (tokens.hasMoreTokens()) {
            arrayList.add(tokens.nextToken());
          }
          argument.Arguments.put(arrayList.get(0), arrayList.get(1));
        }
        AlaTable.put(curr, argument);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static String handleMacro(ArrayList < String > arrayList, int index) {
    return null;
  }

  public static void main(String[] args) throws FileNotFoundException {
    createMNTtable();
    createMDTtable();
    createALAtable();
    FileReader fr_input = new FileReader("input.txt");
    FileWriter fr_output = null;
    try {
      fr_output = new FileWriter("output.txt");
    } catch (IOException e1) {
      e1.printStackTrace();
    }
    try {
      BufferedReader br_input = new BufferedReader(fr_input);
      String s = null;
      while (((s = br_input.readLine()) != null)) {
        ArrayList < String > arrayList = new ArrayList < > ();
        StringTokenizer tokens = new StringTokenizer(s, " ");
        while (tokens.hasMoreTokens()) {
          arrayList.add(tokens.nextToken());
        }
        String curr = arrayList.get(0);
        if (MNT.containsKey(curr)) {
          String temp = "";
          int startPos = MNT.get(arrayList.get(0));
          for (int i = startPos + 1; !MDT.get(i).get(1).equals("MEND"); i++) {
            ArrayList < String > arrayList1 = new ArrayList < > ();
            arrayList1 = MDT.get(i);
            temp += arrayList1.get(1) + " ";
            for (int j = 2; j < arrayList1.size(); j++) {
              if (j == arrayList1.size() - 1)
                temp += AlaTable.get(curr).Arguments.get(arrayList1.get(j));
              else
                temp += AlaTable.get(curr).Arguments.get(arrayList1.get(j)) + ", ";
            }
            temp += "\n";
          }
          fr_output.write(temp);
        } else {
          fr_output.write(s + "\n");
        }
      }
    } catch (Exception e) {
      System.out.println(e);
    } finally {
      try {
        if (fr_output != null) {
          fr_output.flush();
          fr_output.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    System.out.println("successfully Executed :) \n please check output in final output file ");
  }
} 
