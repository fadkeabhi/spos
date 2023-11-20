import java.util.Scanner;

class Process {
    public int id, at, bt, wt, tat, st, ct, rest, priority;
}

public class SJF_Priority_Scheduling {
    public static void main(String[] args) {
        SJF_Priority_Scheduling obj = new SJF_Priority_Scheduling();
        Scanner sc = new Scanner(System.in);
        int nop;
        System.out.println("Enter the number of processes");
        nop = sc.nextInt();
        Process pro[] = new Process[nop];

        for (int i = 0; i < nop; i++) {
            int at, bt, priority;
            System.out.print("Arrival time of process " + i + ": ");
            at = sc.nextInt();
            System.out.print("Burst time of process " + i + ": ");
            bt = sc.nextInt();
            System.out.print("Priority of process " + i + ": ");
            priority = sc.nextInt();
            pro[i] = new Process();
            pro[i].id = i;
            pro[i].at = at;
            pro[i].bt = bt;
            pro[i].priority = priority;
        }

        int choice;
        do {
            System.out.println("\nMenu");
            System.out.println("1. SJF (Shortest Job First)");
            System.out.println("2. Priority Scheduling");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    obj.sjf(nop, pro);
                    break;
                case 2:
                    obj.priorityScheduling(nop, pro);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        } while (choice != 3);
    }

    void sjf(int nop, Process pro[]) {
        for (int i = 0; i < nop - 1; i++) {
            for (int j = i + 1; j < nop; j++) {
                if (pro[i].bt > pro[j].bt) {
                    Process temp = pro[i];
                    pro[i] = pro[j];
                    pro[j] = temp;
                }
            }
        }

        int time = 0;
        for (int i = 0; i < nop; i++) {
            while (pro[i].at > time) {
                time++;
            }
            pro[i].st = time;
            time += pro[i].bt;
            pro[i].ct = time;
            pro[i].tat = pro[i].ct - pro[i].at;
            pro[i].wt = pro[i].tat - pro[i].bt;
        }

        print(nop, pro, "SJF (Shortest Job First)");
    }

    void priorityScheduling(int nop, Process pro[]) {
        for (int i = 0; i < nop - 1; i++) {
            for (int j = i + 1; j < nop; j++) {
                if (pro[i].priority > pro[j].priority) {
                    Process temp = pro[i];
                    pro[i] = pro[j];
                    pro[j] = temp;
                }
            }
        }

        int time = 0;
        for (int i = 0; i < nop; i++) {
            while (pro[i].at > time) {
                time++;
            }
            pro[i].st = time;
            time += pro[i].bt;
            pro[i].ct = time;
            pro[i].tat = pro[i].ct - pro[i].at;
            pro[i].wt = pro[i].tat - pro[i].bt;
        }

        print(nop, pro, "Priority Scheduling");
    }

    static void print(int nop, Process pro[], String algorithmName) {
        Process sum = new Process();
        System.out.println("\n" + algorithmName + " Results:");
        System.out.println("ID\tArrival Time\tBurst Time\tWaiting Time\tTurnaround Time");
        for (int i = 0; i < nop; i++) {
            System.out.println(pro[i].id + "\t" + pro[i].at + "\t\t" + pro[i].bt + "\t\t" + pro[i].wt + "\t\t" + pro[i].tat);
            sum.wt += pro[i].wt;
        }
        System.out.println("Average Waiting Time = " + (sum.wt / nop));
    }
}
