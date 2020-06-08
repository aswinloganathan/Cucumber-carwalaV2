Feature: Book a car from carwala site

  Scenario: scenario description
    Given Launch the Browser
    And Set the Timeouts
    And Load the URL
    And Click used car
    And Select the City as Chennai
    And Select budget min 8L and max 12L and Click
    And Select Cars with Photos under Only Show Cars With
    And Select Manufacturer as Hyundai and Creta
    And Select Fuel Type as Petrol
    And Select Best Match as KM: Low to High
    And Validate the Cars are listed with KMs Low to High
    And Add the least KM ran car to Wishlist
    And Go to Wishlist and Click on More Details
    When Print all the details under Overview in the Same way as displayed in application
    Then Close the browser