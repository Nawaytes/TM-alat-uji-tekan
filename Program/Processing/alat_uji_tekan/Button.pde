void setup_button() {

  noStroke();
  //-------------------------------------Btn UP
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
//-------------------------------------Btn DOWN
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
  //-------------------------------------Btn Home
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
//-------------------------------------Btn Press
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
//-------------------------------------Btn Stop
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
  //-----------------------------------------------------------------------------------

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

  //----------------------------------------------------------------------------------- 1

  btn_loadcell2 = cp5.addButton("btn_loadcell2")
    .setValue(0)
    .setId(52)
    .setPosition(14, 232)
    .setSize(144, 63)
    .setFont(createFont("verdana", 16))
    ;
  btn_loadcell2.getColor().setBackground(color(#993B85, 8))
    ;
  btn_loadcell2.setCaptionLabel("Loadcell 2");
  ;
  btn_loadcell2.setVisible(false);
  //----------------------------------------------------------------------------------- 2

  btn_loadcell3 = cp5.addButton("btn_loadcell3")
    .setValue(0)
    .setId(53)
    .setPosition(14, 309)
    .setSize(144, 63)
    .setFont(createFont("verdana", 16))
    ;
  btn_loadcell3.getColor().setBackground(color(#993B85, 8))
    ;
  btn_loadcell3.setCaptionLabel("Loadcell 3");
  ;
  btn_loadcell3.setVisible(false);
  //----------------------------------------------------------------------------------- 3

  btn_loadcell4 = cp5.addButton("btn_loadcell4")
    .setValue(0)
    .setId(54)
    .setPosition(14, 386)
    .setSize(144, 63)
    .setFont(createFont("verdana", 16))
    ;
  btn_loadcell4.getColor().setBackground(color(#993B85, 8))
    ;
  btn_loadcell4.setCaptionLabel("Loadcell 4");
  ;
  btn_loadcell4.setVisible(false);
  //----------------------------------------------------------------------------------- 4

  btn_rst = cp5.addButton("btn_reset")
    .setValue(0)
    .setId(55)
    .setPosition(469, 386)
    .setSize(144, 63)
    .setFont(createFont("verdana", 16))
    ;
  btn_rst.getColor().setBackground(color(#993B85, 8))
    ;
  btn_rst.setCaptionLabel("Reset");
  ;
  btn_rst.setVisible(false);
  //----------------------------------------------------------------------------------- rst

  btn_clbr = cp5.addButton("btn_exe_calibrate")
    .setValue(0)
    .setId(56)
    .setPosition(654, 386)
    .setSize(144, 63)
    .setFont(createFont("verdana", 16))
    ;
  btn_clbr.getColor().setBackground(color(#993B85, 8))
    ;
  btn_clbr.setCaptionLabel("Calibrate");
  ;
  btn_clbr.setVisible(false);
  //----------------------------------------------------------------------------------- calibrate
}
