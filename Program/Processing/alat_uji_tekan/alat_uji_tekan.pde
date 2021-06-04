import processing.serial.Serial; // serial library
import controlP5.*; // controlP5 library

Table table, tableRotary;
Serial serial, serial2;
ControlP5 cp5;
PImage img_master;
Chart graph_master;

int serial_conect = 0;
int commListMax;

int serial_conect2 = 0;
int commListMax2;

Textlabel txtlblWhichcom; 
ListBox commListbox;
ListBox portlist;

Textlabel txtlblWhichcom2; 
ListBox commListbox2;
ListBox portlist2;
int mode = 0;
int mode2 = 0;
color yellow_ = color(200, 200, 20), green_ = color(46, 209, 2), red_ = color(120, 30, 30), blue_ = color (0, 102, 200);
color white_ = color(255, 255, 255), black_ = color(0, 0, 0), pink_ = color(255, 0, 255), grey_ = color(#636e72);

Button btn_up, btn_down, btn_home, btn_press, btn_stop;
Button btn_sethome, btn_record;


boolean is_recording = false;

float loadcell, rotary_encoder, last_rotary_encoder;
long alpha_millis;
String rotaryMode = "off";
JSONObject config;
void setup() {
  size(900, 600);
  img_master=loadImage("bg.png");
  cp5 = new ControlP5(this); 
  setup_graph();
  setup_UART();
  setup_UART2();
  setup_button();

}

void draw() {
  background(img_master);
  Send_To_Arduino();
  textAlign(CENTER, CENTER);
  fill(#FFFFFF);
  textSize(20);
  text(round(loadcell), 804, 302);
  text(round(rotary_encoder), 804, 376);
}
