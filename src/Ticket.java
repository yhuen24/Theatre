public class Ticket {
    int row;
    int seat;
    float price;
    Person person;

    public Ticket(int newRow, int newSeat, float newPrice, Person newPerson) {
        row = newRow;
        seat = newSeat;
        price = newPrice;
        person = newPerson;
    }

    public void printDetails() {
        System.out.println("name: " + person.name + " " + person.surname);
        System.out.println("email: " + person.email);
        System.out.println("row: " + row + "    seat: " + seat);
        System.out.println("price: " + price);
    }
}