
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;


public class JDBCTest {
	public static void main(String[] args) throws SQLException, Exception{
		try{
			System.out.println("Initialisation de la connexion");
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		}
		catch(ClassNotFoundException ex){
			System.out.println("Problème au chargement "+ex.toString());
		}
		try{
			Connection cx = DriverManager.getConnection("jdbc:mysql://localhost/employees?user=root&password=student");
			Statement etat = cx.createStatement();
			ResultSet rset = etat.executeQuery("Select SYSDATE()");
			while (rset.next()) System.out.println("Nous sommes le : "+rset.getString(1));
			System.out.println("JDBC correctement configuré");

			System.out.println("QUE VOULEZ VOUS FAIRE?");
			Scanner sc = new Scanner(System.in);
			int QUOI = sc.nextInt();

			if (QUOI==1){
				rset=etat.executeQuery("SELECT first_name,last_name FROM employees");
				System.out.println("les employés sont : ");
				while(rset.next())System.out.println(rset.getString(1)+" "+rset.getString(2));
			}


			if (QUOI==2){
				rset=etat.executeQuery("SELECT dept_name from departments");
				System.out.println("\nles departements sont : ");
				while(rset.next())System.out.println(rset.getString(1));
			}

			if (QUOI==3){
				rset=etat.executeQuery("SELECT dept_name,first_name,last_name,dept_manager.to_date from departments INNER JOIN employees INNER JOIN dept_manager WHERE(employees.emp_no=dept_manager.emp_no and dept_manager.dept_no=departments.dept_no)");
				System.out.println("\n les managers de departements");
				while(rset.next()){
					System.out.print(rset.getString(1)+" "+rset.getString(2)+" "+rset.getString(3));
					if (rset.getString(4).equals("9999-01-01"))System.out.print(" ANCIEN");
					System.out.print("\n");
				}
			}

			if (QUOI==4){
				Scanner scan = new Scanner(System.in);
				System.out.println("prenom : ");
				String PRENOM = scan.nextLine();


				System.out.println("nom : ");

				String NOM = scan.nextLine();

				rset=etat.executeQuery("SELECT first_name,last_name FROM employees WHERE(employees.first_name='"+PRENOM+"' and employees.last_name='"+NOM+"')" );
				if (rset.next()) System.out.println("Cette employé existe bien");
				else System.out.println("404 employe not found");
				scan.close();
			}

			if (QUOI==5){
				boolean bool=true;
				
					System.out.println("ajout ou suppression ? ");
					Scanner scan = new Scanner(System.in);
					String choix=scan.nextLine();
					if (choix.equals("ajout")){
						Scanner scan2 = new Scanner(System.in);
						bool=false;
						int emp_no;
						rset=etat.executeQuery("SELECT MAX(emp_no) FROM employees");
						rset.next();
						emp_no=rset.getInt(1)+1;
						System.out.println("date de naissance AAAA-MM-JJ :");
						String birth_date=scan2.nextLine();
						System.out.println("prenom :");
						String first_name=scan2.nextLine();
						System.out.println("nom :");
						String last_name=scan2.nextLine();
						System.out.println("genre :");
						String gender=scan2.nextLine();
						System.out.println("date de recrutement AAAA-MM-JJ :");
						String hire_date=scan2.nextLine(); ;
						etat.executeUpdate("INSERT INTO employees VALUES ("+emp_no+",'"+birth_date+"','"+first_name+"','"+last_name+"','"+gender+"','"+hire_date+"')");
						System.out.println(first_name+" "+last_name+" ajoutté");
scan2.close();
					}
					else if  (choix.equals("suppression")){
						
						
						Scanner scan2 = new Scanner(System.in);
						System.out.println("nom : ");
						String NOM=scan2.nextLine();
						System.out.println("prenom : ");
						String PRENOM=scan2.nextLine();
						etat.executeUpdate("DELETE FROM employees WHERE (first_name='"+PRENOM+"'AND last_name='"+NOM+"')");
						scan2.close();
						System.out.println(PRENOM+" "+NOM+" supprimé");
					}
					else{
						System.out.println("entrée invalide");
					}
					scan.close();

				
			}
			
			
			if (QUOI==6){
				System.out.println("ajout ou suppression ? ");
				Scanner scan = new Scanner(System.in);
				String choix=scan.nextLine();
				
				if (choix.equals("ajout")){
					int numdept;
					ArrayList<Integer> listenumdep=new ArrayList<Integer>();
					rset=etat.executeQuery("SELECT dept_no FROM departments");
					while (rset.next()){
						String couper="";
						String complet = rset.getString(1);
						for (int i=1;i<complet.length();i++)couper+=complet.charAt(i);
						listenumdep.add(Integer.parseInt(couper));
					}
					int max=1;
					for (int i=0;i<listenumdep.size();i++)if(listenumdep.get(i)>max)max=listenumdep.get(i);
					String dept_no="d"+(max+1);
					Scanner scan2 = new Scanner(System.in);
					System.out.println("nom departement :");
					String dept_name=scan2.nextLine();
					etat.executeUpdate("INSERT INTO departments VALUES ('"+dept_no+"','"+dept_name+"')");
					
					System.out.println(dept_name+" ajoutté");
scan2.close();
				}
				else if  (choix.equals("suppression")){
					
					
					
					Scanner scan2 = new Scanner(System.in);
					System.out.println("nom departement: ");
					String dept_name=scan2.nextLine();
					etat.executeUpdate("DELETE FROM departments WHERE (dept_name='"+dept_name+"')");
					scan2.close();
					System.out.println(dept_name+" supprimé");
				}
				else{
					System.out.println("entrée invalide");
				}
				
			}
			
			
			
		}
		catch(SQLException ex){
			System.err.println("Erreur : "+ex);

		}
	}
}
