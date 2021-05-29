import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.serial.Serial; 
import controlP5.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class alat_uji_tekan extends PApplet {

 // serial library
 // controlP5 library

JSONObject json;
Table table;
Serial serial;
ControlP5 cp5;
PImage img_master, img_multiG, img_calibrate;
Chart graph_master;
Chart graph_loadcell1, graph_loadcell2, graph_loadcell3, graph_loadcell4;

int serial_conect = 0;
int commListMax;
int[] data = null;

Textlabel txtlblWhichcom; 
ListBox commListbox;
ListBox portlist;
int mode = 0;
int yellow_ = color(200, 200, 20), green_ = color(46, 209, 2), red_ = color(120, 30, 30), blue_ = color (0, 102, 200);
int white_ = color(255, 255, 255), black_ = color(0, 0, 0), pink_ = color(255, 0, 255), grey_ = color(0xff636e72);

float _LOADCELL1, _LOADCELL2, _LOADCELL3, _LOADCELL4;
Button btn_single, btn_4graph, btn_calibrate, btn_graph, btn_record;
Button btn_loadcell1, btn_loadcell2, btn_loadcell3, btn_loadcell4;
Button btn_rst, btn_clbr;

Textfield txt_actual_mass, txt_current_mass, txt_gain;

int lc_idx = 0;
int screen=0;

boolean is_recording = false;
float sum_loadcell;
long alpha_millis;
public void setup() {
  
  img_master=loadImage("bg.png");
  //img_multiG=loadImage("multi_graph.png");
  json = new JSONObject();
  cp5 = new ControlP5(this); 
  setup_graph();
  setup_UART();
  setup_button();
  setup_textfield();
}

public void draw() {
  background(img_master);
  //graph_master.push("loadcell_m1", _LOADCELL1);
  Send_To_Arduino();
  textSize(25);
  textAlign(CENTER);
  text(_LOADCELL1, 804, 155);
  text(_LOADCELL2, 804, 229);
  text(_LOADCELL3, 804, 302);
  text(_LOADCELL4, 804, 376);
  text(sum_loadcell, 804, 449);
}
public void setup_button() {

  noStroke();
  //-------------------------------------Btn Start
  btn_single = cp5.addButton("btn_single_graph")
    .setValue(0)
    .setId(41)
    .setPosition(14, 521)
    .setSize(144, 63)
    .setFont(createFont("verdana", 16))
    ;
  btn_single.getColor().setBackground(color(0xff993B85, 8))
    ;
  btn_single.setCaptionLabel("Single Graph")
    ;

  btn_4graph = cp5.addButton("btn_4graph")
    .setValue(0)
    .setId(42)
    .setPosition(174, 521)
    .setSize(144, 63)
    .setFont(createFont("verdana", 16))
    ;
  btn_4graph.getColor().setBackground(color(0xff993B85, 8))
    ;
  btn_4graph.setCaptionLabel("Multi Graph")
    ;

  btn_calibrate = cp5.addButton("btn_calibrate")
    .setValue(0)
    .setId(43)
    .setPosition(334, 521)
    .setSize(144, 63)
    .setFont(createFont("verdana", 16))
    ;
  btn_calibrate.getColor().setBackground(color(0xff993B85, 8))
    ;
  btn_calibrate.setCaptionLabel("Zero")
    ;

  btn_graph = cp5.addButton("btn_graph")
    .setValue(0)
    .setId(44)
    .setPosition(494, 521)
    .setSize(144, 63)
    .setFont(createFont("verdana", 16))
    ;
  btn_graph.getColor().setBackground(color(0xff993B85, 8))
    ;
  btn_graph.setCaptionLabel("stop")
    ;

  btn_record = cp5.addButton("btn_record")
    .setValue(0)
    .setId(45)
    .setPosition(654, 521)
    .setSize(144, 63)
    .setFont(createFont("verdana", 16))
    ;
  btn_record.getColor().setBackground(color(0xff993B85, 8))
    ;
  btn_record.setCaptionLabel("Record")
    ;
  //-----------------------------------------------------------------------------------

  btn_loadcell1 = cp5.addButton("btn_loadcell1")
    .setValue(0)
    .setId(51)
    .setPosition(14, 155)
    .setSize(144, 63)
    .setFont(createFont("verdana", 16))
    ;
  btn_loadcell1.getColor().setBackground(color(0xff993B85, 8))
    ;
  btn_loadcell1.setCaptionLabel("Loadcell 1");
  btn_loadcell1.setVisible(false);

  //----------------------------------------------------------------------------------- 1

  btn_loadcell2 = cp5.addButton("btn_loadcell2")
    .setValue(0)
    .setId(52)
    .setPosition(14, 232)
    .setSize(144, 63)
    .setFont(createFont("verdana", 16))
    ;
  btn_loadcell2.getColor().setBackground(color(0xff993B85, 8))
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
  btn_loadcell3.getColor().setBackground(color(0xff993B85, 8))
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
  btn_loadcell4.getColor().setBackground(color(0xff993B85, 8))
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
  btn_rst.getColor().setBackground(color(0xff993B85, 8))
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
  btn_clbr.getColor().setBackground(color(0xff993B85, 8))
    ;
  btn_clbr.setCaptionLabel("Calibrate");
  ;
  btn_clbr.setVisible(false);
  //----------------------------------------------------------------------------------- calibrate
}
//void controlEvent(ControlEvent theControlEvent)
//{
//  if (theControlEvent.isController()) {
//    println(theControlEvent.getController().getName());
//    println(theControlEvent.getController().getId());
//    String controllerName = theControlEvent.getController().getName();
//    int getID=theControlEvent.getController().getId();
//    if (controllerName=="portComList") {
//      println("smile");
//      InitSerial(theControlEvent.getController().getValue());
//      return;
//    }

//    if (getID == 41) {
//      hideAll();
//      graph_master.setVisible(true);
//      img_master = loadImage("bg.png");
//      //if (serial_conect!=0)
//      //  serial.write("{\"id\":1}");
//      //println("{\"id\":1}");
//    } else if (getID == 42) {
//      hideAll();
//      graph_loadcell1.setVisible(true);
//      //graph_loadcell2.setVisible(true);
//      //graph_loadcell3.setVisible(true);
//      //graph_loadcell4.setVisible(true);
//      img_master = loadImage("multi_graph.png");
//      //if (serial_conect!=0)
//      //  serial.write("{\"id\":42}");
//      //println("{\"id\":42}");
//    } else if (getID == 43) {
//      //hideAll();
//      //btn_loadcell1.setVisible(true);
//      //btn_loadcell2.setVisible(true);
//      //btn_loadcell3.setVisible(true);
//      //btn_loadcell4.setVisible(true);
//      //btn_rst.setVisible(true);
//      //btn_clbr.setVisible(true);
//      //txt_actual_mass.setVisible(true);
//      //txt_current_mass.setVisible(true);
//      //txt_gain.setVisible(true);
//      //img_master = loadImage("calibrate_loadcell.png");
//      //if (serial_conect!=0) {
//      //  serial.write("{\"id\":5,\"lc\":1}");
//      //  txt_gain.setValue("1232");
//      if (serial_conect!=0)
//        serial.write("{\"id\":43}");
//      println("{\"id\":43}");
//    } else if (getID == 44) {//tare
//      //if (serial_conect!=0)
//      //  serial.write("{\"id\":53,\"lc\":1}");
//      //println("{\"id\":53,\"lc\":1}");
//    } else if (getID == 45) {
//      if (serial_conect==0)
//        return;
//      if (btn_graph.getLabel() == "stop") {
//        print("record");
//        alpha_millis = millis();
//        table = new Table();
//        table.addColumn("time");
//        table.addColumn("lc1");
//        table.addColumn("lc2");
//        table.addColumn("lc3");
//        table.addColumn("lc4");
//        btn_graph.setLabel("recording");
//        is_recording = true;
//      } else {
//        btn_graph.setLabel("stop");
//        int d = day();    // Values from 1 - 31
//        int m = month();  // Values from 1 - 12
//        int y = year();   // 2003, 2004, 2005, etc.
//        int a = hour();
//        int b = minute();
//        int c = second();

//        String fname = "LC"+String.valueOf(d)+String.valueOf(m)+String.valueOf(y)+"-"+String.valueOf(a)+String.valueOf(b)+String.valueOf(c);
//        println(fname);
//        saveTable(table, "data/"+fname+".csv");
//        is_recording = false;
//      }
//    } else if (getID == 51) {
//      if (serial_conect!=0)
//        serial.write("{\"id\":5,\"lc\":1}");
//      println("{\"id\":5,\"lc\":1}");
//    } else if (getID == 52) {
//      if (serial_conect!=0)
//        serial.write("{\"id\":5,\"lc\":2}");
//      println("{\"id\":5,\"lc\":2}");
//    } else if (getID == 53) {
//      if (serial_conect!=0)
//        serial.write("{\"id\":5,\"lc\":3}");
//      println("{\"id\":5,\"lc\":3}");
//    } else if (getID == 54) {
//      if (serial_conect!=0)
//        serial.write("{\"id\":5,\"lc\":4}");
//      println("{\"id\":5,\"lc\":4}");
//    } else if (getID == 55) {
//      if (serial_conect!=0)
//        serial.write("{\"id\":51,\"lc\":" + lc_idx + "}");
//      println("{\"id\":51,\"lc\":" + lc_idx + "}");
//    } else if (getID == 56) { //calibrating
//      if (serial_conect!=0) {
//        float real_mass = float(txt_actual_mass.getText());
//        float curr_mass = float(txt_current_mass.getText());
//        float gain = curr_mass / real_mass;
//        serial.write("{\"id\":52,\"lc\":" + lc_idx + ",\"gain\":"+gain+"}");
//      }
//    }
//  }
//}

//void hideAll() {
//  graph_master.setVisible(false);
//  graph_loadcell1.setVisible(false);
//  graph_loadcell2.setVisible(false);
//  graph_loadcell3.setVisible(false);
//  graph_loadcell4.setVisible(false);
//  btn_loadcell1.setVisible(false);
//  btn_loadcell2.setVisible(false);
//  btn_loadcell3.setVisible(false);
//  btn_loadcell4.setVisible(false);
//  btn_rst.setVisible(false);
//  btn_clbr.setVisible(false);
//  txt_actual_mass.setVisible(false);
//  txt_current_mass.setVisible(false);
//  txt_gain.setVisible(false);
//}
public void setup_graph() {
  graph_master = cp5.addChart("loadcell_master")
    .setPosition(22, 118)
    .setSize(686, 368)
    .setRange(0, 10000)
    .setView(Chart.LINE) // use Chart.LINE, Chart.PIE, Chart.AREA, Chart.BAR_CENTERED
    ;
  graph_master.getColor().setBackground(color(0xff003366));
  graph_master.setCaptionLabel(" ");

  graph_master.addDataSet("loadcell_m1");
  graph_master.setColors("loadcell_m1", color(yellow_), color(yellow_));
  graph_master.updateData("loadcell_m1", new float[1000]);
  
  graph_master.addDataSet("loadcell_m2");
  graph_master.setColors("loadcell_m2", color(green_), color(green_));
  graph_master.updateData("loadcell_m2", new float[1000]);
  
  graph_master.addDataSet("loadcell_m3");
  graph_master.setColors("loadcell_m3", color(blue_), color(blue_));
  graph_master.updateData("loadcell_m3", new float[1000]);
  
  graph_master.addDataSet("loadcell_m4");
  graph_master.setColors("loadcell_m4", color(pink_), color(pink_));
  graph_master.updateData("loadcell_m4", new float[1000]);
  //--------------------------------------------------------------------------------------------------
  
  graph_loadcell1 = cp5.addChart("graph_loadcell_1")
    .setPosition(22, 118)
    .setSize(686, 368)
    .setRange(0, 10000)
    .setView(Chart.LINE) // use Chart.LINE, Chart.PIE, Chart.AREA, Chart.BAR_CENTERED
    ;
  graph_loadcell1.getColor().setBackground(color(0xff003366));
  graph_loadcell1.setCaptionLabel(" ");

  graph_loadcell1.addDataSet("loadcell_1");
  graph_loadcell1.setColors("loadcell_1", color(yellow_), color(yellow_));
  graph_loadcell1.updateData("loadcell_1", new float[1000]);
  graph_loadcell1.setVisible(false);
  //-------------------------------------------------------------------------------------------------- 1
  
  graph_loadcell2 = cp5.addChart("graph_loadcell_2")
    .setPosition(392, 118)
    .setSize(315, 180)
    .setRange(0, 10000)
    .setView(Chart.LINE) // use Chart.LINE, Chart.PIE, Chart.AREA, Chart.BAR_CENTERED
    ;
  graph_loadcell2.getColor().setBackground(color(0xff003366));
  graph_loadcell2.setCaptionLabel(" ");

  graph_loadcell2.addDataSet("loadcell_2");
  graph_loadcell2.setColors("loadcell_2", color(yellow_), color(yellow_));
  graph_loadcell2.updateData("loadcell_2", new float[1000]);
  graph_loadcell2.setVisible(false);
  //-------------------------------------------------------------------------------------------------- 2
  
  graph_loadcell3 = cp5.addChart("graph_loadcell_3")
    .setPosition(49, 302)
    .setSize(315, 180)
    .setRange(0, 10000)
    .setView(Chart.LINE) // use Chart.LINE, Chart.PIE, Chart.AREA, Chart.BAR_CENTERED
    ;
  graph_loadcell3.getColor().setBackground(color(0xff003366));
  graph_loadcell3.setCaptionLabel(" ");

  graph_loadcell3.addDataSet("loadcell_3");
  graph_loadcell3.setColors("loadcell_3", color(yellow_), color(yellow_));
  graph_loadcell3.updateData("loadcell_3", new float[1000]);
  graph_loadcell3.setVisible(false);
  //-------------------------------------------------------------------------------------------------- 3
  
  graph_loadcell4 = cp5.addChart("graph_loadcell_4")
    .setPosition(392, 302)
    .setSize(315, 180)
    .setRange(0, 10000)
    .setView(Chart.LINE) // use Chart.LINE, Chart.PIE, Chart.AREA, Chart.BAR_CENTERED
    ;
  graph_loadcell4.getColor().setBackground(color(0xff003366));
  graph_loadcell4.setCaptionLabel(" ");

  graph_loadcell4.addDataSet("loadcell_4");
  graph_loadcell4.setColors("loadcell_4", color(yellow_), color(yellow_));
  graph_loadcell4.updateData("loadcell_4", new float[1000]);
  graph_loadcell4.setVisible(false);
  //-------------------------------------------------------------------------------------------------- 4
  
  
}
int posxcom = 700;
int posycom = -45;

public String shortifyPortName(String portName, int maxlen)  
{
  String shortName = portName;
  if (shortName.startsWith("/dev/")) shortName = shortName.substring(5);  
  if (shortName.startsWith("tty.")) shortName = shortName.substring(4); // get rid off leading tty. part of device name
  if (portName.length()>maxlen) shortName = shortName.substring(0, (maxlen-1)/2) + "~" +shortName.substring(shortName.length()-(maxlen-(maxlen-1)/2));
  if (shortName.startsWith("cu.")) shortName = "";// only collect the corresponding tty. devices
  return shortName;
}

public void setup_UART()
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
  fill(0xff000000);
  commListbox.addItem("Close Comm", ++commListMax); // addItem(name,value)
  // text label for which comm port selected
  txtlblWhichcom = cp5.addTextlabel("txtlblWhichcom", "No Port Selected", posxcom+5, posycom+62); // textlabel(name,text,x,y)
}

