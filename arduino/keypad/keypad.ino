#include <Keypad.h>
#include <Key.h>

#include <Adafruit_GFX.h>  // Include core graphics library for the display
#include <Adafruit_SSD1306.h>  // Include Adafruit_SSD1306 library to drive the display


Adafruit_SSD1306 display(128, 64);  // Create display

const byte ROWS = 4; //four rows

const byte COLS = 4; //three columns

char keys[ROWS][COLS] = {

  {'D','C','B','A'},

  {'#','9','6','3'},

  {'0','8','5','2'},

  {'*','7','4','1'}

};

byte rowPins[ROWS] = {5, 4, 3, 2}; //connect to the row pinouts of the keypad

byte colPins[COLS] = {9, 8, 7, 6}; //connect to the column pinouts of the keypad

Keypad keypad = Keypad( makeKeymap(keys), rowPins, colPins, ROWS, COLS );

const int wrongPin = 10;
const int rightPin = 11;
const int feedbackPin = 12;

void setup(){

  pinMode(wrongPin,OUTPUT);
  pinMode(rightPin,OUTPUT);
  pinMode(feedbackPin,OUTPUT);

  delay(100);  // This delay is needed to let the display to initialize

  display.begin(SSD1306_SWITCHCAPVCC, 0x3C);  // Initialize display with the I2C address of 0x3C
 
  display.clearDisplay();  // Clear the buffer

  display.setTextColor(WHITE);  // Set color of the text

  display.setRotation(2);  // Set orientation. Goes from 0, 1, 2 or 3

  display.setTextWrap(false);  // By default, long lines of text are set to automatically “wrap” back to the leftmost column.
                               // To override this behavior (so text will run off the right side of the display - useful for
                               // scrolling marquee effects), use setTextWrap(false). The normal wrapping behavior is restored
                               // with setTextWrap(true).

  display.dim(0);  //Set brightness (0 is maximun and 1 is a little dim)


  //display.setFont(&FreeMono9pt7b);  // Set a custom font
  //display.setTextSize(0);
  display.setCursor(0, 10);  // (x,y)
  display.println("Restarting");  // Text or value to print
  digitalWrite(wrongPin,HIGH);
  digitalWrite(rightPin,HIGH);
  digitalWrite(feedbackPin,HIGH);
  display.display();
  delay(3000);
  display.clearDisplay();
  digitalWrite(wrongPin,LOW);
  digitalWrite(rightPin,LOW);
  digitalWrite(feedbackPin,LOW);
}

String pass = "";
boolean locked = true;
boolean armed = false;

void loop(){

  display.display();
  char key = keypad.getKey();
  if(locked){
    if (key != NO_KEY){
      if(key == '#'){
        Serial.println(pass);
        display.clearDisplay();
        display.setCursor(0, 10);  // (x,y)
        if(pass.equals("0651")){
          locked = false;
          armed = false;
          display.println("Correct");  // Text or value to print
          display.display();
          digitalWrite(rightPin, HIGH);
          digitalWrite(wrongPin, LOW);
          delay(500);
          digitalWrite(rightPin, LOW);
        }else{
          display.println("Incorrect");  // Text or value to print
          display.display();
          digitalWrite(wrongPin, HIGH);
          digitalWrite(rightPin, LOW);
          delay(500);
          digitalWrite(wrongPin, LOW);
        }
        display.clearDisplay();
        pass = "";
      }else{
        display.clearDisplay();
        if(key == '*'){
          pass = "";
        }else{
          pass += key;
          display.setCursor(0, 10);  // (x,y)
          display.println(pass);  // Text or value to print
          display.display();
        }
        digitalWrite(feedbackPin,HIGH);
        delay(100);
        digitalWrite(feedbackPin,LOW);
      }
    }
  }else{
    if(key != NO_KEY){
      if(key == '*'){
        locked = true;
        display.clearDisplay();
      }else{
        if(key == '#'){
          display.clearDisplay();
          display.setCursor(0, 10);
          display.println(armed ? "Armed" : "Disarmed");
        }else{
          if(key == 'A'){
            armed = true;
            for(int i = 0; i < 10; i++){
              display.clearDisplay();
              display.setCursor(0,10);
              display.println("Armed!\nTime Left:\n ["+String(10-i)+"]");
              display.display();
              digitalWrite(wrongPin,HIGH);
              delay(500);
              digitalWrite(wrongPin,LOW);
              delay(500);
            }
            display.clearDisplay();
            display.setCursor(0,10);
            display.println("System Armed!");
            display.display();
            locked = true;
          }else{
            
          }
        }
      }
      digitalWrite(feedbackPin,HIGH);
      delay(100);
      digitalWrite(feedbackPin,LOW);
    }
  }

}
