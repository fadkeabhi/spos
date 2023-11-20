import java.util.Scanner;

class BestFit {
    void bestFit(int blockSize[], int m, int processSize[], int n) {
        int allocation[] = new int[n];
        for (int i = 0; i < allocation.length; i++)
            allocation[i] = -1;

        for (int i = 0; i < n; i++) {
            int bestFitIndex = -1;
            for (int j = 0; j < m; j++) {
                if (blockSize[j] >= processSize[i]) {
                    if (bestFitIndex == -1 || blockSize[j] < blockSize[bestFitIndex]) {
                        bestFitIndex = j;
                    }
                }
            }
            if (bestFitIndex != -1) {
                allocation[i] = bestFitIndex;
                blockSize[bestFitIndex] -= processSize[i];
            }
        }

        System.out.println("\nProcess No.   Process Size   Block no.\n");
        for (int i = 0; i < n; i++) {
            System.out.print("   " + (i + 1) + "          " + processSize[i] + "          ");
            if (allocation[i] != -1)
                System.out.print(allocation[i] + 1);
            else
                System.out.print("Not Allocated");
            System.out.println();
        }
    }
}

class NextFit {
    void nextFit(int blockSize[], int m, int processSize[], int n) {
        int allocation[] = new int[n];
        for (int i = 0; i < allocation.length; i++)
            allocation[i] = -1;

        int j = 0;  // Initialize block index to the first block
        for (int i = 0; i < n; i++) {
            while (j < m) {
                if (blockSize[j] >= processSize[i]) {
                    allocation[i] = j;
                    blockSize[j] -= processSize[i];
                    break;
                }
                j = (j + 1) % m;  // Move to the next block in a circular fashion
            }
        }

        System.out.println("\nProcess No.   Process Size   Block no.\n");
        for (int i = 0; i < n; i++) {
            System.out.print("   " + (i + 1) + "          " + processSize[i] + "          ");
            if (allocation[i] != -1)
                System.out.print(allocation[i] + 1);
            else
                System.out.print("Not Allocated");
            System.out.println();
        }
    }
}


public class Best_Next_Fit {
    public static void main(String[] args) {
       BestFit best = new BestFit();
        NextFit next = new  NextFit();
        Scanner sc = new Scanner(System.in);

        while (true) {
            int choice;
            System.out.println();
            System.out.println("Enter the number of Blocks: ");
            int m = sc.nextInt();
            System.out.println("Enter the number of Processes: ");
            int n = sc.nextInt();
            int blockSize[] = new int[m];
            int processSize[] = new int[n];
            System.out.println("Enter the Size of all the blocks: ");
            for (int i = 0; i < m; i++) {
                blockSize[i] = sc.nextInt();
            }
            System.out.println("Enter the size of all processes: ");
            for (int i = 0; i < n; i++) {
                processSize[i] = sc.nextInt();
            }
            System.out.println();
            System.out.println("Menu");
            System.out.println("1. Best Fit ");
            System.out.println("2. Next Fit");
            System.out.println("3. Exit");
            System.out.println("Select the algorithm you want to implement: ");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Best Fit Output");
                    best.bestFit(blockSize, m, processSize, n);
                    break;
                case 2:
                    System.out.println("Next Fit Output");
                    next.nextFit(blockSize, m, processSize, n);
                    break;
                case 3:
                    System.out.println("Exiting the code...");
                    return;
                default:
                    System.out.println("Invalid option");
            }
        }
    }
}
