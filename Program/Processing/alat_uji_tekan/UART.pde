int posxcom = 700;
int posycom = -45;
int posxcom2 = 550;
int posycom2 = -45;
String shortifyPortName(String portName, int maxlen)  
{
  String shortName = portName;
  if (shortName.startsWith("/dev/")) shortName = shortName.substring(5);  
  if (shortName.startsWith("tty.")) shortName = shortName.substring(4); // get rid off leading tty. part of device name
  if (portName.length()>maxlen) shortName = shortName.substring(0, (maxlen-1)/2) + "~" +shortName.substring(shortName.length()-(maxlen-(maxlen-1)/2));
  if (shortName.startsWith("cu.")) shortName = "";// only collect the corresponding tty. devices
  return shortName;
}

void setup_UART()
{ 

  //Comport List Selection                   
  commListbox = cp5.addListBox("portComList", posxcom+5, posycom+85, 150, 240); // make a listbox and populate it with the available comm ports
  commListbox.setCaptionLabel("PORT COM");
  commListbox.setColorBackground(black_);
  commListbox.setItemHeight(15);
  commListbox.setBarHeight(15);
  commListbox.close();

  for (int i=0; i<Serial.list ().length; i++) 
  {

    String pn = shortifyPortName(Serial.list()[i], 13);
    if (pn.length() >0 ) commListbox.addItem(pn, i); // addItem(name,value)
    commListMax = i;
  }
  fill(#000000);
  commListbox.addItem("Close Comm", ++commListMax); // addItem(name,value)
  // text label for which comm port selected
  txtlblWhichcom = cp5.addTextlabel("txtlblWhichcom", "No Port Selected", posxcom+5, posycom+62); // textlabel(name,text,x,y)
}

void setup_UART2()
{ 

  //Comport List Selection                   
  commListbox2 = cp5.addListBox("portComList2", posxcom2+5, posycom2+85, 150, 240); // make a listbox and populate it with the available comm ports
  commListbox2.setCaptionLabel("PORT COM");
  commListbox2.setColorBackground(black_);
  commListbox2.setItemHeight(15);
  commListbox2.setBarHeight(15);
  commListbox2.close();

  for (int i=0; i<Serial.list ().length; i++) 
  {

    String pn = shortifyPortName(Serial.list()[i], 13);
    if (pn.length() >0 ) commListbox2.addItem(pn, i); // addItem(name,value)
    commListMax2 = i;
  }
  fill(#000000);
  commListbox2.addItem("Close Comm", ++commListMax2); // addItem(name,value)
  // text label for which comm port selected
  txtlblWhichcom2 = cp5.addTextlabel("txtlblWhichcom2", "No Port Selected", posxcom2+5, posycom2+62); // textlabel(name,text,x,y)
}

void Send_To_Arduino()
{
  if (mode == 0) {
    if (serial_conect==1) 
    {  

      //indicator connect
      fill(0, 255, 0);
      ellipse(posxcom+130, posycom+67, 10, 10);
      noFill();

    } else 
    {//indicator no connect
      fill(128, 0, 0);
      ellipse(posxcom+130, posycom+67, 10, 10);
      noFill();
    }

    if (serial_conect2==1) 
    {  

      //indicator connect
      fill(0, 255, 0);
      ellipse(posxcom2+130, posycom2+67, 10, 10);
      noFill();

    } else 
    {//indicator no connect
      fill(128, 0, 0);
      ellipse(posxcom2+130, posycom2+67, 10, 10);
      noFill();
    }
  }
}

void InitSerial(float portValue, int port) 
{
  if (port == 1) {
    if (portValue < commListMax) {
      String portPos = Serial.list()[int(portValue)];
      txtlblWhichcom.setValue("Connected = " + shortifyPortName(portPos, 8));
      serial = new Serial(this, portPos, 115200);
      serial.bufferUntil('\n');
      serial_conect=1;
    } else 
    {
      txtlblWhichcom.setValue("Not Connected");
      serial.clear();
      serial.stop();
      serial_conect=0;
    }
  } else if (port == 2) {
    //q
    if (portValue < commListMax) {
      String portPos2 = Serial.list()[int(portValue)];
      txtlblWhichcom2.setValue("Connected = " + shortifyPortName(portPos2, 8));
      serial2 = new Serial(this, portPos2, 115200);
      serial2.bufferUntil('\n');
      serial_conect2=1;
    } else {
      txtlblWhichcom2.setValue("Not Connected");
      serial2.clear();
      serial2.stop();
      serial_conect2=0;
    }
  }
}


int x = 0;
void serialEvent (Serial usbPort) 
{
  if (usbPort == serial) {
    try {

      String usbString = usbPort.readStringUntil ('\n');

      if (usbString != null) 
      {

        float[] vals = float(split(usbString, "\t")); 

        // Fill r,g,b variables
        loadcell = vals[0];
        rotary_encoder = vals[1];

        //int loadcell = int(usbString);
        println("Serial 1: "+usbString); //--> for debuging
        graph_master.push("loadcell_m1", loadcell);
        if (is_recording == true) {
          TableRow newRow = table.addRow();
          newRow.setLong("time", millis() - alpha_millis);
          newRow.setFloat("rotary_encoder", rotary_encoder);
          newRow.setFloat("loadcell", loadcell);

          if (rotaryMode == "press") {
            if (rotary_encoder > last_rotary_encoder) {
              TableRow newRow2 = tableRotary.addRow();
              newRow2.setFloat("rotary_encoder", rotary_encoder);
              newRow2.setFloat("loadcell", loadcell);
            }
            
          }else if (rotaryMode == "home") {
            if (rotary_encoder < last_rotary_encoder) {
              TableRow newRow2 = tableRotary.addRow();
              newRow2.setFloat("rotary_encoder", rotary_encoder);
              newRow2.setFloat("loadcell", loadcell);
            }
            
          }
        }
        last_rotary_encoder = rotary_encoder;
      }
    }
    catch(RuntimeException e)
    {
      println("Serial event is not null");
      println(e);
    }
  } else if (usbPort == serial2) {
    try {
      String usbString = usbPort.readStringUntil ('\n');

      if (usbString != null) 
      {
        //int loadcell = int(usbString);
        println("Serial 2: "+usbString); //--> for debuging
      }
    }
    catch(RuntimeException e)
    {
      println("Serial event is not null");
      println(e);
    }
  }
}
