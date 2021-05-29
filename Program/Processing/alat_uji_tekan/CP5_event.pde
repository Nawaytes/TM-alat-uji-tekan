void controlEvent(ControlEvent theControlEvent)
{
  if (theControlEvent.isController()) {
    println(theControlEvent.getController().getName());
    println(theControlEvent.getController().getId());
    String controllerName = theControlEvent.getController().getName();
    int getID=theControlEvent.getController().getId();
    if (controllerName=="portComList") {
      println("smile");
      InitSerial(theControlEvent.getController().getValue());
      return;
    }

    if        (getID == 41) { // Move UP
      serial.write("P 3200 5000");
    } else if (getID == 42) { // Move DOWN
      serial.write("N 3200 5000");
    } else if (getID == 43) { //Home
    serial.write("H");
    } else if (getID == 44) { //Press
    } else if (getID == 45) { //Stop
    serial.write("S");
    } else if (getID == 51) { //"Set Home
    serial.write("U");
    } else if (getID == 52) {
    } else if (getID == 53) {
    } else if (getID == 54) {
    } else if (getID == 55) {
    } else if (getID == 56) { //calibrating
    }
  }
}
