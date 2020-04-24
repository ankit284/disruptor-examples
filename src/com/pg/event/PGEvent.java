package com.pg.event;

import com.lmax.disruptor.EventFactory;

public class PGEvent {

    private String name;
    private String address;
    private Long telephoneNo;

    public PGEvent() {
    }

    public PGEvent(String name, String address, Long telephoneNo) {
        this.name = name;
        this.address = address;
        this.telephoneNo = telephoneNo;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Long getTelephoneNo() {
        return telephoneNo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTelephoneNo(Long telephoneNo) {
        this.telephoneNo = telephoneNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PGEvent)) return false;

        PGEvent pgEvent = (PGEvent) o;

        if (address != null ? !address.equals(pgEvent.address) : pgEvent.address != null) return false;
        if (name != null ? !name.equals(pgEvent.name) : pgEvent.name != null) return false;
        if (telephoneNo != null ? !telephoneNo.equals(pgEvent.telephoneNo) : pgEvent.telephoneNo != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (telephoneNo != null ? telephoneNo.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PGEvent{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", telephoneNo=" + telephoneNo +
                '}';
    }

    public static final EventFactory<PGEvent> EVENT_FACTORY = new EventFactory<PGEvent>() {
        public PGEvent newInstance() {
            return new PGEvent();
        }
    };
}
