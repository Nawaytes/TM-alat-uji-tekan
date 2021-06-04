#include "HX711.h"
#include <Encoder.h>
// HX711.DOUT	- pin #A1
// HX711.PD_SCK	- pin #A0

HX711 scale(A2, A3);		// parameter "gain" is ommited; the default value 128 is used by the library
Encoder myEnc(2, 3);
float current_output, previous_output, alpha = 0.45;
void setup() {
  Serial.begin(115200);
  Serial.println("System begin");
  Serial.println("Basic Encoder Test:");
  scale.set_scale(123.f);                      // this value is obtained by calibrating the scale with known weights; see the README for details
  scale.tare();				        // reset the scale to 0
  Serial.println("Loadcell ok");
}
long oldPosition  = -999;
void loop() {
  long newPosition = myEnc.read();
  long LC = scale.get_units();
  if (newPosition != oldPosition) {
    oldPosition = newPosition;
//    Serial.println("RE : " + String(newPosition));
  }
//  send_float(LC);
current_output  = alpha*LC + (1-alpha)*previous_output;
previous_output = current_output;
Serial.print(current_output);Serial.print("\t");
Serial.print(newPosition);Serial.print("\t");
Serial.print("\r\n");
  
  
}

//void send_float (float arg)
//{
//  // get access to the float as a byte-array:
//  byte * data = (byte *) &arg; 
//
//  // write the data to the serial
//  Serial.write (data, sizeof (arg));
//}
