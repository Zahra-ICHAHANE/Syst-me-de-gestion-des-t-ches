import TaskManager.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import java.util.Scanner;

public class MainClient {
    public static void main(String[] args) {
        try {
            // Initialisation du contexte JNDI
            InitialContext context = new InitialContext();

            // Lookup du service
            TaskService taskService = TaskServiceHelper.narrow((org.omg.CORBA.Object) context.lookup("TaskService"));

            Scanner scanner = new Scanner(System.in);
            boolean running = true;

            while (running) {
                System.out.println("\nMenu:");
                System.out.println("1. Ajouter une tâche");
                System.out.println("2. Lister les tâches");
                System.out.println("3. Mettre à jour le statut d'une tâche");
                System.out.println("4. Supprimer une tâche");
                System.out.println("0. Quitter");
                System.out.print("Choisissez une option: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1: // Ajouter une tâche
                        Task newTask = new Task();
                        System.out.print("Entrez l'ID de la tâche: ");
                        newTask.id = scanner.next();
                        scanner.nextLine();
                        System.out.print("Entrez le titre: ");
                        newTask.title = scanner.nextLine();
                        System.out.print("Entrez la description: ");
                        newTask.description = scanner.nextLine();
                        System.out.print("Entrez la date d'échéance (YYYY-MM-DD): ");
                        newTask.dueDate = scanner.nextLine();
                        newTask.status = TaskStatus.NOT_STARTED;
                        System.out.print("Affecter à (utilisateur): ");
                        newTask.assignedTo = scanner.nextLine();

                        taskService.addTask(newTask);
                        System.out.println("Tâche ajoutée avec succès.");
                        break;

                    case 2: // Lister les tâches
                        Task[] tasks = taskService.getTasks();
                        for (Task task : tasks) {
                            System.out.println("Id: " + task.id + ", Tâche: " + task.title + ", Statut: " + task.status.value() + ", Assignée à: " + task.assignedTo + ", Date d'échéance: " + task.dueDate + ", Description: " + task.description);
                        }
                        break;

                    case 3: // Mettre à jour le statut d'une tâche
                        System.out.print("Entrez l'ID de la tâche à mettre à jour: ");
                        String idToUpdate = scanner.next();
                        scanner.nextLine();

                        // Demander à l'utilisateur ce qu'il souhaite mettre à jour
                        System.out.println("Que souhaitez-vous mettre à jour ?");
                        System.out.println("1. Titre");
                        System.out.println("2. Date d'échéance");
                        System.out.println("3. Utilisateur assigné");
                        System.out.println("4. Statut");
                        System.out.print("Choisissez une option: ");
                        int updateChoice = scanner.nextInt();
                        scanner.nextLine();

                        switch (updateChoice) {
                            case 1: // Mise à jour du titre
                                System.out.print("Entrez le nouveau titre: ");
                                String newTitle = scanner.nextLine();
                                for (Task task : taskService.getTasks()) {
                                    if (task.id.equals(idToUpdate)) {
                                        task.title = newTitle;
                                        taskService.updateTask(task);
                                        break;
                                    }
                                }
                                System.out.println("Titre mis à jour.");
                                break;

                            case 2: // Mise à jour de la date d'échéance
                                System.out.print("Entrez la nouvelle date d'échéance (YYYY-MM-DD): ");
                                String newDueDate = scanner.nextLine();
                                for (Task task : taskService.getTasks()) {
                                    if (task.id.equals(idToUpdate)) {
                                        task.dueDate = newDueDate;
                                        taskService.updateTask(task);
                                        break;
                                    }
                                }
                                System.out.println("Date d'échéance mise à jour.");
                                break;

                            case 3: // Mise à jour de l'utilisateur assigné
                                System.out.print("Entrez le nouvel utilisateur assigné: ");
                                String newAssignedTo = scanner.nextLine();
                                for (Task task : taskService.getTasks()) {
                                    if (task.id.equals(idToUpdate)) {
                                        task.assignedTo = newAssignedTo;
                                        taskService.updateTask(task);
                                        break;
                                    }
                                }
                                System.out.println("Utilisateur assigné mis à jour.");
                                break;

                            case 4: // Mise à jour du statut
                                System.out.print("Nouveau statut (0: NOT_STARTED, 1: IN_PROGRESS, 2: COMPLETED): ");
                                int newStatusIndex = scanner.nextInt();
                                TaskStatus newStatus;
                                switch (newStatusIndex) {
                                    case 0:
                                        newStatus = TaskStatus.NOT_STARTED;
                                        break;
                                    case 1:
                                        newStatus = TaskStatus.IN_PROGRESS;
                                        break;
                                    case 2:
                                        newStatus = TaskStatus.COMPLETED;
                                        break;
                                    default:
                                        System.out.println("Statut non valide. Aucune mise à jour effectuée.");
                                        continue;
                                }
                                taskService.updateTaskStatus(idToUpdate, newStatus);
                                System.out.println("Statut mis à jour.");
                                break;

                            default:
                                System.out.println("Option non valide.");
                        }
                        break;

                    case 4: // Supprimer une tâche
                        System.out.print("Entrez l'ID de la tâche à supprimer: ");
                        String idToRemove = scanner.next();
                        taskService.removeTask(idToRemove);
                        System.out.println("Tâche supprimée.");
                        break;

                    case 0: // Quitter
                        running = false;
                        break;

                    default:
                        System.out.println("Option non valide.");
                }
            }

            scanner.close();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
}