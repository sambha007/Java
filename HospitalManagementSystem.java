package HospitalMangaementSystem;

import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {
    private static final String url="jdbc:mysql://localhost:3306/hospital";
    private static final String username="root";
    private static final String password="Sambha@1010";

    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
           e.printStackTrace();
        }
        Scanner scanner=new Scanner(System.in);
        try{
            Connection connection= DriverManager.getConnection(url,username,password);
            Patient patient=new Patient(connection,scanner);
            Doctor doctor=new Doctor(connection);
            while (true)
            {
                System.out.println("*** hospital management System *** ");
                System.out.println("1. Add patient");
                System.out.println("2. view patients ");
                System.out.println("3. view Doctor ");
                System.out.println("4. Book Appointment  ");
                System.out.println("5. Exit ");
                System.out.println("Enter your choice ");
                int ch=scanner.nextInt();
                switch (ch){
                    case 1:
                        patient.addPatient();
                        System.out.println();
                        break;
                    case 2:
                        patient.viewPatients();
                        System.out.println();
                        break;
                    case 3:
                        doctor.viewDocter();
                        System.out.println();
                        break;
                    case 4:
                          bookAppointment(patient,doctor,connection,scanner);
                        System.out.println();
                        break;
                    case 5:

                        break;
                    default:
                        break;

                }

            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    public static boolean checkDoctor(int doctorId,String appointmentDate,Connection connection)
    {
        String query="select count(*) from appointments where doctor_id=? and appointment_date=? ";
        try
        {
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1,doctorId);
            preparedStatement.setString(2,appointmentDate);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next())
            {
                int count=resultSet.getInt(1);
                if(count==0)
                {
                    return true;
                }
                else
                {
                    return false;
                }

            }

        }
        catch (SQLException e)
        {
           e.printStackTrace();
        }
        return false;

    }
    public static void bookAppointment(Patient patient,Doctor doctor, Connection connection,Scanner scanner)
    {
        System.out.println("Enter Patient Id: ");
        int patientId=scanner.nextInt();
        System.out.println("Enter Doctor Id: ");
        int doctorId=scanner.nextInt();
        System.out.println("Enter appointment date(yyyy-mm-dd) ");
        String appointmentDate =scanner.nextLine();
        if(patient.getPatientById(patientId) && doctor.getDoctorById(doctorId))
        {
            if(checkDoctor(doctorId,appointmentDate,connection))
            {
                String appquery="insert into appointments( PATIENT_ID,DOCTOR_ID, APPOINTMENT_DATE)values(?,?,?)";
                try
                {
                    PreparedStatement preparedStatement=connection.prepareStatement(appquery);
                    preparedStatement.setInt(1,patientId);
                    preparedStatement.setInt(2,doctorId);
                    preparedStatement.setString(3,appointmentDate);
                    int rowsAffected=preparedStatement.executeUpdate();
                    if(rowsAffected>0)
                    {
                        System.out.println("appointment Booked !");

                    }
                    else
                    {
                        System.out.println("appointment not Booked ...fail !");
                    }
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            else
            {
                System.out.println("doctor is not available....");
            }

        }
        else
        {
            System.out.println("doctor or patient are not present");
        }



    }
}
