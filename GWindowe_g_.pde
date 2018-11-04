import processing.net.*;
import java.awt.*;
import g4p_controls.*;

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

void setup() {
 size(1440, 800);
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

void draw() {
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

void createNewButton() {
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

void keyPressed() {
 switch(key) {
   case 'u':
   uploadRosterToServer(); break;
   case 'd':
   downloadRosterFromServer(); break;

 }
}
