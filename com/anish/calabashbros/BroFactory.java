package com.anish.calabashbros;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BroFactory {
    private static Color[] colors_tmp;

    public BroFactory(Calabash[][] bros, int lines, World world) {
        List<Integer> list = randomList(lines * lines );
        colors_tmp = new Color[lines];
        for (int i = 0; i < lines; ++i) {
            double ans = ((double) 4 / lines) * i;
            int sw = (int) ans;
            ans = ans - sw;
            switch (sw) {
                case 0: {
                    colors_tmp[i] = new Color(255, (int) (ans * 255), 0);
                    break;
                }
                case 1: {
                    colors_tmp[i] = new Color((int) ((1 - ans) * 255), 255, 0);
                    break;
                }
                case 2: {
                    colors_tmp[i] = new Color(0, 255, (int) (ans * 255));
                    break;
                }
                case 3: {
                    colors_tmp[i] = new Color(0, (int) ((1 - ans) * 255), 255);
                    break;
                }
            }
        }

        for(int i = 0;i < lines * lines;++i) {
            int r = i/lines;
            int j = i%lines;
            bros[list.get(i)/lines][list.get(i)%lines] = new Calabash(new Color(colors_tmp[r].getRed() * (4 * lines - 3 * j) / lines / 4, colors_tmp[r].getGreen() * (4 * lines - 3 * j) / lines / 4,colors_tmp[r].getBlue() * (4 * lines - 3 * j) / lines / 4) , i ,world);

        }

        int p = 0;
        for(int i = 0;i < lines;++i, ++p) {
            int q = 0;
            for (int j = 0; j < lines; ++j, ++q) {
                world.put(bros[i][j],8 + i + p,8 + j + q);
            }
        }
    }


    public List randomList(int size) {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < size; i++)
            list.add(new Integer(i));
        Collections.shuffle(list);
        return list;
    }

}
