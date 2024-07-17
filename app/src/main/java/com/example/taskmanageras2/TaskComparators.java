package com.example.taskmanageras2;
import java.util.Comparator;

public class TaskComparators {

    public static Comparator<Task> dueDateAscending = new Comparator<Task>() {
        @Override
        public int compare(Task t1, Task t2) {
            return t1.getDueDate().compareTo(t2.getDueDate());
        }
    };

    public static Comparator<Task> dueDateDescending = new Comparator<Task>() {
        @Override
        public int compare(Task t1, Task t2) {
            return t2.getDueDate().compareTo(t1.getDueDate());
        }
    };
    public static Comparator<Task> priorityComparator = new Comparator<Task>() {
        @Override
        public int compare(Task t1, Task t2) {
            return getPriorityValue(t1.getPriority()) - getPriorityValue(t2.getPriority());
        }

        private int getPriorityValue(String priority) {
            switch (priority.toLowerCase()) {
                case "low":
                    return 1;
                case "medium":
                    return 2;
                case "high":
                    return 3;
                default:
                    return 0; // Default case, should not happen with valid priority strings
            }
        }
    };
}
