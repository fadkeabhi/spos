import java.util.Scanner;

class process {
    int number, burst, arrival, start, completion, waiting, remaining;

    public static void print(int n, process[] p) {
        System.out.println("process No.\tarrival time\tBurst time\tStart Time\tCompletion Time\twaiting Time");
        for (int i = 0; i < n; i++) {
            System.out.println(String.format("%s\t\t%s\t\t%s\t\t%s\t\t%s\t\t%s", p[i].number, p[i].arrival, p[i].burst,
                    p[i].start, p[i].completion, p[i].waiting));

        }

    }
}

public class Prog {
    public static void main(String[] args) {
        rr();

    }

    private static void fcfs() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter no of processes : ");
        int n = sc.nextInt();
        process p[] = new process[n];
        for (int i = 0; i < n; i++) {
            p[i] = new process();
            p[i].number = i;
            System.out.print("Enter arrival time : ");
            p[i].arrival = sc.nextInt();
            System.out.print("Enter burst time : ");
            p[i].burst = sc.nextInt();

            p[i].waiting = 0;
            p[i].completion = 0;
            p[i].start = 0;
        }

        // sort according to arrival time
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (p[j].arrival < p[i].arrival) {
                    // swap
                    process temp = p[i];
                    p[i] = p[j];
                    p[j] = temp;
                }
            }
        }

        int time = 0;
        for (int i = 0; i < n; i++) {
            while (p[i].arrival > time) {
                time++;
            }
            p[i].start = time;
            time += p[i].burst;
            p[i].completion = time;
            p[i].waiting = p[i].start - p[i].arrival;
        }

        process.print(n, p);

    }

    private static void rr() {

        String processId = "";
        String remTime = "";

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter time quantum: ");
        int quantum = sc.nextInt();
        System.out.print("Enter no of processes : ");
        int n = sc.nextInt();
        process p[] = new process[n];
        for (int i = 0; i < n; i++) {
            p[i] = new process();
            p[i].number = i;
            System.out.print("Enter arrival time : ");
            p[i].arrival = sc.nextInt();
            System.out.print("Enter burst time : ");
            p[i].burst = sc.nextInt();

            p[i].waiting = 0;
            p[i].completion = 0;
            p[i].start = 0;
            p[i].remaining = p[i].burst;
        }

        // sort according to arrival time
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (p[j].arrival < p[i].arrival) {
                    // swap
                    process temp = p[i];
                    p[i] = p[j];
                    p[j] = temp;
                }
            }
        }

        int time = 0;

        // Time quatom counter
        int tc = 0;
        // Process pointer
        int pp = 0;

        int emptyTimeCounter = 0;

        int completeCount = 0;
        while (true) {
            if(completeCount == n){
                break;
            }
            if (tc == quantum) {
                tc = 0;
                pp = (pp + 1) % quantum;
            }
            if(emptyTimeCounter == n){
                emptyTimeCounter = 0;
                time++;
            }

            if (p[pp].arrival <= time && p[pp].remaining != 0) {
                // if process is just started
                if (p[pp].remaining == p[pp].burst) {
                    p[pp].start = time;
                }
                
                emptyTimeCounter = 0;
                p[pp].remaining--;
                tc++;
                time++;

                processId += String.format("%s ",p[pp].number);
                remTime += String.format("%s ",p[pp].remaining);

                // if process complted
                if (p[pp].remaining == 0) {
                    p[pp].completion = time;
                    completeCount++;
                }
            } else {
                pp = (pp + 1) % n;
                emptyTimeCounter++;
            }
        }

        // calculate waiting time
        for(int i = 0; i<n;i++){
            p[i].waiting = (p[i].completion - p[i].arrival) - p[i].burst;
        }

        System.out.println(processId);
        System.out.println(remTime);

        process.print(n, p);

    }

}
