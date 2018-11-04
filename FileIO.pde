String[] lines;
String[] element; //elements of each line
char splitter = '#'; //character that splits strings

void loadRoster() { //load information from roster.txt
  lines = loadStrings("roster.txt");
  for (int i = 0; i < lines.length; i++) {
    element = split(lines[i], splitter);
    
    //initialize obect student:
    student.add(new Profile(element[0]));
    student.get(student.size()-1).review = element[1];
    student.get(student.size()-1).personality = element[2];
    student.get(student.size()-1).cheatingFlags = int(element[3]);
    student.get(student.size()-1).workRating = int(element[4]);
    
    loadButton(student.get(student.size()-1).name);
  }
}

void saveRoster() { //save information to roster.txt
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

void loadButton(String n) {
  pTabs.add(new GPanel(this, 10, yPos, tabWidth, tabHeight, n));
  panel.addControl(pTabs.get(student.size()-1));
  tabButt.add(new GButton(this, 0, 20, tabWidth-10, tabHeight-10, n));
  pTabs.get(student.size()-1).addControl(tabButt.get(student.size()-1));
  //System.out.println(n);
  
  yPos += tabHeight + 20;
}

void uploadRosterToServer() {
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

void downloadRosterFromServer() {
  String[] text = new String[1];
  println("c.readString() = " + c.readString());
  if (c.available() > 0) {
    text[0] = c.readString();
    saveStrings("roster.txt", text);
  } else {
    println("c not available");
  }
  
}
