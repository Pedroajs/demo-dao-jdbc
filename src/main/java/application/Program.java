package application;

import model.entities.Department;
import model.entities.Seller;

import java.util.Date;

public class Program {
    public static void main(String[] args) {
        Department department = new Department(1, "Kindle");
        Seller seller = new Seller(1, "Predo", "pedro@gmail.com", new Date(), 6000.0, department.getId());

        System.out.println(department);
        System.out.println(seller);
    }
}
