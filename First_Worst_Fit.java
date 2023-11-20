import java.util.Scanner;

public class First_Worst_Fit {
    public static void firstFit(int[] blockSize, int m, int[] processSize, int n) {
        int[] allocation = new int[n];
        for (int i = 0; i < n; i++) {
            allocation[i] = -1;
            for (int j = 0; j < m; j++) {
                if (blockSize[j] >= processSize[i]) {
                    allocation[i] = j;
                    blockSize[j] -= processSize[i];
                    break;
                }
            }
        }
        System.out.println("First Fit Memory Allocation:");
        printMemoryAllocationTable(allocation, processSize, n);
    }

    public static void worstFit(int[] blockSize, int m, int[] processSize, int n) {
        int[] allocation = new int[n];
        for (int i = 0; i < n; i++) {
            allocation[i] = -1;
            int worstFitIndex = -1;
            for (int j = 0; j < m; j++) {
                if (blockSize[j] >= processSize[i]) {
                    if (worstFitIndex == -1 || blockSize[j] > blockSize[worstFitIndex]) {
                        worstFitIndex = j;
                    }
                }
            }

            if (worstFitIndex != -1) {
                allocation[i] = worstFitIndex;
                blockSize[worstFitIndex] -= processSize[i];
            }
        }
        System.out.println("Worst Fit Memory Allocation:");
        printMemoryAllocationTable(allocation, processSize, n);
    }

    public static void printMemoryAllocationTable(int[] allocation, int[] processSize, int n) {
        System.out.println(" Process No. Process Size      Block no.");
        for (int i = 0; i < n; i++) {
            System.out.print(" " + (i + 1) + "         " + processSize[i] + "         ");
            if (allocation[i] != -1) {
                System.out.println(allocation[i] + 1);
            } else {
                System.out.println("Not Allocated");
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] blockSize = {100, 500, 200, 300, 600};
        int[] processSize = {212, 417, 112, 426};
        int m = blockSize.length;
        int n = processSize.length;

        while (true) {
            System.out.println("Menu:");
            System.out.println("1. First Fit Memory Allocation");
            System.out.println("2. Worst Fit Memory Allocation");
            System.out.println("3. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    firstFit(blockSize.clone(), m, processSize.clone(), n);
                    break;
                case 2:
                    worstFit(blockSize.clone(), m, processSize.clone(), n);
                    break;
                case 3:
                    System.out.println("Exiting program. Bye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}
