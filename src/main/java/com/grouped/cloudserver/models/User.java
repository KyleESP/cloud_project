package com.grouped.cloudserver.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@JsonSerialize(as=User.class)
@Table(name = "user", schema = "cloud", catalog = "")
public class User {

    private String id;
    private String firstName;
    private String lastName;
    private String birthDay;
    private Position position;

    public User() {
        this.position = new Position();
    }

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(nullable = false, name = "firstName")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(nullable = false, name = "lastName")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(nullable = false, name = "birthDay")
    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    @JsonIgnore
    @Basic
    @Column(nullable = false, name = "lat")
    public double getLat() {
        return this.position.getLat();
    }

    public void setLat(double lat) {
        this.position.setLat(lat);
    }

    @JsonIgnore
    @Basic
    @Column(nullable = false, name = "lon")
    public double getLon() {
        return this.position.getLon();
    }

    public void setLon(double lon) {
        this.position.setLon(lon);
    }

    @Transient
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
