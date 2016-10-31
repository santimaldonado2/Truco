package com.company.ui;

import javax.swing.*;

public class SeleccionCarta extends JDialog {
    private JPanel contentPane;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton buttonOK;

    public SeleccionCarta() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
    }

    public static void main(String[] args) {
        SeleccionCarta dialog = new SeleccionCarta();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
