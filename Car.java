package org.example.cars.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Car {
    long id;
    String brand;
    String model;
    String number;
    int releaseYear;
    public Car(String brand, String model, String number, int releaseYear) {
        this.brand = brand;
        this.model = model;
        this.number = number;
        this.releaseYear = releaseYear;
    }
}
