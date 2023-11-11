import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

class Pass1 {
	static String input = "";
	static List<List<String>> inputTokens = new ArrayList<>();
	static String output = "";

	static List<String> literals = new ArrayList<>();
	static List<String> literalsAddress = new ArrayList<>();

	static List<String> symbols = new ArrayList<>();
	static List<String> symbolsAddress = new ArrayList<>();

	private static void readInput() {
		int ipCharBuff;
		try {
			FileInputStream ip = new FileInputStream("./inputs/input.txt");
			while ((ipCharBuff = ip.read()) != -1) {
				input += (char) ipCharBuff;
			}
			ip.close();
		} catch (Exception e) {
			System.out.println("Error" + e);
			System.exit(0);
		}
	}

	private static void tokenizeInput() {
		int k = 0;
		String temp = "";
		List<String> tempToken = new ArrayList<>();

		for (int i = 0; i < input.length(); i++) {
			char j = input.charAt(i);

			if (((j >= 'a' && j <= 'z') || (j >= 'A' && j <= 'Z') || (j >= '0' && j <= '9') || j == '=' || j == '\'')) {
				temp += j;
				k++;
			} else if ((char) j == '\n') {
				if (k != 0) {
					tempToken.add(temp);
					temp = "";
					k = 0;

				}

				if (tempToken.size() != 0) {
					inputTokens.add(new ArrayList<>(tempToken));
					// empty temp token
					tempToken.clear();
				}

			} else {
				if (k != 0) {
					// if current temp is not empty
					tempToken.add(temp);
					temp = "";
					k = 0;
				}
			}
		}
	}

	private static void printTokens() {
		for (int i = 0; i < inputTokens.size(); i++) {
			for (int j = 0; j < inputTokens.get(i).size(); j++) {
				System.out.print(inputTokens.get(i).get(j) + ' ');

			}
			System.out.print('\n');
		}
	}

	private static int checkSymbol(String symbol) {
		int pos = -1;

		for (int i = 0; i < symbols.size(); i++) {
			if (symbols.get(i).equals(symbol)) {
				pos = i;
			}
		}

		return pos;
	}

	private static void intermidiateCoder() {
		int lc = Integer.parseInt(inputTokens.get(0).get(1));
		// start
		output = String.format("(AD,01)\t(C,%s)\t\n", lc);

		// read each line from input
		for (int i = 1; i < inputTokens.size(); i++) {

			String x = inputTokens.get(i).get(0).toUpperCase();
			// System.out.println(x + ".");

			// LTORG or end
			if (x.equals("LTORG") || x.equals("END")) {
				// handle ltorg
				for (int j = 0; j < literalsAddress.size(); j++) {
					if (literalsAddress.get(j).charAt(0) == '.') {
						// address not allocated allocate it
						// Extract number from literal
						String num = "";
						for (int k = 2; k < literals.get(j).length(); k++) {
							if (literals.get(j).charAt(k) == '\'') {
								break;
							}
							num += literals.get(j).charAt(k);
							lc++;

						}
						output += String.format("(AD,05)\t(DL,02)\t(C,%s)\n", Integer.valueOf(num));
						literalsAddress.set(j, String.valueOf(lc));
					}
				}

				

				if (x.equals("END")) {
					output += "(AD,02)\t";
					return;
				}
			} else if (x.equals("ADD") || x.equals("SUB") || x.equals("MULT") || x.equals("MOVER")
					|| x.equals("MOVEM")) {
				// handle ltorg here
				String code = "";
				if (x.equals("ADD")) {
					code = "01";
				} else if (x.equals("SUB")) {
					code = "02";
				} else if (x.equals("MULT")) {
					code = "03";
				} else if (x.equals("MOVER")) {
					code = "04";
				} else if (x.equals("MOVEM")) {
					code = "05";
				}
				// add ad to output
				output += String.format("(IS,%s)\t", code);

				// handle second token
				x = inputTokens.get(i).get(1).toUpperCase();
				if (x.equals("AREG") || x.equals("BREG") || x.equals("CREG") || x.equals("DREG")) {
					// REGISTERS
					code = "";
					if (x.equals("AREG")) {
						code = "01";
					} else if (x.equals("BREG")) {
						code = "02";
					} else if (x.equals("CREG")) {
						code = "03";
					} else if (x.equals("DREG")) {
						code = "04";
					}
					output += String.format("(RG,%s)\t", code);
				}

				// handle third token
				x = inputTokens.get(i).get(2).toUpperCase();
				if (x.charAt(0) == '=') {
					// literal
					literals.add(x);
					literalsAddress.add(".");
					output += String.format("(L,%s)\t", literals.size() - 1);
				} else {
					// symbol
					int pos;
					if (checkSymbol(x) == -1) {
						symbols.add(x);
						symbolsAddress.add(".");
						pos = symbols.size() - 1;
					} else {
						pos = checkSymbol(x);
					}
					output += String.format("(S,%s)\t", pos);
				}

				lc++;

			} else if (x.equals("DS") || x.equals("DC")) {
				// declarative
				String code;
				if (x.equals("DS")) {
					code = "01";
				} else {
					code = "02";
				}
				output += String.format("(DS,%s)\t(C,%s)\t", code, inputTokens.get(i).get(1));
				lc++;

			} else {
				// label
				int pos;
				if (checkSymbol(x) == -1) {
					symbols.add(x);
					symbolsAddress.add(String.valueOf(lc));
					pos = symbols.size() - 1;
				} else {
					pos = checkSymbol(x);
					symbolsAddress.set(pos, String.valueOf(lc));
				}

				output += String.format("(S,%s)\t", pos);

				// continue same line again
				inputTokens.get(i).remove(0);
				i--;
				continue;
			}

			// output += String.format("\t\t%s", lc);

			output += '\n';
		}
	}

	private static void saveOutput() {
		try {
			FileOutputStream out = new FileOutputStream("./outputs/out.txt");
			out.write(output.getBytes());
			out.close();

		} catch (Exception e) {

		}
	}

	public static void main(String[] args) {
		readInput();
		tokenizeInput();
		printTokens();
		intermidiateCoder();
		saveOutput();

		System.out.println(output.toString());

		System.out.println(symbols.toString());
		System.out.println(symbolsAddress.toString());

		System.out.println(literals.toString());
		System.out.println(literalsAddress.toString());
	}

}