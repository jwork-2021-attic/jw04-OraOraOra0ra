/*
 * Copyright (C) 2015 Aeranythe Echosong
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package screen;

import world.*;
import asciiPanel.AsciiPanel;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Aeranythe Echosong
 */
public class PlayScreen implements Screen {

    private World world;
    private Creature player1;
    private Creature player2;
    private Creature Athena;
    private int screenWidth;
    private int screenHeight;
    private List<String> messages;
    private List<String> oldMessages;
    public int[][] maze = MazeBuilder.mazeArray();

    public PlayScreen() {
        this.screenWidth = 10;
        this.screenHeight = 10;
        createWorld();
        this.messages = new ArrayList<String>();
        this.oldMessages = new ArrayList<String>();

        CreatureFactory creatureFactory = new CreatureFactory(this.world);
        createCreatures(creatureFactory);
    }

    private void createCreatures(CreatureFactory creatureFactory) {
        this.player1 = creatureFactory.newPlayer1(this.messages);
        this.player2 = creatureFactory.newPlayer2(this.messages);
        this.Athena = creatureFactory.Athena();
        /*for (int i = 0; i < 8; i++) {
            creatureFactory.newFungus();
        }*/
    }

    private void createWorld() {
        world = new WorldBuilder(10, 10).makeMaze().build();
    }

    private void displayTiles(AsciiPanel terminal, int left, int top) {
        // Show terrain
        for (int x = 0; x < screenWidth; x++) {
            for (int y = 0; y < screenHeight; y++) {
                int wx = x + left;
                int wy = y + top;

                terminal.write(world.glyph(wx, wy), x, y, world.color(wx, wy));

                /*if (player.canSee(wx, wy)) {
                    terminal.write(world.glyph(wx, wy), x, y, world.color(wx, wy));
                } else {
                    terminal.write(world.glyph(wx, wy), x, y, Color.DARK_GRAY);
                }*/
            }
        }
        // Show creatures
        /*for (Creature creature : world.getCreatures()) {
            if (creature.x() >= left && creature.x() < left + screenWidth && creature.y() >= top
                    && creature.y() < top + screenHeight) {
                if (player.canSee(creature.x(), creature.y())) {
                    terminal.write(creature.glyph(), creature.x() - left, creature.y() - top, creature.color());
                }
            }
        }*/
        // Creatures can choose their next action now
        world.update();
    }

    private void displayMessages(AsciiPanel terminal, List<String> messages) {
        int top = this.screenHeight - messages.size();
        for (int i = 0; i < messages.size(); i++) {
            terminal.write(messages.get(i), 1, top + i + 1);
        }
        this.oldMessages.addAll(messages);
        messages.clear();
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        // Terrain and creatures
        displayTiles(terminal, getScrollX(), getScrollY());
        // Player
        terminal.write(player1.glyph(), player1.x() - getScrollX(), player1.y() - getScrollY(), player1.color());
        terminal.write(player2.glyph(), player2.x() - getScrollX(), player2.y() - getScrollY(), player2.color());
        terminal.write(Athena.glyph(), Athena.x() - getScrollX(), Athena.y() - getScrollY(), Athena.color());
        // Messages
        displayMessages(terminal, this.messages);
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                player1.moveBy(-1, 0);
                break;
            case KeyEvent.VK_RIGHT:
                player1.moveBy(1, 0);
                break;
            case KeyEvent.VK_UP:
                player1.moveBy(0, -1);
                break;
            case KeyEvent.VK_DOWN:
                player1.moveBy(0, 1);
                break;
            case KeyEvent.VK_A:
                player2.moveBy(-1, 0);
                break;
            case KeyEvent.VK_D:
                player2.moveBy(1, 0);
                break;
            case KeyEvent.VK_W:
                player2.moveBy(0, -1);
                break;
            case KeyEvent.VK_S:
                player2.moveBy(0, 1);
                break;
        }

        if (judgeEnd()==1)
            return new WinScreen("player1");
        else if (judgeEnd()==2)
            return new WinScreen("player2");

        return this;
    }

    public int judgeEnd(){
        if (player1.x()==world.width()-1 && player1.y()==world.height()-1)
            return 1;
        else if (player2.x()==world.width()-1 && player2.y()==world.height()-1)
            return 2;
        return 0;
    }

    public int getScrollX() {
        return Math.max(0, Math.min(player1.x() - screenWidth / 2, world.width() - screenWidth));
    }

    public int getScrollY() {
        return Math.max(0, Math.min(player1.y() - screenHeight / 2, world.height() - screenHeight));
    }

}
