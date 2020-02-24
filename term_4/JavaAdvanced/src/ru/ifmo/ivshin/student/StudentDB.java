package ru.ifmo.ivshin.student;

import info.kgeorgiy.java.advanced.student.Group;
import info.kgeorgiy.java.advanced.student.Student;
import info.kgeorgiy.java.advanced.student.StudentGroupQuery;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class StudentDB implements StudentGroupQuery {
    @Override
    public List<Group> getGroupsByName(Collection<Student> students) {
        return getGroups(students, this::sortStudentsByName);
    }

    @Override
    public List<Group> getGroupsById(Collection<Student> students) {
        return getGroups(students, this::sortStudentsById);
    }

    @Override
    public String getLargestGroup(Collection<Student> students) {
        return getLargestGroup(students, List::size);
    }

    @Override
    public String getLargestGroupFirstName(Collection<Student> students) {
        return getLargestGroup(students, list -> getDistinctFirstNames(list).size());
    }


    @Override
    public List<String> getFirstNames(List<Student> students) {
        return get(students, Student::getFirstName);
    }

    @Override
    public List<String> getLastNames(List<Student> students) {
        return get(students, Student::getLastName);
    }

    @Override
    public List<String> getGroups(List<Student> students) {
        return get(students, Student::getGroup);
    }

    @Override
    public List<String> getFullNames(List<Student> students) {
        return get(students, s -> String.format("%s %s", s.getFirstName(), s.getLastName()));
    }

    @Override
    public Set<String> getDistinctFirstNames(List<Student> students) {
        return students.stream()
                .map(Student::getFirstName)
                .collect(Collectors.toCollection(TreeSet::new));
    }

    @Override
    public String getMinStudentFirstName(List<Student> students) {
        return students.stream()
                .min(Comparator.comparing(Student::getId))
                .map(Student::getFirstName)
                .orElse("");
    }

    @Override
    public List<Student> sortStudentsById(Collection<Student> students) {
        return students.stream()
                .sorted(Comparator.comparing(Student::getId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Student> sortStudentsByName(Collection<Student> students) {
        return students.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    @Override
    public List<Student> findStudentsByFirstName(Collection<Student> students, String name) {
        return getSorted(students, s -> s.getFirstName().equals(name));
    }

    @Override
    public List<Student> findStudentsByLastName(Collection<Student> students, String name) {
        return getSorted(students, s -> s.getLastName().equals(name));
    }

    @Override
    public List<Student> findStudentsByGroup(Collection<Student> students, String group) {
        return getSorted(students, s -> s.getGroup().equals(group));
    }

    @Override
    public Map<String, String> findStudentNamesByGroup(Collection<Student> students, String group) {
        return students.stream()
                .filter(s -> s.getGroup().equals(group))
                .collect(Collectors.toMap(Student::getLastName, Student::getFirstName, (l, f) -> l.compareTo(f) < 0 ? l : f));
    }

    private Comparator<Student> comparator = Comparator.comparing(Student::getLastName)
            .thenComparing(Student::getFirstName)
            .thenComparing(Student::getId);

    private List<String> get(Collection<Student> students, Function<Student, String> fun) {
        return students.stream()
                .map(fun)
                .collect(Collectors.toList());
    }

    private List<Student> getSorted(Collection<Student> students, Predicate<Student> pre) {
        return students.stream()
                .sorted(comparator)
                .filter(pre)
                .collect(Collectors.toList());
    }

    private List<Group> getGroups(Collection<Student> students, UnaryOperator<List<Student>> op) {
        return students.stream()
                .collect(Collectors.groupingBy(Student::getGroup, TreeMap::new, Collectors.toList()))
                .entrySet()
                .stream()
                .map(o -> new Group(o.getKey(), op.apply(o.getValue())))
                .collect(Collectors.toList());
    }

    private String getLargestGroup(Collection<Student> students, Function<List<Student>, Integer> fun) {
        return students.stream()
                .collect(Collectors.groupingBy(Student::getGroup, TreeMap::new, Collectors.toList()))
                .entrySet()
                .stream()
                .max(Comparator.comparing((Map.Entry<String, List<Student>> group) -> fun.apply(group.getValue()))
                .thenComparing(Map.Entry::getKey, Collections.reverseOrder(String::compareTo)))
                .map(Map.Entry::getKey)
                .orElse("");
    }
}
