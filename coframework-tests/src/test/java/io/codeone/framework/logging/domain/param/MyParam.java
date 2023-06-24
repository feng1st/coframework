package io.codeone.framework.logging.domain.param;

public class MyParam {

    private Long id;

    private Address address;

    public Long getId() {
        return id;
    }

    public MyParam setId(Long id) {
        this.id = id;
        return this;
    }

    public Address getAddress() {
        return address;
    }

    public MyParam setAddress(Address address) {
        this.address = address;
        return this;
    }

    @Override
    public String toString() {
        return "MyParam{" +
                "id=" + id +
                ", address=" + address +
                '}';
    }

    public static class Address {

        private String city;

        public String getCity() {
            return city;
        }

        public Address setCity(String city) {
            this.city = city;
            return this;
        }

        @Override
        public String toString() {
            return "Address{" +
                    "city='" + city + '\'' +
                    '}';
        }
    }
}
