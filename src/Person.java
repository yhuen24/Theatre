public class Person {
    private String name;
    private String surname;
    private String email;

    public Person(String newName, String newSurname, String newEmail) {
        name = newName;
        surname = newSurname;
        email = newEmail;
    }

    public String getName() {
        return name;
    }

    public String getSurname() { return surname; }

    public String getEmail() { return email; }

    public void setName(String newName) {
        name = newName;
    }

    public void setSurname(String newSurname) {
        surname = newSurname;
    }

    public void setEmail(String newEmail) {
        email = newEmail;
    }
}