package com.grouped.cloudserver.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

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

    @Id
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

    @Basic
    @Column(nullable = false, name = "lat")
    public double getLat() {
        return this.position.getLat();
    }

    public void setLat(double lat) {
        if(this.position == null) {this.position = new Position();}
        this.position.setLat(lat);
    }

    @Basic
    @Column(nullable = false, name = "lon")
    public double getLon() {
        return this.position.getLon();
    }

    public void setLon(double lon) {
        if(this.position == null) {this.position = new Position();}
        this.position.setLon(lon);
    }
}
