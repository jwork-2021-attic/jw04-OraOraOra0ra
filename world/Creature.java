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
package world;

import screen.WinScreen;

import java.awt.Color;

/**
 *
 * @author Aeranythe Echosong
 */
public class Creature {

    private World world;

    private int x;

    public void setX(int x) {
        this.x = x;
    }

    public int x() {
        return x;
    }

    private int y;

    public void setY(int y) {
        this.y = y;
    }

    public int y() {
        return y;
    }

    private char glyph;

    public char glyph() {
        return this.glyph;
    }

    private Color color;

    public Color color() {
        return this.color;
    }

    private CreatureAI ai;

    public void setAI(CreatureAI ai) {
        this.ai = ai;
    }

    public int[][] maze = MazeBuilder.mazeArray();

    /*private int maxHP;

    public int maxHP() {
        return this.maxHP;
    }

    private int hp;

    public int hp() {
        return this.hp;
    }

    public void modifyHP(int amount) {
        this.hp += amount;

        if (this.hp < 1) {
            world.remove(this);
        }
    }

    private int attackValue;

    public int attackValue() {
        return this.attackValue;
    }

    private int defenseValue;

    public int defenseValue() {
        return this.defenseValue;
    }*/

    private int visionRadius;

    public int visionRadius() {
        return this.visionRadius;
    }

    /*public boolean canSee(int wx, int wy) {
        return ai.canSee(wx, wy);
    }*/

    public Tile tile(int wx, int wy) {
        return world.tile(wx, wy);
    }

    public void dig(int wx, int wy) {
        world.dig(wx, wy);
    }

    public void moveBy(int mx, int my) {
        Creature other = world.creature(x + mx, y + my);
        if ((x+mx) < 0 && mx == -1) //| (x+mx) > world.width() | (y+my) < 0 | (y+mx) > world.height() ) {
            return;
        else if ((x+mx) >= world.width() && mx == 1)
            return;
        else if ((y+my) < 0 && my == -1)
            return;
        else if ((y+my) >= world.height() && my == 1)
            return;

        if (maze[x+mx][y+my]==0) {
            world.setTile(x+mx,y+my,Tile.WALLBB);
            ai.onEnter(0, 0, world.tile(0, 0));
        }
        else {
            world.setTile(x+mx,y+my,Tile.FLOORG);
            ai.onEnter(x + mx, y + my, world.tile(x + mx, y + my));
        }
    }

    public void update() {
        this.ai.onUpdate();
    }

    public boolean canEnter(int x, int y) {
        return world.tile(x, y).isGround();
    }

    public void notify(String message, Object... params) {
        ai.onNotify(String.format(message, params));
    }

    public Creature(World world, char glyph, Color color, /*int HP, int attack, int defense,*/ int visionRadius) {
        this.world = world;
        this.glyph = glyph;
        this.color = color;
        this.visionRadius = visionRadius;
    }
}
