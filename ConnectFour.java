import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ConnectFour extends JPanel {
    static{
        System.loadLibrary("ConnectFour");
    }
    public native int[] getBoard();
    public native void setBoardCell(int row, int col, int value);
    public native boolean checkWin(int color);
    public native void startNew();
    public native boolean isRedTurn();
    public native boolean changeTurn();
    public native boolean checkDraw();

    public static final int ROWS = 4;
    public static final int COLS = 5;
    public static final int CELL_SIZE = 80;
    public static final int CIRCLE_DIAMETER = 60;
    public static final int PARKING_AREA_HEIGHT = 100;

    public Point circle1Position = new Point(125, ROWS * CELL_SIZE + 20);
    public Point circle2Position = new Point(225, ROWS * CELL_SIZE + 20);
    public final Point circle1ParkingPosition = new Point(125, ROWS * CELL_SIZE + 20);
    public final Point circle2ParkingPosition = new Point(225, ROWS * CELL_SIZE + 20);

    private Point draggingCircle = null;
    private int draggingColor = 0;
    public JLabel turnLabel;
    public ConnectFour() {
        setPreferredSize(new Dimension(COLS * CELL_SIZE, ROWS * CELL_SIZE + PARKING_AREA_HEIGHT));

        turnLabel = new JLabel("Turn: Red");
        turnLabel.setHorizontalAlignment(SwingConstants.CENTER);
        turnLabel.setFont(new Font("Arial", Font.BOLD, 16));
        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int column = e.getKeyChar() - '1';
                if (column >= 0 && column < COLS) {
                    keyColumnDrop(column);
                }
            }
        });

        MouseAdapter mouseAdapter = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Point mousePoint = e.getPoint();

                if (isRedTurn() && isWithinCircle(mousePoint, circle1Position)) {
                    draggingCircle = circle1Position;
                    draggingColor = 1;
                } else if (!isRedTurn() && isWithinCircle(mousePoint, circle2Position)) {
                    draggingCircle = circle2Position;
                    draggingColor = 2;
                }
            }

            public void mouseDragged(MouseEvent e) {
                if (draggingCircle != null) {
                    draggingCircle.setLocation(e.getX() - CIRCLE_DIAMETER / 2, e.getY() - CIRCLE_DIAMETER / 2);
                    repaint();
                }
            }

            public void mouseReleased(MouseEvent e) {
                if (draggingCircle != null) {
                    if (isWithinGrid(e.getPoint())) {
                        Point winningCell = snapToColumn(draggingCircle, draggingColor);
                        if (winningCell != null) {
                            colorWinningCell(winningCell, draggingColor);
                            if (checkWin(draggingColor)) {
                                showWinMessage(draggingColor);
                                resetGame();
                            }else if (checkDraw()){
                                JOptionPane.showMessageDialog(null, "It's a draw!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                                resetGame();
                            }else {
                                switchTurn();
                            }
                        }
                    }
                    resetToParkingArea();
                    draggingCircle = null;
                    draggingColor = 0;
                    repaint();
                }
            }
        };

        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);

        setLayout(new BorderLayout());
        add(turnLabel, BorderLayout.SOUTH);

        startNew();
    }

    private void keyColumnDrop(int column){
        int color = isRedTurn() ? 1 : 2;
        Point initialPos = new Point(column * CELL_SIZE + (CELL_SIZE - CIRCLE_DIAMETER) / 2, 0);
        Point winningCell = snapToColumn(initialPos, color);
        if(winningCell != null){
            colorWinningCell(winningCell, color);
            if(checkWin(color)){
                showWinMessage(color);
                resetGame();
            }else if (checkDraw()){
                JOptionPane.showMessageDialog(null, "It's a draw!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                resetGame();
            }else {
                switchTurn();
            }
        }
        resetToParkingArea();
        repaint();
    }
    public int[][] getBoardAs2D() {
        int[] flattenedBoard = getBoard();
        int[][] board2D = new int[ROWS][COLS];

        // Konwertowanie "spłaszczonej" tablicy do tablicy 2-wymiarowej
        for (int row = 0; row < 4; row++) {
            System.arraycopy(flattenedBoard, row * 5, board2D[row], 0, 5);
        }
        return board2D;
    }

    private boolean isWithinCircle(Point p, Point circleCenter) {
        int dx = p.x - circleCenter.x - CIRCLE_DIAMETER / 2;
        int dy = p.y - circleCenter.y - CIRCLE_DIAMETER / 2;
        return dx * dx + dy * dy <= (CIRCLE_DIAMETER / 2) * (CIRCLE_DIAMETER / 2);
    }

    private boolean isWithinGrid(Point p) {
        return p.y < ROWS * CELL_SIZE;
    }

    private Point snapToColumn(Point circlePosition, int color) {
        int gridX = (circlePosition.x + CIRCLE_DIAMETER / 2) / CELL_SIZE;
        int[][] gridColors = getBoardAs2D();

        for (int row = ROWS - 1; row >= 0; row--) {
            if (gridColors[row][gridX] == 0) {
                //System.out.println("snap to column");
                setBoardCell(row, gridX, color);

                circlePosition.x = gridX * CELL_SIZE + (CELL_SIZE - CIRCLE_DIAMETER) / 2;
                circlePosition.y = row * CELL_SIZE + (CELL_SIZE - CIRCLE_DIAMETER) / 2;
                return new Point(gridX, row);
            }
        }
        return null;
    }

    private void resetToParkingArea() {
        circle1Position.setLocation(circle1ParkingPosition);
        circle2Position.setLocation(circle2ParkingPosition);
    }

    private void switchTurn() {
        changeTurn();
        turnLabel.setText("Turn: " + (isRedTurn() ? "Red" : "Blue"));
    }

    private void colorWinningCell(Point winningCell, int color) {
        //System.out.println("ustawiam cell");
        setBoardCell(winningCell.y, winningCell.x, color);
        repaint();
    }

    private void showWinMessage(int color) {
        String winner = (color == 1) ? "Red" : "Blue";
        JOptionPane.showMessageDialog(this, winner + " wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
    }

    private void resetGame() {
        startNew();
        circle1Position.setLocation(circle1ParkingPosition);
        circle2Position.setLocation(circle2ParkingPosition);
        turnLabel.setText("Turn: " + (isRedTurn() ? "Red" : "Blue"));
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int[][] gridColors = getBoardAs2D();

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                int x = col * CELL_SIZE;
                int y = row * CELL_SIZE;

                if (gridColors[row][col] != 0) {
                    if(gridColors[row][col] == 1){
                        g.setColor(Color.RED);
                    } else {
                        g.setColor(Color.BLUE);
                    }
                    g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                }

                g.setColor(Color.LIGHT_GRAY);
                g.drawRect(x, y, CELL_SIZE, CELL_SIZE);
            }
        }

        g.setColor(new Color(185, 185, 185));
        g.fillRect(0, ROWS * CELL_SIZE, COLS * CELL_SIZE, ROWS * CELL_SIZE);

        g.setColor(Color.RED);
        g.fillOval(circle1Position.x, circle1Position.y, CIRCLE_DIAMETER, CIRCLE_DIAMETER);
        g.setColor(Color.BLUE);
        g.fillOval(circle2Position.x, circle2Position.y, CIRCLE_DIAMETER, CIRCLE_DIAMETER);

        g.setColor(Color.BLACK);
        g.drawLine(0, ROWS * CELL_SIZE, COLS * CELL_SIZE, ROWS * CELL_SIZE);

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Connect Four!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ConnectFour());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
    }
}
