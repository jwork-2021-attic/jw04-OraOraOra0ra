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

import java.util.List;

import asciiPanel.AsciiPanel;

/**
 *
 * @author Aeranythe Echosong
 */
public class CreatureFactory {

    private World world;

    public CreatureFactory(World world) {
        this.world = world;
    }

    public Creature newPlayer1(List<String> messages) {
        Creature player1 = new Creature(this.world, (char)2, AsciiPanel.brightRed, 100);
        world.myLocation(player1,0,0);
        new PlayerAI(player1, messages);
        return player1;
    }

    public Creature newPlayer2(List<String> messages) {
        Creature player2= new Creature(this.world, (char)2, AsciiPanel.brightBlue, 100);
        world.myLocation(player2,0,0);
        new PlayerAI(player2, messages);
        return player2;
    }

    public Creature newFungus() {
        Creature fungus = new Creature(this.world, (char)3, AsciiPanel.green, 10);
        world.addAtEmptyLocation(fungus);
        new FungusAI(fungus, this);
        return fungus;
    }

    public Creature Athena() {
        Creature Athena = new Creature(this.world, (char)12, AsciiPanel.brightYellow, 100);
        world.myLocation(Athena,this.world.width()-1,this.world.height()-1);
        new CreatureAI(Athena);
        return Athena;
    }

}
