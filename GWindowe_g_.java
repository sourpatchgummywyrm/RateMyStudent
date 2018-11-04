import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.net.*; 
import java.awt.*; 
import g4p_controls.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class GWindowe_g_ extends PApplet {





Client c;

GPanel panel;
GTextField txf;
GTextArea txa;
GButton newStudent;

ArrayList<GPanel> pTabs = new ArrayList<GPanel>();
ArrayList<GButton> tabButt = new ArrayList<GButton>();
Font font;
int tempCheat, tempWork;
char p0, p1, p2, p3;
String persona = "";
String tempName, tempReview;

GSlider scrollBar;
int yPos, yScroll;
int tabWidth = 450, tabHeight = 80;
int panelHeight = 1200;

ArrayList<Profile> student = new ArrayList<Profile>();

//GWindow newStudentWindow;

public void setup() {
 
 c = new Client(this, "172.16.241.57", 1234);
 font = new Font("consola.ttf", Font.ITALIC | Font.BOLD, 20);
 yPos = 40;
 yScroll = 20;
 surface.setResizable(true);

 panel = new GPanel(this, 10, yScroll, 950, panelHeight, "Student Roster");
 panel.setFont(font);
 panel.setCollapsible(false);

 newStudent = new GButton(this, 1000, yPos, tabWidth/2, tabHeight, "Add New Student");
 newStudent.setFont(font);
 
 scrollBar = new GSlider(this, 1025, 15, height-20, 100, 18);
 scrollBar.setRotation(PI/2);
 scrollBar.setValue(0);

 loadRoster();

 panel.setDraggable(false);
}

public void draw() {
 background(100);
}

//------------------------ HANDLE EVENTS ------------------------

public void handleButtonEvents(GButton button, GEvent event) { //------------------BUTTON HANDLING

 //Main Page Buttons:
 for (int i = 0; i < student.size(); i ++) {
   if(button == tabButt.get(i) && event == GEvent.CLICKED){
     createStudentWindow(i);
     println("Button "+ student.get(i).name + " was clicked");
   }
 }

 //Add New Student Buttons:
 if (button == newStudent && event == GEvent.CLICKED) createWindowForNewStudent();
 if (button ==  f0 && event == GEvent.CLICKED) tempCheat = 0;
 if (button ==  f1 && event == GEvent.CLICKED) tempCheat = 1;
 if (button ==  f2 && event == GEvent.CLICKED) tempCheat = 2;
 if (button ==  f3 && event == GEvent.CLICKED) tempCheat = 3;
 if (button ==  f4 && event == GEvent.CLICKED) tempCheat = 4;
 if (button ==  p[0] && event == GEvent.CLICKED) p0 = 'I';
 if (button ==  p[1] && event == GEvent.CLICKED) p0 = 'E';
 if (button ==  p[2] && event == GEvent.CLICKED) p1 = 'S';
 if (button ==  p[3] && event == GEvent.CLICKED) p1 = 'N';
 if (button ==  p[4] && event == GEvent.CLICKED) p2 = 'T';
 if (button ==  p[5] && event == GEvent.CLICKED) p2 = 'F';
 if (button ==  p[6] && event == GEvent.CLICKED) p3 = 'J';
 if (button ==  p[7] && event == GEvent.CLICKED) p3 = 'P';

 for (int i = 0; i < 5; i ++) {
   if (button == workAmount[i] && event == GEvent.CLICKED) {
     tempWork = i+1;
     println("workAmount " + i);
   }
 }

 if(button == add && event == GEvent.CLICKED) { //if add new profile, new profile is added
   student.add(new Profile(name.getText()));
   createNewButton();

   student.get(student.size()-1).personality = persona;
   student.get(student.size()-1).cheatingFlags = tempCheat;
   student.get(student.size()-1).review = review.getText();
   student.get(student.size()-1).workRating = tempWork;
 }
}

public void createNewButton() {
 pTabs.add(new GPanel(this, 10, yPos, tabWidth, tabHeight, name.getText()));
 panel.addControl(pTabs.get(student.size()-1));
 tabButt.add(new GButton(this, 0, 20, tabWidth-10, tabHeight-10, name.getText()));
 pTabs.get(student.size()-1).addControl(tabButt.get(student.size()-1));
 System.out.println(name.getText());
 persona = ""+p0+p1+p2+p3;

 yPos += tabHeight + 30;

 saveRoster();
}

//------------------------------------------------------------------------------ END BUTTON HANDLING

public void handleSliderEvents(GValueControl slider, GEvent event) { //scroll bar control
 panel.moveTo(10, -(slider.getValueF() * (panelHeight - height+10)), GControlMode.CORNER);
}

public void keyPressed() {
 switch(key) {
   case 'u':
   uploadRosterToServer(); break;
   case 'd':
   downloadRosterFromServer(); break;

 }
}
String[] lines;
String[] element; //elements of each line
char splitter = '#'; //character that splits strings

public void loadRoster() { //load information from roster.txt
  lines = loadStrings("roster.txt");
  for (int i = 0; i < lines.length; i++) {
    element = split(lines[i], splitter);
    
    //initialize obect student:
    student.add(new Profile(element[0]));
    student.get(student.size()-1).review = element[1];
    student.get(student.size()-1).personality = element[2];
    student.get(student.size()-1).cheatingFlags = PApplet.parseInt(element[3]);
    student.get(student.size()-1).workRating = PApplet.parseInt(element[4]);
    
    loadButton(student.get(student.size()-1).name);
  }
}

public void saveRoster() { //save information to roster.txt
  lines = new String[student.size()];
  for (int i = 0; i < lines.length; i++) {
    lines[i] = student.get(i).name + str(splitter) + 
               student.get(i).review + str(splitter) +
               student.get(i).personality + str(splitter) +
               str(student.get(i).cheatingFlags) + str(splitter) +
               str(student.get(i).workRating) + str(splitter);
  }
  
  saveStrings("roster.txt", lines);
}

public void loadButton(String n) {
  pTabs.add(new GPanel(this, 10, yPos, tabWidth, tabHeight, n));
  panel.addControl(pTabs.get(student.size()-1));
  tabButt.add(new GButton(this, 0, 20, tabWidth-10, tabHeight-10, n));
  pTabs.get(student.size()-1).addControl(tabButt.get(student.size()-1));
  //System.out.println(n);
  
  yPos += tabHeight + 20;
}

public void uploadRosterToServer() {
  String text = "";
  for (int i = 0; i < lines.length; i ++) {
    lines[i] = student.get(i).name + str(splitter) + 
               student.get(i).review + str(splitter) +
               student.get(i).personality + str(splitter) +
               str(student.get(i).cheatingFlags) + str(splitter) +
               str(student.get(i).workRating) + str(splitter) + "\n";
    text += lines[i];
  }
  c.write(text);
}

public void downloadRosterFromServer() {
  String[] text = new String[1];
  println("c.readString() = " + c.readString());
  if (c.available() > 0) {
    text[0] = c.readString();
    saveStrings("roster.txt", text);
  } else {
    println("c not available");
  }
  
}
class Profile {
  String name;
  int id;
  String personality;
  int cheatingFlags;
  String review;
  int workRating;
  
  public Profile(String n) {
    name = n;
    
  }
  
}
//Create New Student Window
GWindow newStudentWin;
GLabel nLabel, rLabel, cheatRate, personality;
GButton f0, f1, f2, f3, f4, add;
GButton[] workAmount = new GButton[5];
GButton[] p = new GButton[8];
GTextArea review, name;

//Access Profile Window
GWindow viewProfileWin;
GLabel cheatRatingLabel, workLabel, personalityLabel;
GTextArea reviewLabel;

public void createWindowForNewStudent() {
  newStudentWin = GWindow.getWindow(this, "Add New Student", 50, 50, 800, 600, JAVA2D);
  PApplet app = newStudentWin;
  //newStudentWin.setActionOnClose(G4P.CLOSE_WINDOW);
  
  nLabel = new GLabel(app, 10, 10, 120, 26, "New Student Name: ");
  name = new GTextArea(app, 10, 30, 150, 40);
  rLabel = new GLabel(app, 10, 70, 120, 26, "Personal Review:");
  review = new GTextArea(app, 10, 90, 300, 100);
  
  cheatRate = new GLabel(app, 10, 200, 120, 25, "Cheating Chance: ");
  f0 = new GButton(app, 10, 230, 50, 25, "0%");
  f1 = new GButton(app, 60, 230, 50, 25, "25%");
  f2 = new GButton(app, 110, 230, 50, 25, "50%");
  f3 = new GButton(app, 160, 230, 50, 25, "75%");
  f4 = new GButton(app, 210, 230, 50, 25, "100%");
  personality = new GLabel(app, 10, 270, 120, 25, "Personality Type: ");
  p[0] = new GButton(app, 10, 300, 100, 25, "Introverted");
  p[1] = new GButton(app, 130, 300, 100, 25, "Extroverted");
  p[2] = new GButton(app, 10, 330, 100, 25, "Sensing");
  p[3] = new GButton(app, 130, 330, 100, 25, "Intuitive");
  p[4] = new GButton(app, 10, 360, 100, 25, "Thinking");
  p[5] = new GButton(app, 130, 360, 100, 25, "Feeling");
  p[6] = new GButton(app, 10, 390, 100, 25, "Judging");
  p[7] = new GButton(app, 130, 390, 100, 25, "Percieving");
  
  for (int i = 0; i < 5; i ++) {
    workAmount[i] = new GButton(app, 350 + i*50, 300, 25, 25, str(i+1)); 
  }
  add = new GButton(app, 10, 500, 100, 100, "Add Student");
  newStudentWin.addDrawHandler(this, "drawController");
}

public void drawController(PApplet appc, GWinData data) {
  appc.background(227, 230, 255);
}

//------------------------------------------------------------------------

public void createStudentWindow(int index) {
  viewProfileWin = GWindow.getWindow(this, student.get(index).name + "'s Profile", 50, 50, 800, 600, JAVA2D);
  PApplet app = viewProfileWin;
  cheatRatingLabel = new GLabel(app, 10, 10, 120, 25, "Cheating Rating: \t" + student.get(index).cheatingFlags);
  reviewLabel =      new GTextArea(app, 10, 50, 200, 50, G4P.SCROLLBARS_VERTICAL_ONLY);
  reviewLabel.setText("Review: \t" + student.get(index).review);
  reviewLabel.setTextEditEnabled(false);
  personalityLabel = new GLabel(app, 10, 70, 120, 25, "Personality Type: \t" + student.get(index).personality);
  workLabel =        new GLabel(app, 10, 100, 120, 25, "Work Rating: \t" + student.get(index).workRating);
  
  viewProfileWin.addDrawHandler(this, "drawController");
}

public void handlePanelEvents(GPanel panel, GEvent event) { /* code */ }
  public void settings() {  size(1440, 800); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "GWindowe_g_" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
