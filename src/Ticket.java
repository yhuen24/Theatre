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
}
