void controlEvent(ControlEvent theControlEvent)
{
  if (theControlEvent.isController()) {
    //println(theControlEvent.getController().getName());
    //println(theControlEvent.getController().getId());
    String controllerName = theControlEvent.getController().getName();
    int getID=theControlEvent.getController().getId();
    if (controllerName=="portComList") {
      println("Connected");
      InitSerial(theControlEvent.getController().getValue(), 1);
      return;
    }
    if (controllerName=="portComList2") {
      println("Connected");
      InitSerial(theControlEvent.getController().getValue(), 2);
      return;
    }

    if        (getID == 41) { // Move UP
      if (serial_conect2==1) {
        int distance = loadConfig("move-up", "config.json");
        serial2.write("P"+distance+" 2500");
      }
    } else if (getID == 42) { // Move DOWN
      if (serial_conect2==1) {
        int distance = loadConfig("move-down", "config.json");
        serial2.write("N"+distance+" 2500");
      }
    } else if (getID == 43) { //Home
      if (serial_conect2==1) {
        serial2.write("H");
        rotaryMode = "home";
      }
    } else if (getID == 44) { //Press
      if (serial_conect2==1) {
        int distance = loadConfig("press", "config.json");
        serial2.write("P"+distance+" 2000");
        rotaryMode = "press";
      }
    } else if (getID == 45) { //Stop
      if (serial_conect2==1) 
        serial2.write("S");
    } else if (getID == 51) { //"Set Home
      if (serial_conect2==1) 
        serial2.write("U");
    } else if (getID == 52) { //Record
      if (serial_conect==1 && serial_conect2==1) {
        print("ok");
        if (btn_record.getLabel() == "RECORD") {
          print("record");
          alpha_millis = millis();
          table = new Table();
          table.addColumn("time");
          table.addColumn("rotary_encoder");
          table.addColumn("loadcell");
          //--------------------------
          tableRotary = new Table();
          tableRotary.addColumn("rotary_encoder");
          tableRotary.addColumn("loadcell");
          //--------------------------
          btn_record.setLabel("STOP");
          is_recording = true;
        } else {
          btn_record.setLabel("RECORD");
          int d = day();    // Values from 1 - 31
          int m = month();  // Values from 1 - 12
          int y = year();   // 2003, 2004, 2005, etc.
          int a = hour();
          int b = minute();
          int c = second();

          String fname = String.valueOf(d)+String.valueOf(m)+String.valueOf(y)+"-"+String.valueOf(a)+String.valueOf(b)+String.valueOf(c);
          println(fname);
          saveTable(table, "data/timebase/timelog-"+fname+".csv");
          saveTable(tableRotary, "data/encoderBase/encoderlog-"+fname+".csv");
          is_recording = false;
        }
      }
    } else if (getID == 53) {
    } else if (getID == 54) {
    } else if (getID == 55) {
    } else if (getID == 56) { //calibrating
    }
  }
}

int loadConfig(String objek, String file) {
  config = loadJSONObject(file); 

  return config.getInt(objek);
}
