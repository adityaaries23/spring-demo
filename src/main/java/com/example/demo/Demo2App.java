package com.example.demo;


import org.springframework.web.bind.annotation.*;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class Demo2App {
    List<Double> tarifPajakIndo = List.of(0.05,0.1,0.15);
    List<Integer> batasMinIndo = List.of(0,50,250);
    List<Integer> batasMaxIndo = List.of(50,250,500);

    List<Double> tarifPajakVietnam = List.of(0.025,0.075);
    List<Integer> batasMinVietnam = List.of(0,50);
    List<Integer> batasMaxVietnam = List.of(50,250);

    @PostMapping("/hitungpajak")
    public Employee hello2(@RequestBody Person person) {
        Employee employee = new Employee();
        employee.name = person.employee.name;
        employee.sex = person.employee.sex;
        employee.childs = person.employee.childs;
        employee.country = person.employee.country;
        employee.marital_status = person.employee.marital_status;
        employee.tarifPtkp = getTarifPtkp(employee);
        employee.monthSalary = totalGaji(person.komponengaji,employee);
        employee.yearSalary = employee.monthSalary*12-(employee.tarifPtkp*1000000);
        employee.montlyTax = employee.country.equalsIgnoreCase("indonesia") ? getPajakIndo(employee.yearSalary) : getPajakVietnam(employee.yearSalary);


        return employee;
    }

    public record Person(Employee employee, List<Salary> komponengaji) {
    }

    public int getTarifPtkp(Employee employee) {
        if (employee.country.equalsIgnoreCase("indonesia")) {
            if (employee.marital_status.equalsIgnoreCase("maried")){
                if(employee.childs > 0){
                    return 75;
                } else {
                    return 50;
                }
            } else {
                return 25;
            }
        } else {
            if (employee.marital_status.equalsIgnoreCase("maried")){
                return 30;
            } else {
                return 15;
            }
        }
    }

    public Long totalGaji(List<Salary> komponengaji, Employee employee) {
        Long total = 0L;
        for (Salary salary : komponengaji) {
            if(salary.type.equalsIgnoreCase("earning")){
                total += salary.amount;
            }
            if(employee.country.equalsIgnoreCase("vietnam")
                    && salary.type.equalsIgnoreCase("deduction")){
                total -= salary.amount;
            }
        }
        return total;
    }

    public Integer getPajakIndo(Long salaryYear){
        Double totalPajak = 0.0;
        Long temp = salaryYear/1000000;
        Long tomp = salaryYear/1000000;
        for (int i = 0; i < tarifPajakIndo.size() ; i++) {
            int min = batasMinIndo.get(i);
            int max = batasMaxIndo.get(i);
            double tarif = tarifPajakIndo.get(i);

            if(min==250 && temp > min){
                totalPajak += (tomp-min)*tarif;
                break;
            }

            if(temp>min && temp>max){
                totalPajak += max*tarif;
                temp -= max;
            } else if (temp>min && temp<max) {
                totalPajak += (tomp-min)*tarif;
            }
        }
        Double monthly = totalPajak/12;
        DecimalFormat df = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.FLOOR);
        return (int) (Double.parseDouble(df.format(monthly))*1000000);
    }

    public Integer getPajakVietnam(Long salaryYear){
        Double totalPajak = 0.0;
        Long temp = salaryYear/1000000;
        Long tomp = salaryYear/1000000;
        for (int i = 0; i < tarifPajakVietnam.size() ; i++) {
            int min = batasMinVietnam.get(i);
            int max = batasMaxVietnam.get(i);
            double tarif = tarifPajakVietnam.get(i);

            if(min==50 && temp > min){
                totalPajak += (tomp-min)*tarif;
                break;
            }

            if(temp>min && temp>max){
                totalPajak += max*tarif;
                temp -= max;
            } else if (temp>min && temp<max) {
                totalPajak += (tomp-min)*tarif;
            }
        }
        Double monthly = totalPajak/12;
        DecimalFormat df = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.FLOOR);
        return (int) (Double.parseDouble(df.format(monthly))*1000000);
    }

}
