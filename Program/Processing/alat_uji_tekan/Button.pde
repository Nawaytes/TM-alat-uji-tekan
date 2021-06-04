void setup_button() {

  noStroke();
  //----------------------------------------------------------------------------------- Btn UP
  btn_up = cp5.addButton("btn_up")
    .setValue(0)
    .setId(41)
    .setPosition(14, 521)
    .setSize(144, 63)
    .setFont(createFont("verdana", 16))
    ;
  btn_up.getColor().setBackground(color(#993B85, 8))
    ;
  btn_up.setCaptionLabel("MOVE UP")
    ;
  //----------------------------------------------------------------------------------- Btn DOWN
  btn_down = cp5.addButton("btn_down")
    .setValue(0)
    .setId(42)
    .setPosition(174, 521)
    .setSize(144, 63)
    .setFont(createFont("verdana", 16))
    ;
  btn_down.getColor().setBackground(color(#993B85, 8))
    ;
  btn_down.setCaptionLabel("MOVE DOWN")
    ;
  //----------------------------------------------------------------------------------- Btn Home
  btn_home = cp5.addButton("btn_home")
    .setValue(0)
    .setId(43)
    .setPosition(334, 521)
    .setSize(144, 63)
    .setFont(createFont("verdana", 16))
    ;
  btn_home.getColor().setBackground(color(#993B85, 8))
    ;
  btn_home.setCaptionLabel("Home")
    ;
  //----------------------------------------------------------------------------------- Btn Press
  btn_press = cp5.addButton("btn_Press")
    .setValue(0)
    .setId(44)
    .setPosition(494, 521)
    .setSize(144, 63)
    .setFont(createFont("verdana", 16))
    ;
  btn_press.getColor().setBackground(color(#993B85, 8))
    ;
  btn_press.setCaptionLabel("Press")
    ;
  //----------------------------------------------------------------------------------- Btn Stop
  btn_stop = cp5.addButton("btn_stop")
    .setValue(0)
    .setId(45)
    .setPosition(654, 521)
    .setSize(144, 63)
    .setFont(createFont("verdana", 16))
    ;
  btn_stop.getColor().setBackground(color(#993B85, 8))
    ;
  btn_stop.setCaptionLabel("Stop")
    ;
  //----------------------------------------------------------------------------------- SETHOME

  btn_sethome = cp5.addButton("btn_sethome")
    .setValue(0)
    .setId(51)
    .setPosition(708, 118)
    .setSize(192, 73)
    .setFont(createFont("verdana", 16))
    ;
  btn_sethome.getColor().setBackground(color(#993B85, 8))
    ;
  btn_sethome.setCaptionLabel("Set Home");
  btn_sethome.setVisible(true);

  //----------------------------------------------------------------------------------- RECORD

  btn_record = cp5.addButton("btn_record")
    .setValue(0)
    .setId(52)
    .setPosition(708, 192)
    .setSize(192, 73)
    .setFont(createFont("verdana", 16))
    ;
  btn_record.getColor().setBackground(color(#993B85, 8))
    ;
  btn_record.setCaptionLabel("RECORD");
  ;
  btn_record.setVisible(true);
 
}
