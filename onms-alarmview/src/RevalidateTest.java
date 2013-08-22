import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public class RevalidateTest extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTable table;

    public RevalidateTest() {
        Object[] columnNames = {"Type", "Company", "Shares", "Price", "Boolean"};
        Object[][] data = {
            {"Buy", "IBM", new Integer(1000), new Double(80.50), false},
            {"Sell", "MicroSoft", new Integer(2000), new Double(6.25), true},
            {"Sell", "Apple", new Integer(3000), new Double(7.35), true},
            {"Buy", "Nortel", new Integer(4000), new Double(20.00), false}
        };
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        table = new JTable(model) {

            private static final long serialVersionUID = 1L;

            @Override
            public Class getColumnClass(int column) {
                return getValueAt(0, column).getClass();
            }
        };
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
        createPopupMenu();
    }

    private void createPopupMenu() {
        JPopupMenu popup = new JPopupMenu();
        JMenuItem myMenuItem1 = new JMenuItem("cccccccccccccccccccccc");
        JMenuItem myMenuItem2 = new JMenuItem("bbbbbbbbbbbbbbbbbbbbbb");
        popup.add(myMenuItem1);
        popup.add(myMenuItem2);
        MouseListener popupListener = new PopupListener(popup);
        table.addMouseListener(popupListener);
    }

    private class PopupListener extends MouseAdapter {

        private JPopupMenu popup;

        PopupListener(JPopupMenu popupMenu) {
            popup = popupMenu;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            maybeShowPopup(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (table.getSelectedRow() != -1) {
                maybeShowPopup(e);
            }
        }

        private void maybeShowPopup(MouseEvent e) {
            if (e.isPopupTrigger()) {
                popup.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                RevalidateTest frame = new RevalidateTest();
                frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
                frame.pack();
                frame.setLocation(150, 150);
                frame.setVisible(true);
            }
        });
    }
}