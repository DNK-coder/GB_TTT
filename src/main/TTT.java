package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class TTT extends JComponent {

    public static final int FIELD_EMPTY = 0;//пустое поле
    public static final int FIELD_X = 10; // поле с крестиком
    public static final int FIELD_0 = 200; // поле с ноликом
    int[][] field; // объявляем наш массив игрового поля
    boolean isXturn;

    public TTT() {
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        field = new int[3][3]; // выделям память под массив при создании компонента
        initGame();
    }

    public void initGame() {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                field[i][j] = FIELD_EMPTY; // очищаем массив, заполняя его 0

            }
        }

        isXturn = true;
    }

    @Override
    protected void processMouseEvent(MouseEvent mouseEvent) {
        super.processMouseEvent(mouseEvent);
        if (mouseEvent.getButton() == MouseEvent.BUTTON1) {// проверяем, что нажата левая клавиша
            int x = mouseEvent.getX(); // координата x клика
            int y = mouseEvent.getY(); // координата у клика

// переводим координаты в индексы ячейки в массиве field
            int i = (int) ((float) x / getWidth() * 3);
            int j = (int) ((float) y / getHeight() * 3);
//проверям, что выбранная ячейка пуста и туда можно сходить
            if (field[i][j] == FIELD_EMPTY) {

//проверка чей ход, если Х - ставим крестик, если 0 - ставим нолик
                field[i][j] = isXturn ? FIELD_X : FIELD_0;
                isXturn = !isXturn; // меняем флаг хода.
                repaint(); // перерисовка компонента, это вызовет метод paintComponent()
                int res = checkState();
                if(res!=0){
                    if(res == FIELD_0 * 3) {
                        JOptionPane.showMessageDialog(this, "Выиграли нули", "Победа!", JOptionPane.INFORMATION_MESSAGE);
                    } else if(res == FIELD_X * 3) {
                        JOptionPane.showMessageDialog(this, "Выиграли кресты", "Победа!", JOptionPane.INFORMATION_MESSAGE);
                    }else {
                        JOptionPane.showMessageDialog(this, "Ничья", "Ничья!", JOptionPane.INFORMATION_MESSAGE);
                    }
                    initGame();
                    repaint();
                }
            }
        }
    }



    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.clearRect(0, 0, getWidth(), getHeight());
        drawGrid(graphics);
        drawXO(graphics);

    }


   void drawGrid(Graphics graphics) {
       int w = getWidth(); // ширина игрового поля
       int h = getHeight(); // высота игрового поля
       int dw = w / 3; // делим ширину на 3 - получаем ширину одной ячейки
       int dh = h / 3; // делим высоту на 3 - получаем высоту одной ячейки
       graphics.setColor(Color.BLUE); // цвет линий
       for (int i = 1; i < 3; i++) { // i пробегает значения от 1 до 2 включительно (при i = 3) выход из цикла
           graphics.drawLine(0, dh * i, w, dh * i); // горизонтальная линия
           graphics.drawLine(dw * i, 0, dw * i, h); // вертикальная линия

       }
   }
    void drawX(int i, int j, Graphics graphics) {
        graphics.setColor(Color.BLACK);
        int dw = getWidth() / 3;
        int dh = getHeight() / 3;
        int x = i * dw;
        int y = j * dh;
//линия от верхнего левого угла в правый нижний
        graphics.drawLine(x, y, x + dw, y + dh);
//линия от левого нижнего угла до правого верхнего
        graphics.drawLine(x, y + dh, x + dw, y);
    }

    void draw0(int i, int j, Graphics graphics) {
        graphics.setColor(Color.BLACK);

        int dw = getWidth() / 3;
        int dh = getHeight() / 3;

        int x = i * dw;
        int y = j * dh;


        graphics.drawOval(x + 5 * dw / 100, y, dw * 9 / 10, dh);
    }

    void drawXO(Graphics graphics) {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (field[i][j] == FIELD_X) {
                    drawX(i, j, graphics);
                } else if (field[i][j] == FIELD_0) {
                    draw0(i, j, graphics);
                }
            }
        }
    }

int checkState() {
    int diag = 0;

    int diag2 = 0;

    for (int i = 0; i < 3; i++) {


        diag += field[i][i];

        diag2 += field[i][2 - i];

    }

        if(diag == FIELD_0 * 3 || diag == FIELD_X * 3){return diag;}
      if(diag2 ==FIELD_0 * 3 || diag2 == FIELD_X * 3){return diag2;}

        int check_i, check_j;

        boolean hasEmpty = false;


        for(int i=0; i < 3; i++) {
            check_i = 0;
            check_j = 0;
            for (int j = 0; j < 3; j++) {
                if (field[i][j] == 0) {
                    hasEmpty = true;

                }
                check_i += field[i][j];

                check_j += field[j][i];
            }


            if (check_i == FIELD_0 * 3 || check_i == FIELD_X * 3) {

                return check_i;
            }
            if (check_j == FIELD_0 * 3 || check_j == FIELD_X * 3) {
                return check_j;
            }
        }
                        if(hasEmpty) return 0; else return -1;

}

}
