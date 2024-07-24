package com.mycom.myapp.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.mycom.myapp.dto.BookDto;


public class EditBookDialog extends JDialog{
	private JTextField bookIdField, bookNameField, publisherField, priceField;
	private JButton updateButton, deleteButton;
	
	public EditBookDialog(BookManager parent, DefaultTableModel tableModel, int rowIndex) { // 선택된 row index
		setTitle("Book Edit Dialog");
		setSize(300, 200);
		setLayout(new BorderLayout());
		setLocationRelativeTo(parent); // 부모에 맞게
		
		// 선택된 book 의 bookId 로 book table 에서 조회
		Integer bookId = (Integer) tableModel.getValueAt(rowIndex, 0);
		BookDto book = parent.detailBook(bookId);	
		
		// input panel
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(4, 2));
		
		// field
		bookIdField = new JTextField(String.valueOf(bookId)); // 정수 -> 문자열
		bookIdField.setEditable(false);
		bookNameField = new JTextField(book.getBookName());
		publisherField = new JTextField(book.getPublisher());
		priceField = new JTextField(String.valueOf(book.getPrice())); // 정수 -> 문자열
		
		// button
		updateButton = new JButton("수정");
		
		// add field with label
		inputPanel.add(new JLabel("Book id"));
		inputPanel.add(bookIdField);
		inputPanel.add(new JLabel("Book Name"));
		inputPanel.add(bookNameField);
		inputPanel.add(new JLabel("Publisher"));
		inputPanel.add(publisherField);
		inputPanel.add(new JLabel("Price"));
		inputPanel.add(priceField);
		
		// button panel
		JPanel buttonPanel = new JPanel();
		
		// button
		updateButton = new JButton("수정");
		deleteButton = new JButton("삭제");
		
		buttonPanel.add(updateButton);
		buttonPanel.add(deleteButton);
		
		// add inputPanel, buttonPanel to Dialog
		add(inputPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
		
		// update button actionListner
		updateButton.addActionListener( e -> {
			
            int ret = JOptionPane.showConfirmDialog(this, "수정할까요?", "수정 확인", JOptionPane.YES_NO_OPTION);
            if( ret == JOptionPane.YES_OPTION ) {
//            	int curBookId = Integer.parseInt(bookIdField.getText());
            	String bookName = bookNameField.getText();
            	String publisher = publisherField.getText();
            	int price = Integer.parseInt(priceField.getText());
            	
            	parent.updateBook(new BookDto(bookId, bookName, publisher, price)); // 위쪽에 선언된(선택된 row 에서) 변수를 사용
            	dispose();
            }
		});
		
		// delete button actionListner
		deleteButton.addActionListener( e -> {
			
            int ret = JOptionPane.showConfirmDialog(this, "삭제할까요?", "삭제 확인", JOptionPane.YES_NO_OPTION);
            if( ret == JOptionPane.YES_OPTION ) {
            	
            	parent.deleteBook(bookId); 
            	dispose();
            }
		});
	}
}
























