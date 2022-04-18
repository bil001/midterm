package models;

import java.math.BigDecimal;

public class Host {

    private String id;
    private String lastName;
    private String email;
    private int phone;
    private String address;
    private String city;
    private String state;
    private int postalCode;
    private BigDecimal weekdayRate;
    private BigDecimal weekendRate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public BigDecimal getWeekdayRate() {
        return weekdayRate;
    }

    public void setWeekdayRate(BigDecimal weekdayRate) {
        this.weekdayRate = weekdayRate;
    }

    public BigDecimal getWeekendRate() {
        return weekendRate;
    }

    public void setWeekendRate(BigDecimal weekendRate) {
        this.weekendRate = weekendRate;
    }

    @Override
    public String toString() {
        return "Host{" +
                "id='" + id + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone=" + phone +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", postalCode=" + postalCode +
                ", weekdayRate=" + weekdayRate +
                ", weekendRate=" + weekendRate +
                '}';
    }
}
