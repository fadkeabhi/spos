// 1. First In Last Out (FIFO) 2. Least Recently Used (LRU)

import java.util.Scanner;

class Algo {

    int[] frame;
    int[] recentUsed;

    void printFrame() {
        System.out.print("Frame\t");
        for (int i = 0; i < frame.length; i++) {
            System.out.print(frame[i] + " ");
        }
        System.out.println();
    }

    void printRecent() {
        System.out.print("recent\t");
        for (int i = 0; i < recentUsed.length; i++) {
            System.out.print(recentUsed[i] + " ");
        }
        System.out.println();
    }

    int isHit(int page) {

        for (int i = 0; i < frame.length; i++) {
            if (frame[i] == page) {
                return i;
            }
        }
        return -1;
    }

    public void fifo(int[] pages, int n, int frame_size) {
        int hits = 0;
        int faults = 0;
        frame = new int[frame_size];
        int frame_ptr = -1;

        // set all to -1
        for (int i = 0; i < frame_size; i++) {
            frame[i] = -1;
        }

        for (int i = 0; i < n; i++) {
            if (isHit(pages[i]) != -1) {
                // page found
                hits++;

            } else {
                // page not found
                faults++;
                frame_ptr = (frame_ptr + 1) % frame_size;
                frame[frame_ptr] = pages[i];
            }
        }

        System.out.println(String.format("No. of page fault=%s, No of page hit=%s", faults, hits));
    }

    int leastrecentUsed() {
        int ret = 0;
        for (int i = 1; i < recentUsed.length; i++) {
            if (recentUsed[i] < recentUsed[ret]) {
                ret = i;
            }
        }
        return ret;
    }

    public void lru(int[] pages, int n, int frame_size) {
        int hits = 0;
        int faults = 0;
        frame = new int[frame_size];
        recentUsed = new int[frame_size];

        // set all to -1
        for (int i = 0; i < frame_size; i++) {
            frame[i] = -1;
            recentUsed[i] = -1;
        }

        for (int i = 0; i < n; i++) {
            if (isHit(pages[i]) != -1) {
                // page found
                hits++;
                recentUsed[isHit(pages[i])] = i;

            } else {
                // page not found
                faults++;
                frame[leastrecentUsed()] = pages[i];
                recentUsed[leastrecentUsed()] = i;
            }
            System.out.println("\n----------------");
            System.out.println("current page = " + pages[i]);
            printFrame();
            printRecent();
            System.out.println("----------------\n");
            // System.out.println(leastrecentUsed());
        }

        System.out.println(String.format("No. of page fault=%s, No of page hit=%s", faults, hits));
    }

}

public class Fifo_lru {
    public static void main(String[] args) {

        // testing
        // int pages[] = { 1, 2, 3, 1, 4, 1 };

        

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter no of pages: ");
        int n = sc.nextInt();
        int pages[] = new int[n];
        for (int i = 0; i < n; i++) {
            System.out.print("Enter page no " + i  + " : ");
            pages[i] = sc.nextInt();
        }
        System.out.print("Enter size of frame: ");
        int frame_size = sc.nextInt();

        Algo f = new Algo();
        f.fifo(pages, pages.length, frame_size);
        f.lru(pages, pages.length, frame_size);

    }
}
