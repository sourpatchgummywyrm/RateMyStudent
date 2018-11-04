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
