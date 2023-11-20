import java.util.*;

class Process {
    int pid;
    int arrivalTime;
    int burstTime;
    int remainingTime;

    Process(int pid, int arrivalTime, int burstTime) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
    }
}

public class Scheduler {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Process> processes = new ArrayList<>();
        int choice;

        do {
            System.out.println("Select an algorithm:");
            System.out.println("1. First Come First Serve (FCFS)");
            System.out.println("2. Shortest Job First (SJF) - Preemptive");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    FCFS(processes);
                    break;
                case 2:
                    SJFPreemptive(processes);
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

    public static void FCFS(ArrayList<Process> processes) {
        Collections.sort(processes, Comparator.comparingInt(p -> p.arrivalTime));

        int currentTime = 0;
        double totalWaitingTime = 0;

        System.out.println("\nFCFS Scheduling:");
        for (Process p : processes) {
            if (currentTime < p.arrivalTime)
                currentTime = p.arrivalTime;

            System.out.println("Process " + p.pid + " starts at time " + currentTime);
            totalWaitingTime += currentTime - p.arrivalTime;
            currentTime += p.burstTime;
        }

        double averageWaitingTime = totalWaitingTime / processes.size();
        System.out.println("Average Waiting Time: " + averageWaitingTime);
    }

    public static void SJFPreemptive(ArrayList<Process> processes) {
        PriorityQueue<Process> queue = new PriorityQueue<>(Comparator.comparingInt(p -> p.remainingTime));
        ArrayList<Process> executedProcesses = new ArrayList<>();
        int currentTime = 0;
        double totalWaitingTime = 0;

        for (Process p : processes) {
            if (p.arrivalTime == currentTime) {
                queue.add(p);
            }
        }

        System.out.println("\nSJF Preemptive Scheduling:");
        while (!queue.isEmpty()) {
            Process currentProcess = queue.poll();
            assert currentProcess != null;
            System.out.println("Process " + currentProcess.pid + " starts at time " + currentTime);
            totalWaitingTime += currentTime - currentProcess.arrivalTime;
            currentTime++;
            currentProcess.remainingTime--;

            if (currentProcess.remainingTime > 0) {
                executedProcesses.add(currentProcess);
                for (Process p : processes) {
                    if (p.arrivalTime <= currentTime && !executedProcesses.contains(p)) {
                        queue.add(p);
                    }
                }
                queue.add(currentProcess);
            }
        }

        double averageWaitingTime = totalWaitingTime / processes.size();
        System.out.println("Average Waiting Time: " + averageWaitingTime);
    }
}
