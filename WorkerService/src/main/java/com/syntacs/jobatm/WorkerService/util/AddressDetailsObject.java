package com.syntacs.jobatm.WorkerService.util;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class AddressDetailsObject {

    private String houseNo;
    @Column(nullable = true)
    private String streetNo;
    @Column(nullable = true)
    private String landmark;
    private String city;
    private String district;
    private String state;
    private String country;
    private long pincode;
}
