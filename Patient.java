package HospitalMangaementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    private Connection connection;
    private Scanner scanner;
    public Patient(Connection connection, Scanner scanner)
    {
        this.connection=connection;
        this.scanner=scanner;
    }
    public void addPatient()
    {
        System.out.println("enter patient age :");
        int age=scanner.nextInt();
        System.out.println();
        System.out.println("enter patient name:");
        String name=scanner.nextLine();
        System.out.println();
        System.out.println("enter patient gender: ");
        String gender=scanner.nextLine();

        try{
            String query="insert into patients(name ,age,gender)values(?,?,?)";
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            preparedStatement.setInt(2,age);
            preparedStatement.setString(3,gender);
            int affectedRows=preparedStatement.executeUpdate();
            if (affectedRows>0)
            {
                System.out.println("added successfully!!");

            }
            else
            {
                System.out.println("not added check once again");
            }
        }
        catch (SQLException e)
        {
           e.printStackTrace();
        }

    }
    public void viewPatients(){
        String query="select * from patients";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            ResultSet resultSet=preparedStatement.executeQuery();
            System.out.println("Patients: ");
            System.out.println("+------------+--------------------+--------------+-------------+");
            System.out.println("| Patient Id | Name               | Age          | Gender      |");
            System.out.println("+------------+--------------------+--------------+-------------+");
            while (resultSet.next())
            {
                int  id =resultSet.getInt("id");
                String name=resultSet.getString("Name");
                int age=resultSet.getInt("age");
                String gender=resultSet.getString("gender");
                System.out.printf("|%-12s|%-20s|%-10s|%-12s\n",id,name,age,gender);
                System.out.println("+------------+--------------------+--------------+-------------+");


            }


        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    public boolean getPatientById(int id)
    {
        String query="select *from patients where id=?";
        try
        {
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet=preparedStatement.executeQuery();
            if (resultSet.next())
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false ;
    }
}
