package com.mycom.myapp.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import com.mycom.myapp.dao.BookDao;
import com.mycom.myapp.dto.BookDto;


public class BookManager extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton searchButton, addButton, editButton, listButton;
    private JTextField searchWordField;

    private BookDao bookDao = new BookDao();
    
	// 자바 설정 이용
    public BookManager() {
    		
        setTitle("고객 및 주문 및 도서 관리");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tableModel = new DefaultTableModel(new Object[]{"Book ID", "Book Name", "Publisher", "Price"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);

        listBook();

        Dimension textFieldSize = new Dimension(400, 28);
        searchWordField = new JTextField();
        searchWordField.setPreferredSize(textFieldSize);

        searchButton = new JButton("검색");

        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("제목 검색"));
        searchPanel.add(searchWordField);
        searchPanel.add(searchButton);

        addButton = new JButton("등록");
        editButton = new JButton("수정 & 삭제");
        listButton = new JButton("목록");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(listButton);

        setLayout(new BorderLayout());
        add(searchPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        searchButton.addActionListener(e -> {
            String searchWord = searchWordField.getText();
            if (!searchWord.isBlank()) {
                listBookSearch(searchWord);
            }
        });

        addButton.addActionListener(e -> {
            AddBookDialog addDialog = new AddBookDialog(this, tableModel);
            addDialog.setVisible(true);
        });

        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                EditBookDialog editDialog = new EditBookDialog(this, tableModel, selectedRow);
                editDialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "도서를 선택하세요.");
            }
        });

        listButton.addActionListener(e -> listBook());

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow >= 0) {
                        EditBookDialog editDialog = new EditBookDialog(BookManager.this, tableModel, selectedRow);
                        editDialog.setVisible(true);
                    }
                }
            }
        });
    }

    private void clearTable() {
        tableModel.setRowCount(0);
    }

    private void listBook() {
        clearTable();
        List<BookDto> bookList = bookDao.listBook();
        for (BookDto book : bookList) {
            tableModel.addRow(new Object[]{book.getBookId(), book.getBookName(), book.getPublisher(), book.getPrice()});
        }
    }

    private void listBookSearch(String searchWord) {
        clearTable();
        List<BookDto> bookList = bookDao.listBookSearch(searchWord);
        for (BookDto book : bookList) {
            tableModel.addRow(new Object[]{book.getBookId(), book.getBookName(), book.getPublisher(), book.getPrice()});
        }
    }
    
    // BookDao에서 가져와서 사용할 메서드들
    BookDto detailBook(int bookId) {
        return bookDao.detailBook(bookId);
    }

    void insertBook(BookDto book) {
        bookDao.insertBook(book);
        
        listBook();
        
    }

    void updateBook(BookDto book) {
        bookDao.updateBook(book);        
        listBook();
    }

    void deleteBook(int bookId) {
        bookDao.deleteBook(bookId);
        listBook();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new BookManager().setVisible(true);
        });
    }
}
