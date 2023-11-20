import java.util.*;

public class PageReplacement {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Integer> pageFrames = new ArrayList<>();
        int choice;

        do {
            System.out.println("Select a page replacement algorithm:");
            System.out.println("1. Last In First Out (LIFO)");
            System.out.println("2. Optimal");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    LIFO(pageFrames);
                    break;
                case 2:
                    Optimal(pageFrames);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice! Please enter again.");
                    break;
            }
        } while (choice != 3);
        scanner.close();
    }

    public static void LIFO(ArrayList<Integer> pageFrames) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of page frames: ");
        int capacity = scanner.nextInt();
        int pageFaults = 0;

        Stack<Integer> stack = new Stack<>();
        System.out.print("Enter the sequence of page references (separated by spaces): ");
        String[] pageReferences = scanner.next().split(" ");

        for (String pageRef : pageReferences) {
            int page = Integer.parseInt(pageRef);

            if (!pageFrames.contains(page)) {
                pageFaults++;
                if (pageFrames.size() == capacity) {
                    int removedPage = stack.pop();
                    pageFrames.remove(Integer.valueOf(removedPage));
                }
                pageFrames.add(page);
                stack.push(page);
            }
        }

        System.out.println("Page Faults using LIFO: " + pageFaults);
        scanner.close();
    }

    public static void Optimal(ArrayList<Integer> pageFrames) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of page frames: ");
        int capacity = scanner.nextInt();
        int pageFaults = 0;

        System.out.print("Enter the sequence of page references (separated by spaces): ");
        String[] pageReferences = scanner.next().split(" ");

        ArrayList<Integer> pages = new ArrayList<>();
        for (String pageRef : pageReferences) {
            int page = Integer.parseInt(pageRef);
            pages.add(page);
        }

        for (int i = 0; i < pages.size(); i++) {
            int page = pages.get(i);

            if (!pageFrames.contains(page)) {
                pageFaults++;
                if (pageFrames.size() == capacity) {
                    int farthest = i;
                    int farthestPage = -1;
                    for (int j = 0; j < pageFrames.size(); j++) {
                        int nextPage = pageFrames.get(j);
                        int index = pages.subList(i, pages.size()).indexOf(nextPage);
                        if (index == -1) {
                            farthestPage = nextPage;
                            break;
                        }
                        if (index > farthest) {
                            farthest = index;
                            farthestPage = nextPage;
                        }
                    }
                    pageFrames.remove(Integer.valueOf(farthestPage));
                }
                if (pageFrames.size() < capacity) {
                    pageFrames.add(page);
                }
            }
        }

        System.out.println("Page Faults using Optimal: " + pageFaults);
        scanner.close();
    }
}
