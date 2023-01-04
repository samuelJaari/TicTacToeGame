import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Button extends javax.swing.JButton {
        public int i;   // The row and column coordinate of the button in a GridLayout
        public int j;
        public Button (int x, int y) {
            // Create a JButton with a blank icon. This also gives the button its correct size.
            super();
            super.setIcon(new javax.swing.ImageIcon(getClass().getResource("None.png")));
            this.i = x;
            this.j = y;
        }

        // Return row coordinate
        public int getRow () {
            return i;
        }

        // Return column coordinate
        public int getCol() {
            return j;
        }

}
