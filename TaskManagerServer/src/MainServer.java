import TaskManager.TaskServiceImpl;
import org.omg.CORBA.*;
import org.omg.CosNaming.*;
import org.omg.PortableServer.*;

public class MainServer {
    public static void main(String[] args) {
        try {
            // Initialisation de l'ORB
            ORB orb = ORB.init(args, null);

            // Création de la POA
            POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootPOA.the_POAManager().activate();

            // Création et enregistrement du service de tâches
            TaskServiceImpl taskService = new TaskServiceImpl();
            org.omg.CORBA.Object ref = rootPOA.servant_to_reference(taskService);

            // Enregistrement dans le Naming Context
            NamingContext namingContext = NamingContextHelper.narrow(orb.resolve_initial_references("NameService"));

            // Création du tableau de NameComponent
            NameComponent[] nameComponents = new NameComponent[1];
            nameComponents[0] = new NameComponent("TaskService", ""); // Nom et type

            namingContext.rebind(nameComponents, ref);
            System.out.println("Le service de gestion des tâches a été enregistré avec succès sous le nom 'TaskService'.");
            // Boucle principale de l'ORB
            orb.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}