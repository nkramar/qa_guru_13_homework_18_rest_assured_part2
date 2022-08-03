package tests;

import com.github.javafaker.Faker;
import utils.RandomDataGenerator;

import java.util.Locale;


public class TestData {

  Faker faker = new Faker(new Locale("en"));
  String gender = RandomDataGenerator.randomGender(),
          firstName = faker.name().firstName(),
          lastName = faker.name().lastName(),
          email = faker.internet().emailAddress(),
          password = String.valueOf(faker.internet().password(6, 12)),
          confirmPassword = password;


  }




