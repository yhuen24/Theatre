public class Ticket {
    private byte row;
    private byte seat;
    private float price;
    private Person person;

    public Ticket(byte newRow, byte newSeat, float newPrice, Person newPerson) {
        row = newRow;
        seat = newSeat;
        price = newPrice;
        person = newPerson;
    }

    public void print() {
        System.out.println("name: " + person.getName() + " " + person.getSurname());
        System.out.println("email: " + person.getEmail());
        System.out.println("row: " + row + "    seat: " + seat);
        System.out.println("price: £" + price);
    }

    public float getPrice() {
        return price;
    }

    public byte getSeat() {
        return seat;
    }

    public byte getRow() {
        return row;
    }

    public Person getPerson() {
        return person;
    }

    public void setRow(byte newRow) {
        row = newRow;
    }

    public void setSeat(byte newSeat) {
        seat = newSeat;
    }

    public void setPrice(float newPrice) {
        price = newPrice;
    }
}