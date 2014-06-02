/*
 * Copyright (C) 2014  Fang0716
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package git.meowinnovation.inputfix.client;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.StringUtils;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.ButtonGroup;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.Mod.EventHandler;

import org.lwjgl.opengl.Display;

public class InputWindow extends JFrame {
	final static JFrame frame = new JFrame("Input Window");
	final static JTextArea comp = new JTextArea();
	final static JScrollPane scroll = new JScrollPane(comp);

	public static void showGUI(String text) {
		frame.getContentPane().add(scroll , BorderLayout.CENTER);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		if (text.equalsIgnoreCase("/")) {
			comp.setText(text);
			comp.setSelectionStart(1);
			comp.setSelectionEnd(1);
		}
		frame.setSize(Display.getWidth(), 25);
		frame.setUndecorated(true);
		frame.setLocation(Display.getX(), Display.getY() + Display.getHeight()
				- 5);
		frame.setAlwaysOnTop(true);
		frame.setVisible(true);

		FMLClientHandler.instance().getClient().setIngameNotInFocus();
		comp.requestFocus();

		KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
		KeyStroke esc = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
		InputMap inputmap = comp.getInputMap();
		ActionMap actionmap = comp.getActionMap();

		Action enteraction = new AbstractAction() {

			private Minecraft mc;

			@Override
			public void actionPerformed(ActionEvent event) {
				if (!StringUtils.isNullOrEmpty(comp.getText())) {
					FMLClientHandler.instance().getClient().thePlayer
							.sendChatMessage(comp.getText());
					comp.setText("");
					frame.dispose();
					FMLClientHandler.instance().getClient().thePlayer
							.closeScreen();
				} else {
					comp.setText("");
					frame.dispose();
					FMLClientHandler.instance().getClient().thePlayer
							.closeScreen();
				}

			}

		};

		Action escaction = new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent event) {
				comp.setText("");
				frame.dispose();
				FMLClientHandler.instance().getClient().thePlayer.closeScreen();
			}

		};

		inputmap.put(enter, "enter");
		inputmap.put(esc, "esc");
		actionmap.put("enter", enteraction);
		actionmap.put("esc", escaction);

	}

	public static void closeframe() {
		frame.dispose();
	}

}
