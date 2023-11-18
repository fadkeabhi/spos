// 1. Last In First Out (LIFO) 2. Optimal algorithm

import java.util.Scanner;

class Algo2 {

    int[] frame;

    void printFrame() {
        System.out.print("Frame\t");
        for (int i = 0; i < frame.length; i++) {
            System.out.print(frame[i] + " ");
        }
        System.out.println();
    }

    // checks if given page is present in frame
    int isHit(int page) {
        for (int i = 0; i < frame.length; i++) {
            if (frame[i] == page) {
                return i;
            }
        }
        return -1;
    }

    // used to return location of empty frame
    int getEmptyFrame() {
        for (int i = 0; i < frame.length; i++) {
            if (frame[i] == -1) {
                return i;
            }
        }
        return -1;
    }

    public void lifo(int[] pages, int n, int frame_size) {
        int hits = 0;
        int faults = 0;
        frame = new int[frame_size];
        int latest_ptr = -1;

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
                if (getEmptyFrame() != -1) {
                    // An empty frame is available
                    frame[getEmptyFrame()] = pages[i];
                    latest_ptr = i;
                } else {
                    // No empty frame available
                    frame[latest_ptr] = pages[i];
                }

            }
        }

        System.out.println(String.format("No. of page fault=%s, No of page hit=%s", faults, hits));
    }

    int getOptimalReplacement(int pos, int[] pages) {

        int useAfter[] = new int[frame.length];
        for (int i = 0; i < frame.length; i++) {
            useAfter[i] = Integer.MAX_VALUE;
            for (int j = pos; j < pages.length; j++) {
                if(pages[j] == frame[i]){
                    useAfter[i] = j;
                    break;
                }
            }
        }

        // now return index of max use after
        int ret = 0;
        for(int i=1;i<useAfter.length;i++){
            if(useAfter[i] > useAfter[ret]){
                ret = i;
            }
        }

        return ret;

    }

    void optimal(int[] pages, int n, int frame_size) {
        int hits = 0;
        int faults = 0;
        frame = new int[frame_size];

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
                if (getEmptyFrame() != -1) {
                    // An empty frame is available
                    frame[getEmptyFrame()] = pages[i];
                } else {
                    // No empty frame available
                    frame[getOptimalReplacement(i, pages)] = pages[i];

                }

            }
        }

        System.out.println(String.format("No. of page fault=%s, No of page hit=%s", faults, hits));
    }

}

public class Lifo_opt {
    public static void main(String[] args) {

        // testing
        // int pages[] = { 2, 3, 4, 2, 1, 3, 7, 5, 4, 6, 7, 7, 8, 4, 6, 2, 5, 1, 4, 3 };


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


        Algo2 f = new Algo2();
        f.lifo(pages, pages.length, frame_size);
        f.optimal(pages, pages.length, frame_size);

    }
}
