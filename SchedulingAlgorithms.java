import java.util.Scanner;

class Process {
    public int id, at, bt, wt, tat, rt, st, ct, rest, priority;
}

public class SchedulingAlgorithms {
    public static void main(String[] args) {
        SchedulingAlgorithms obj = new SchedulingAlgorithms();
        Scanner sc = new Scanner(System.in);
        int nop;

        System.out.println("Enter the number of processes: ");
        nop = sc.nextInt();
        Process pro[] = new Process[nop];

        for (int i = 0; i < nop; i++) {
            int at, bt;
            System.out.print("Arrival time of process " + i + ": ");
            at = sc.nextInt();
            System.out.print("Burst time of process " + i + ": ");
            bt = sc.nextInt();
            pro[i] = new Process();
            pro[i].id = i;
            pro[i].at = at;
            pro[i].bt = bt;
            pro[i].rt = bt;
        }

        int choice;
        do {
            System.out.println("\nMenu");
            System.out.println("1. FCFS");
            System.out.println("2. Round Robin");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    obj.fcfs(nop, pro);
                    break;
                case 2:
                    System.out.print("Enter time quantum for Round Robin: ");
                    int quantum = sc.nextInt();
                    obj.roundRobin(nop, pro, quantum);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        } while (choice != 3);
        sc.close();
    }

    void fcfs(int nop, Process pro[]) {
        int time = 0;
        for (int i = 0; i < nop - 1; i++) {
            for (int j = i + 1; j < nop; j++) {
                if (pro[i].at > pro[j].at) {
                    Process temp = pro[i];
                    pro[i] = pro[j];
                    pro[j] = temp;
                }
                if (pro[i].at == pro[j].at) {
                    if (pro[i].bt > pro[j].bt) {
                        Process temp = pro[i];
                        pro[i] = pro[j];
                        pro[j] = temp;
                    }
                }
            }
        }

        for (int i = 0; i < nop; i++) {
            while (pro[i].at > time) {
                time++;
            }
            pro[i].st = time;
            time += pro[i].bt;
            pro[i].ct = time;
            pro[i].wt = pro[i].st - pro[i].at;
            pro[i].tat = pro[i].wt + pro[i].bt;
            pro[i].rest = pro[i].wt;
        }

        print(nop, pro);
    }

    void roundRobin(int nop, Process pro[], int quantum) {
        int rem_bt[] = new int[nop];
        for (int i = 0; i < nop; i++) {
            rem_bt[i] = pro[i].bt;
        }
        int t = 0;

        while (true) {
            boolean done = true;
            for (int i = 0; i < nop; i++) {
                if (rem_bt[i] > 0) {
                    done = false;
                    if (rem_bt[i] > quantum) {
                        t += quantum;
                        rem_bt[i] -= quantum;
                    } else {
                        t = t + rem_bt[i];
                        pro[i].ct = t;
                        rem_bt[i] = 0;
                    }
                }
            }
            if (done) {
                break;
            }
        }

        for (int i = 0; i < nop; i++) {
            pro[i].tat = pro[i].ct - pro[i].at;
            pro[i].wt = pro[i].tat - pro[i].bt;
        }

        print(nop, pro);
    }

    static void print(int nop, Process pro[]) {
        Process sum = new Process();
        System.out.println(" id at bt wt tat");
        for (int i = 0; i < nop; i++) {
            System.out.println(" " + pro[i].id + " " + pro[i].at + " " + pro[i].bt + " " + pro[i].wt + " " + pro[i].tat + " ");
            sum.wt += pro[i].wt;
        }
        System.out.println("Average wait time = " + (sum.wt / nop));
    }
}
