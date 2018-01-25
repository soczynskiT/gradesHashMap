import java.util.*;

class Student {
    private String name;
    private String surname;
    private int schoolClass;

    public Student(String name, String surname, int schoolClass) {
        this.name = name;
        this.surname = surname;
        this.schoolClass = schoolClass;
    }

    @Override
    public int hashCode() {
        return this.schoolClass * this.name.length() * this.surname.length();
    }

    @Override
    public boolean equals(Object o) {
        final Student s = (Student) o;
        return this.name.equals(s.name) && this.surname.equals(s.surname) && this.schoolClass == s.schoolClass;
    }

    @Override
    public String toString() {
        return "\n" + name + " " + surname + " attends to class " + schoolClass;
    }
}

class SingleSubjectListGenerator {
    final private Random random = new Random();

    public List<Integer> generateRandomListOfGradesNo(int maxNoOfGrades) {
        final int maxValue = 6;
        List<Integer> newGradesList = new LinkedList<>();
        for (int i=0; i<maxNoOfGrades; i++) {
            int newGrade = random.nextInt(maxValue);
            newGradesList.add(newGrade+1);
        }
        return newGradesList;
    }
}

class SingleSubjectGradesList {
    private List<Integer> singleSubjectGradesList;
    private String name;

    public SingleSubjectGradesList(List<Integer> singleSubjectGradesList, String name) {
        this.singleSubjectGradesList = singleSubjectGradesList;
        this.name = name;
    }

    public List<Integer> getSingleSubjectGradesList() {
        return singleSubjectGradesList;
    }

    public String getName() {
        return name;
    }

    public void setSingleSubjectGradesList(List<Integer> singleSubjectGradesList) {
        this.singleSubjectGradesList = singleSubjectGradesList;
    }

    @Override
    public String toString() {
        return "\n" + this.name + ": " + this.singleSubjectGradesList.toString();
    }
}

class AllSubjectsGradesList {
    private List<SingleSubjectGradesList> allGradesList;

    public AllSubjectsGradesList(List<SingleSubjectGradesList> allGradesList) {
        this.allGradesList = allGradesList;
    }

    public void addGradeTo(int newGradeValue, String subjectName) {
        for(SingleSubjectGradesList anList : this.allGradesList) {
            if(subjectName.equals(anList.getName())) {
                List<Integer> newList = anList.getSingleSubjectGradesList();
                newList.add(newGradeValue);
                anList.setSingleSubjectGradesList(newList);
            }
        }
    }

    private double getAverageOfAllGrades() {
        double sum = 0;
        int elementsNo = 0;
        for (SingleSubjectGradesList anAllGradesList : this.allGradesList) {
            List<Integer> singleGradesList = anAllGradesList.getSingleSubjectGradesList();
            elementsNo += singleGradesList.size();
            for (Integer aSingleGradesList : singleGradesList) {
                sum += aSingleGradesList;
            }
        }
        return sum/elementsNo;
    }

    @Override
    public String toString() {
        return "\nGrades list: " + this.allGradesList + "\nAverage grade value = " + this.getAverageOfAllGrades();
    }
}
class Application {
    public static void main(String[] args) {
        final SingleSubjectListGenerator generator = new SingleSubjectListGenerator();

        final Student st1 = new Student("Jan", "Kowalski", 2);
        final Student st2 = new Student("Anna", "Nowak", 4);

        final List<Integer> st1Math = generator.generateRandomListOfGradesNo(8);
        final SingleSubjectGradesList st1MathGrades = new SingleSubjectGradesList(st1Math, "Math");
        final List<Integer> st1Biology = generator.generateRandomListOfGradesNo(5);
        final SingleSubjectGradesList st1BiologyGrades = new SingleSubjectGradesList(st1Biology, "Biology");
        final List<SingleSubjectGradesList> st1AllGradesList = new ArrayList<>();
        st1AllGradesList.addAll(Arrays.asList(st1MathGrades, st1BiologyGrades));
        final AllSubjectsGradesList student1AllGrades = new AllSubjectsGradesList(st1AllGradesList);

        final List<Integer> st2Chemistry = generator.generateRandomListOfGradesNo(4);
        final SingleSubjectGradesList st2ChemistryGrades = new SingleSubjectGradesList(st2Chemistry, "Chemistry");
        final List<Integer> st2Lang = generator.generateRandomListOfGradesNo(8);
        final SingleSubjectGradesList st2LangGrades = new SingleSubjectGradesList(st2Lang, "Language");
        final List<Integer> st2PT = generator.generateRandomListOfGradesNo(4);
        final SingleSubjectGradesList st2PTGrades = new SingleSubjectGradesList(st2PT, "P.T.");
        final List<SingleSubjectGradesList> st2AllGradesList = new ArrayList<>();
        st2AllGradesList.addAll(Arrays.asList(st2ChemistryGrades, st2LangGrades, st2PTGrades));
        final AllSubjectsGradesList student2AllGrades = new AllSubjectsGradesList(st2AllGradesList);

        final Map<Student, AllSubjectsGradesList> studentsGradesMap = new HashMap<>();
        studentsGradesMap.put(st1, student1AllGrades);
        studentsGradesMap.put(st2, student2AllGrades);

        for(Map.Entry<Student, AllSubjectsGradesList> entry : studentsGradesMap.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

        /* Test of adding new subject for existing student */
        /* Test of adding new grade for existing student and existing subject*/
        final List<Integer> st2Biology = generator.generateRandomListOfGradesNo(4);
        final SingleSubjectGradesList st2BiologyGrades = new SingleSubjectGradesList(st2Biology, "Biology");

        st2AllGradesList.add(st2BiologyGrades);
        student2AllGrades.addGradeTo(6, "Chemistry");

        for(Map.Entry<Student, AllSubjectsGradesList> entry : studentsGradesMap.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }
}