package org.example;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operator"));
        employees.add(new Employee("João", LocalDate.of(1900, 5, 12), new BigDecimal("2284.30"), "Operator"));
        employees.add(new Employee("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9036.14"), "Coordinator"));
        employees.add(new Employee("Miguel", LocalDate.of(1968, 10, 14), new BigDecimal("19119.00"), "Director"));
        employees.add(new Employee("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Receptionist"));
        employees.add(new Employee("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1587.00"), "Operator"));
        employees.add(new Employee("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Accountant"));
        employees.add(new Employee("Laura", LocalDate.of(1994, 7, 6), new BigDecimal("3017.45"), "Manager"));
        employees.add(new Employee("Heloísa", LocalDate.of(2003, 3, 24), new BigDecimal("1606.85"), "Electrician"));
        employees.add(new Employee("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Manager"));

        // remove funcionário com nome João
        employees.removeIf(e -> e.getName().equals("João"));

        // imprime todos os funcionários
        employees.forEach(e -> System.out.println(e.toString()));

        // aumenta o salário de todos os funcionários em 10%
        employees.forEach(e -> e.setSalary(e.getSalary().multiply(new BigDecimal("1.10"))));

        // Agrupar funcionários por função
        Map<String, List<Employee>> employeesByRole = employees.stream()
                .collect(Collectors.groupingBy(Employee::getRole));

        // Imprimir os funcionários agrupados por função
        employeesByRole.forEach((role, employeeList) -> {
            System.out.println("Função: " + role);
            employeeList.forEach(System.out::println);
        });

        // Imprimir funcionários que fazem aniversário em Outubro e Dezembro
        employees.stream()
                .filter(e -> e.getBirthDate().getMonthValue() == 10 || e.getBirthDate().getMonthValue() == 12)
                .forEach(System.out::println);

        // Imprimir funcionário com maior idade
        Employee oldestEmployee = employees.stream()
                .max(Comparator.comparingInt(e -> Period.between(e.getBirthDate(), LocalDate.now()).getYears()))
                .orElse(null);

        if (oldestEmployee != null) {
            System.out.println("Funcionário mais velho: " + oldestEmployee.getName() + ", Idade: " +
                    Period.between(oldestEmployee.getBirthDate(), LocalDate.now()).getYears());
        }

        // Imprimir lista de funcionários por ordem alfabética
        employees.stream()
                .sorted(Comparator.comparing(Employee::getName))
                .forEach(System.out::println);

        // Imprimir total dos salários dos funcionários
        BigDecimal totalSalaries = employees.stream()
                .map(Employee::getSalary)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("Total dos salários: " + totalSalaries);

        // Imprimir quantos salários mínimos ganha cada funcionário
        final BigDecimal minimumWage = new BigDecimal("1212.00");
        employees.forEach(e -> {
            BigDecimal numberOfMinimumWages = e.getSalary().divide(minimumWage, 2, BigDecimal.ROUND_HALF_UP);
            System.out.println(e.getName() + " ganha " + numberOfMinimumWages + " salários mínimos.");
        });
    }
}
