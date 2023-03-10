public class Ticket {
    private final byte row;
    private final byte seat;
    private final float price;
    private final Person person;

    public Ticket(byte row, byte seat, float price, Person person) {
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;
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

}