public void Send_To_Arduino()
{
  if (mode == 0) {
    if (serial_conect==1) 
    {  

      //indicator connect
      fill(0, 255, 0);
      ellipse(posxcom+130, posycom+67, 10, 10);
      noFill();
      //
      //    if (TuningPID==true) {
      //      Tuning_PID_param();
      //    }
    } else 
    {//indicator no connect
      fill(128, 0, 0);
      ellipse(posxcom+130, posycom+67, 10, 10);
      noFill();
    }
  }
}

public void InitSerial(float portValue) 
{
  if (portValue < commListMax) {
    String portPos = Serial.list()[PApplet.parseInt(portValue)];
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
}
int x = 0;
public void serialEvent (Serial usbPort) 
{

  try {
    String usbString = usbPort.readStringUntil ('\n');

    if (usbString != null) 
    {
      usbString = trim(usbString);
      json = parseJSONObject(usbString);
      println(usbString); //--> for debuging


      int id = json.getInt("id");
      if (id == 1) {
        _LOADCELL1 = json.getFloat("l1");
        _LOADCELL2 = json.getFloat("l2");
        _LOADCELL3 = json.getFloat("l3");
        _LOADCELL4 = json.getFloat("l4");
        graph_master.push("loadcell_m1", _LOADCELL1);
        graph_master.push("loadcell_m2", _LOADCELL2);
        graph_master.push("loadcell_m3", _LOADCELL3);
        graph_master.push("loadcell_m4", _LOADCELL4);
        sum_loadcell = _LOADCELL1 + _LOADCELL2 + _LOADCELL3 + _LOADCELL4;
        graph_loadcell1.push("loadcell_1", sum_loadcell);

        

        if (is_recording == true) {
          TableRow newRow = table.addRow();
          newRow.setLong("time", millis() - alpha_millis);
          newRow.setFloat("lc1", _LOADCELL1);
          newRow.setFloat("lc2", _LOADCELL2);
          newRow.setFloat("lc3", _LOADCELL3);
          newRow.setFloat("lc4", _LOADCELL4);
        }
      } else if (id == 5) {
        lc_idx = json.getInt("lc");
        txt_gain.setValue(String.valueOf(json.getFloat("gain"))); 
        txt_current_mass.setValue(String.valueOf(json.getFloat("mass")));
      }
    }

    //float data[] = float(split(usbString, ','));
    //for (int sensorNum = 1; sensorNum < data.length; sensorNum++) { println(sensorNum + " " + data[sensorNum]);  } //--> for debuging
    /*
    Format paket
     HEADER#DATA1#DATA2#DATA3 ...
     24 = data grafik
     24#PRESSURE#FLOW#VOLUME
     */
    //PRESSURE_=data[1];
    //FLOW_=data[2];
    //VOLUME_=data[0];
    // exhale_time=data[4];

    //if (data[0] == 59)
    //{
    //  _LOADCELL1 = data[1];
    //  _LOADCELL2 = data[2];
    //  _LOADCELL3 = data[3];
    //  _LOADCELL4 = data[4];
    //  println(_LOADCELL1);
    //}
  }
  catch(RuntimeException e)
  {
    println("Serial event is not null");
    println(e);
  }
}

public void setup_textfield() {
  txt_actual_mass = cp5.addTextfield("actual_mass")
    .setPosition(469, 155)
    .setSize(329, 64)
    .setFont(createFont("verdana", 30))
    .setAutoClear(false)
    .setCaptionLabel(" ")
    ;
  txt_actual_mass.setVisible(false);

  txt_current_mass = cp5.addTextfield("Current_mass")
    .setPosition(469, 232)
    .setSize(329, 64)
    .setFont(createFont("verdana", 30))
    .setAutoClear(false)
    .setCaptionLabel(" ")
    ;
  txt_current_mass.setVisible(false);

  txt_gain = cp5.addTextfield("k_gain")
    .setPosition(469, 309)
    .setSize(329, 64)
    .setFont(createFont("verdana", 30))
    .setAutoClear(false)
    .setCaptionLabel(" ")
    .setUpdate(true);
    ;
  txt_gain.setVisible(false);
}
  public void settings() {  size(900, 600); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "alat_uji_tekan" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
